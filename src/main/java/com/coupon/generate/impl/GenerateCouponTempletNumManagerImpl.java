package com.coupon.generate.impl;

import com.coupon.generate.GenerateNumberManager;
import com.coupon.utils.redis.RedisService;
import com.yuelvhui.util.date.DateUtil;
import com.yuelvhui.util.string.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.LongAdder;

@Component("GenerateCouponTempletNumManagerImpl")
public class GenerateCouponTempletNumManagerImpl implements GenerateNumberManager {

    @Autowired
    private RedisService redisService;

    private static final Object syncObj = new Object();

    private LongAdder longAdder = new LongAdder();

    private Long baseSequenceId = 1L;

    private long stepLength = 1000;

    private Date lastUpdateDate = Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC));

    private final SimpleDateFormat SDF_DATE = new SimpleDateFormat("yyyyMMdd");

    private Date firstDate;

    @PostConstruct
    public void init() {
        Calendar calendar = Calendar.getInstance();
        // 从哪天开始   2021-01-01
        calendar.set(2021, 1, 1);
        firstDate = calendar.getTime();
        String key = buildSeqIdCacheKey() + "_";
        Long base = redisService.incr(key, stepLength);
        baseSequenceId = base - stepLength;
        redisService.expire(key, 24 * 60 * 60);
    }

    private String buildSeqIdCacheKey() {
        return "coupon_temple_SeqId:" + SDF_DATE.format(new Date());
    }

    private Long nextSequenceId() {
        // 随机一个增量值
        int value = ThreadLocalRandom.current().nextInt(1, 4);
        longAdder.add(value);
        Long nextId = longAdder.longValue();

        // 验证如果累积的增量值超过 stepLength 则需要通过redis更新 baseSequenceId
        // 或者不是同一天
        Date currentDate = Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC));
        if (stepLength <= nextId || DateUtil.getDay(lastUpdateDate) != DateUtil.getDay(currentDate)) {
            synchronized (syncObj) {
                if (stepLength <= nextId || DateUtil.getDay(lastUpdateDate) != DateUtil.getDay(currentDate)) {
                    longAdder.reset();
                    lastUpdateDate = Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC));
                    //
                    String key = buildSeqIdCacheKey();
                    baseSequenceId = redisService.incr(key, stepLength) - stepLength;
                    redisService.expire(key, 24 * 60 * 60);
                }
            }
            // 重新使用随机值做增量值
            longAdder.add(value);
            nextId = longAdder.longValue();
        }

        Long sequenceId = baseSequenceId + nextId;
        return sequenceId;
    }

    @Override
    public String nextNumberString(String prefix) {
        int days = DateUtil.getIntervalDays(firstDate, Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC)));
        String part2 = StringUtil.leftPad(String.valueOf(days), "0", 5);
        Long sequenceId = nextSequenceId();
        // 默认长度为6位  位数不够的前面补零
        String part3 = StringUtil.leftPad(String.valueOf(sequenceId), "0", 6);
        return StringUtil.append(prefix,part2, part3);
    }

    @Override
    public String nextNumberString() {
        return nextNumberString("");
    }

}
