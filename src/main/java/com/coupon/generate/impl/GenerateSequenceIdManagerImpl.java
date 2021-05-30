package com.coupon.generate.impl;

import com.coupon.generate.GenerateNumberManager;
import com.yuelvhui.util.date.DateUtil;
import com.yuelvhui.util.string.StringUtil;
import com.coupon.utils.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;

@Component("GenerateSequenceIdManagerImpl")
public class GenerateSequenceIdManagerImpl implements GenerateNumberManager {

    @Autowired
    private RedisService redisService;

    private static final Object syncObj = new Object();

    private LongAdder longAdder = new LongAdder();

    private Long baseSequenceId = 1L;

    private long stepLength = 1000;

    private Date firstDate;

    private Date lastUpdateDate = Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC));

    @PostConstruct
    public void init() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2017, 10, 20); // 2017-11-20
        firstDate = calendar.getTime();
        //
        String key = buildSeqIdCacheKey() + "_";
        Long base = redisService.incr(key, stepLength);
        baseSequenceId = base - stepLength;
        redisService.expire(key, 24 * 60 * 60);
    }

    private String buildSeqIdCacheKey() {
        return "orderNo_SeqId:" + new SimpleDateFormat("yyyyMMdd").format(new Date());
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
        String part2 = StringUtil.leftPad(String.valueOf(days), "0", 4);

        Long sequenceId = nextSequenceId();
        String part3 = StringUtil.leftPad(String.valueOf(sequenceId), "0", 6); // 默认长度为6位

        String part4 = String.valueOf(ThreadLocalRandom.current().nextInt(1, 10)); // 一位随机数

        //
        return StringUtil.append(prefix, part2, part3, part4);
    }

    @Override
    public String nextNumberString() {
        return getTemplateCode();
    }

    private final static AtomicInteger atomic = new AtomicInteger(0);

    private String getTemplateCode() {
        if(!redisService.exists("id")){
            String id = String.format("%06d", atomic.incrementAndGet());
            redisService.putNeverExpires("id",id);
            return id;
        }
        Integer num = Integer.parseInt(redisService.get("id"));
        atomic.getAndSet(num);
        String id = String.format("%06d", atomic.incrementAndGet());
        redisService.putNeverExpires("id",id);
        return id;
    }


}
