package com.coupon.generate;

public interface GenerateNumberManager {

    /**
     * 生成带前缀的消费码
     *
     * @param prefix
     * @return
     */
    public String nextNumberString(String prefix);

    /**
     * 生成不带前缀的消费码
     *
     * @return
     */
    public String nextNumberString();
}
