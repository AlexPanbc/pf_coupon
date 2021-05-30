package com.coupon.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @program: pf_cashier
 * @description: 生成编号
 * @author: Mr.Wang
 * @create: 2019-10-24 17:11
 **/
public class SnowFlakeSimplifiedUtil {

    private final static Logger logger= LoggerFactory.getLogger(SnowFlakeSimplifiedUtil.class);

    /**
     * 起始的时间戳
     */
    private final static long START_STMP = 1480166465631L;

    /**
     * 序列号占用的位数
     */
    private final static long SEQUENCE_BIT = 12;
    /**
     * 业务编码占用的位数
     */
    private final static long BUSINESS_BIT = 2;

    /**
     * 每一部分的最大值
     */
    private final static long MAX_SEQUENCE_NUM = -1L ^ (-1L << SEQUENCE_BIT);

    /**
     * 每一部分向左的位移
     */
    private final static long BUSINESS_LEFT = SEQUENCE_BIT;
    private final static long TIMESTMP_LEFT = SEQUENCE_BIT + BUSINESS_BIT;

    /**
     * 业务标识
     */
    private long businessId;
    /**
     *
     */
    private long sequence = 0L;
    /**
     * 上一次时间戳
     */
    private long lastStmp = -1L;

    private static SnowFlakeSimplifiedUtil snowFlakeUtil;

    public static SnowFlakeSimplifiedUtil getInstace(long businessId){
        try {
            synchronized (SnowFlakeSimplifiedUtil.class) {
                if (null == snowFlakeUtil) {
                    // 模拟在创建对象之前做一些准备工作
                    Thread.sleep(1000);
                    snowFlakeUtil = new SnowFlakeSimplifiedUtil(businessId);
                }
            }
        } catch (InterruptedException e) {
            // TODO: handle exception
        }
        return snowFlakeUtil;
    }

    private SnowFlakeSimplifiedUtil(long businessId) {
        //防御性判断
        if (businessId > BUSINESS_LEFT || businessId < 0) {
            throw new IllegalArgumentException("datacenterId can't be greater than BUSINESS_LEFT or less than 0");
        }
        this.businessId =  businessId;
    }

    /**
     * 产生下一个ID
     *
     * @return
     */
    public synchronized long nextId() {
        long currStmp = getNewstmp();
        if (currStmp < lastStmp) {
            throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
        }

        if (currStmp == lastStmp) {
            //相同毫秒内，序列号自增
            sequence = (sequence + 1) & MAX_SEQUENCE_NUM;
            //同一毫秒的序列数已经达到最大
            if (sequence == 0L) {
                currStmp = getNextMill();
            }
        } else {
            //不同毫秒内，序列号置为0
            sequence = 0L;
        }

        lastStmp = currStmp;

        return (currStmp - START_STMP) << TIMESTMP_LEFT //时间戳部分
                | businessId << BUSINESS_LEFT           //业务编码部分
                | sequence;                             //序列号部分
    }

    private long getNextMill() {
        long mill = getNewstmp();
        while (mill <= lastStmp) {
            mill = getNewstmp();
        }
        return mill;
    }

    private long getNewstmp() {
        return System.currentTimeMillis();
    }

    public static void main(String[] args) {
        for (int i = 0; i < (1 << 12); i++) {
            System.out.println(String.valueOf(SnowFlakeSimplifiedUtil.getInstace(3L).nextId()));
        }
    }
}