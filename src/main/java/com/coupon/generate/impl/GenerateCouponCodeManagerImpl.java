package com.coupon.generate.impl;

import com.coupon.generate.GenerateNumberManager;
import com.coupon.utils.redis.RedisService;
import com.yuelvhui.util.date.DateUtil;
import com.yuelvhui.util.string.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.LongAdder;

/**
 * Created by tanyu on 2017/1/4.
 */
@Component("GenerateCouponCodeManagerImpl")
public class GenerateCouponCodeManagerImpl implements GenerateNumberManager {

    private static final int NUMBER_OF_VALID_INPUT_CHARACTERS = 29;
    /**
     * 可用的字符,排除掉了:0、1、A、E、I、O、U
     */
    private static final char[] CHARACTER_FROM_CODE_POINT = new char[]{'8', 'K', '5', 'L', '6', 'M', 'N', '9', 'P', '7', '2', 'W', 'X', 'D', 'F', 'Y', 'Z', 'B', 'C', 'G', 'H', '3', '4', 'R', 'S', 'J', 'Q', 'T', 'V'};

    private ConcurrentHashMap<String, Counter> beanMap = new ConcurrentHashMap<>();

    @Autowired
    private RedisService redisService;

    private final Object syncObj = new Object();

    private Date firstDate;

