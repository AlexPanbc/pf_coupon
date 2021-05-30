package com.coupon.utils.rabbitmq;

import com.coupon.utils.Constants;
import com.yuelvhui.util.string.StringUtil;

/**
 * @program: pf_coupon
 * @description: 交换机名称
 * @author: Mr.Wang
 * @create: 2019-08-12 19:32
 **/
public class BindingKeyBuilder {

    /**
     * 等待发券的批次单队列
     *
     * @return
     */
    public static String waittingCouponBatchBindingKey() {
        return StringUtil.append(Constants.PROJECT_NAME, ".binding.name.waitting.coupon.batch");
    }


    /**
     * 拼团活动过期处理
     *
     * @return
     */
    public static String activityBindingKey() {
        return StringUtil.append(Constants.PROJECT_NAME, ".binding.name.activity");
    }

    /**
     * 拼团活动过期处理 死信
     *
     * @return
     */
    public static String activityBindingDelayKey() {
        return StringUtil.append(Constants.PROJECT_NAME, ".binding.name.activity.delay");
    }
}
