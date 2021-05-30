package com.coupon.utils.rabbitmq;

import com.coupon.utils.Constants;
import com.yuelvhui.util.string.StringUtil;

/**
 * @program: pf_coupon
 * @description: 队列名称
 * @author: Mr.Wang
 * @create: 2019-08-12 19:30
 **/
public class QueueKeyBuilder {

    /**
     * 等待退款的退款单队列
     *
     * @return
     */
    public static String waittingCouponBatchQueueKey() {
        return StringUtil.append(Constants.PROJECT_NAME, ".queue.name.waitting.coupon.batch");
    }

    /**
     * 拼团活动过期处理队列
     */
    public static String activityKey() {
        return StringUtil.append(Constants.PROJECT_NAME, ".queue.name.activity");
    }

    /**
     * 拼团活动过期处理队列 死信队列
     */
    public static String activityDelayKey() {
        return StringUtil.append(Constants.PROJECT_NAME, ".queue.name.activity.delay");
    }
}