    private Date lastUpdateDate = Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC));

    @PostConstruct
    public void init() {
        Calendar calendar = Calendar.getInstance();
        // 从哪天开始   2021-01-01
        calendar.set(2021, 1, 1);
        firstDate = calendar.getTime();
    }

    @Override
    public String nextNumberString(String prefix) {
        return StringUtil.append(prefix, nextId());
    }

    @Override
    public String nextNumberString() {
        return nextId();
    }

    public String nextId() {
        Date date = Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC));
        int days = DateUtil.getIntervalDays(firstDate, date);
        return nextId(days);
    }

    public String nextId(int day) {
        char rndCh = CHARACTER_FROM_CODE_POINT[ThreadLocalRandom.current().nextInt(29)];

        // 生成一个缓存Key
        String key = new StringBuilder().append("confirm_num.").append(rndCh).append('.').append(day).toString();

        Long sequenceId = nextSequenceId(key);

        return buildString(day, rndCh, sequenceId);
    }

    private Long nextSequenceId(String key) {
        // 如果不是同一天，则需要重置本地的记数器，防止记数器使用昨天的序号造成与今天的序号重复，产生冲突
        Date currentDate = Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC));
        if (DateUtil.getDay(lastUpdateDate) != DateUtil.getDay(currentDate)) {
            synchronized (syncObj) {
                if (DateUtil.getDay(lastUpdateDate) != DateUtil.getDay(currentDate)) {
                    lastUpdateDate = Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC));
                    beanMap = new ConcurrentHashMap<>();
                }
            }
        }

        Counter counter = beanMap.computeIfAbsent(key, k -> new Counter(key));
        return counter.nextId();
    }

    /**
     * 生成带格式字符串
     *
     * @param rndCh
     * @param day
     * @param sequenceId
     * @return
     */
    public String buildString(int day, char rndCh, Long sequenceId) {

        /**
         * 编码规则
         *
         * [part1] 123: 序号,由sequenceId转换后而来,由后三位拼接
         *
         * [part2] 4: 随机数
         *
         * [part3] 567: 为2021-01-01至今天的天数
         *              其中占位 5 上的值为两部分: 第一 0~10之间的随机数。 第二 是日期天数(每满29*29-1天)往上进一位
         *
         * [part4] C: 通过generateCheckCharacter()获取的校验码
         *
         */

        //[part1]
        String part1 = StringUtil.leftPad(number2Character(sequenceId), "8", 3);

        //[part2]
        char part2 = rndCh;

        char part3;
        // 生成一个 0~10(不含10)之间的随机数，用于获取一个字符
        int part3_RndNum = ThreadLocalRandom.current().nextInt(10);
        int rndChAdd2 = day / (29 * 28 - 1);
        if (rndChAdd2 > 0) {
            // 时间 days如果大于 29*28则需要在进位（如上述所述位置 5 除了表示时间，还会随机数）
            part3 = CHARACTER_FROM_CODE_POINT[part3_RndNum + 10 + rndChAdd2];
        } else {
            part3 = CHARACTER_FROM_CODE_POINT[part3_RndNum];
        }

        String part3_1;
        if (day <= 29 * 28L - 1) {
            part3_1 = StringUtil.leftPad(number2Character(Long.valueOf(day)), "8", 2);
        } else {
            part3_1 = StringUtil.leftPad(number2Character(day % (29 * 28L - 1)), "8", 2);
        }

        //[part4]
        StringBuilder input = new StringBuilder().append(part1).append(part2).append(part3).append(part3_1);
        char part4 = generateCheckCharacter(input.toString());

        input.append(part4);
        return input.toString();
    }

    public String number2Character(Long number) {
        /**
         * 将指定的数据转换成字符表示,简单的理解可29进制即可(29个字符)
         */
        long mod = number % 29;
        if (number >= 29) {
            return number2Character(number / 29) + new String(new char[]{CHARACTER_FROM_CODE_POINT[(int) mod]});
        }
        return new String(new char[]{CHARACTER_FROM_CODE_POINT[(int) mod]});
    }

    private int codePointFromCharacter(char ch) {
        int ascii = (int) ch;
        // 2 ~ 9
        if (ascii >= 50 && ascii <= 57) {
            return ascii - 50;
        }

        // B ~ D
        if (ascii >= 66 && ascii <= 68) {
            return ascii - 66;
        }

        // F ~ H
        if (ascii >= 70 && ascii <= 72) {
            return ascii - 70;
        }

        // J ~ N
        if (ascii >= 74 && ascii <= 78) {
            return ascii - 74;
        }

        // P ~ T
        if (ascii >= 80 && ascii <= 84) {
            return ascii - 80;
        }

        // V ~ Z
        if (ascii >= 86 && ascii <= 90) {
            return ascii - 86;
        }

        // 按目前算法的处理规则,理论上是不会发生返回-1的情况,所以整个代码中也就没有做相关的异常处理
        return -1;
    }

    /**
     * @param input
     * @return 以下的算法来自 : https://en.wikipedia.org/wiki/Luhn_mod_N_algorithm
     */
    public char generateCheckCharacter(String input) {

        int factor = 2;
        int sum = 0;
        final int n = NUMBER_OF_VALID_INPUT_CHARACTERS;

        // Starting from the right and working leftwards is easier since
        // the initial "factor" will always be "2"
        for (int i = input.length() - 1; i >= 0; i--) {
            int codePoint = codePointFromCharacter(input.charAt(i));
            int addend = factor * codePoint;

            // Alternate the "factor" that each "codePoint" is multiplied by
            factor = (factor == 2) ? 1 : 2;

            // Sum the digits of the "addend" as expressed in base "n"
            addend = (int) (addend / n) + (addend % n);
            sum += addend;
        }

        // Calculate the number that must be added to the "sum"
        // to make it divisible by "n"
        int remainder = sum % n;
        int checkCodePoint = (n - remainder) % n;

        return CHARACTER_FROM_CODE_POINT[checkCodePoint];
    }

    /**
     * @param input
     * @return 以下的算法来自 : https://en.wikipedia.org/wiki/Luhn_mod_N_algorithm
     */
    public Boolean validateCheckCharacter(String input) {

        int factor = 1;
        int sum = 0;
        final int n = NUMBER_OF_VALID_INPUT_CHARACTERS;

        // Starting from the right, work leftwards
        // Now, the initial "factor" will always be "1"
        // since the last character is the check character
        for (int i = input.length() - 1; i >= 0; i--) {
            int codePoint = codePointFromCharacter(input.charAt(i));
            int addend = factor * codePoint;

            // Alternate the "factor" that each "codePoint" is multiplied by
            factor = (factor == 2) ? 1 : 2;

            // Sum the digits of the "addend" as expressed in base "n"
            addend = (int) (addend / n) + (addend % n);
            sum += addend;
        }

        int remainder = sum % n;

        return (remainder == 0);
    }

    private class Counter {

        private final Object syncObj = new Object();

        private int stepLength = 100;

        private Long baseSequenceId = 1L;

        private LongAdder longAdder = new LongAdder();

        private String cacheKey;

        public Counter(String cacheKey) {
            this.cacheKey = cacheKey;
            baseSequenceId = redisService.incr(cacheKey, stepLength) - stepLength;
        }

        public Long nextId() {

            // 随机一个增量值
            int value = ThreadLocalRandom.current().nextInt(3) + 1;
            longAdder.add(value);
            Long nextId = longAdder.longValue();

            // 验证如果累积的增量值超过 stepLength 则需要通过redis更新 baseSequenceId
            if (stepLength <= nextId) {
                synchronized (syncObj) {
                    if (stepLength <= nextId) {
                        longAdder.reset();
                        //
                        baseSequenceId = redisService.incr(cacheKey, stepLength) - stepLength;
                    }
                }
                // 重新使用随机值做增量值
                longAdder.add(value);
                nextId = longAdder.longValue();
            }

            Long sequenceId = baseSequenceId + nextId;
            return sequenceId;
        }
    }
}
