package com.coupon.utils.rabbitmq;

import com.yuelvhui.util.string.StringUtil;
import com.coupon.utils.Constants;

/**
 * @program: pf_coupon
 * @description: 交换机名称
 * @author: Mr.Wang
 * @create: 2019-08-12 19:32
 **/
public class ExchangeKeyBuilder {

    /**
     * 路由 模式调度策略
     *
     * @return
     */
    public static String diceExchangeKey() {
        return StringUtil.append(Constants.PROJECT_NAME, ".dice.exchange");
    }

    /**
     * 订阅/广播 模式调度策略
     *
     * @return
     */
    public static String fanoutExchangeKey() {
        return StringUtil.append(Constants.PROJECT_NAME, ".fanout.exchange");
    }

    /**
     * 通配符 模式调度策略
     *
     * @return
     */
    public static String topicExchangeKey() {
        return StringUtil.append(Constants.PROJECT_NAME, ".topic.exchange");
    }

    /**
     * 键值对 模式调度策略
     *
     * @return
     */
    public static String headersExchangeKey() {
        return StringUtil.append(Constants.PROJECT_NAME, ".headers.exchange");
    }
}
