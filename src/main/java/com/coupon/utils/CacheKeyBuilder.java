package com.coupon.utils;

import com.yuelvhui.util.string.StringUtil;

public class CacheKeyBuilder {

    /**
     * 生成支付商户信息缓存Key
     *
     * @param mchId
     * @return
     */
    public static String buildMerchantCacheKey(String mchId) {
        return StringUtil.append(Constants.PROJECT_NAME, "_pay_config_", mchId);
    }

    /**
     * 生成微信支付信息缓存Key
     *
     * @param mchAppId
     * @param tradeType
     * @return
     */
    public static String buildConfigWechatCacheKey(String mchAppId, String tradeType) {
        return StringUtil.append(Constants.PROJECT_NAME, "_config_wechat_", mchAppId, "_", tradeType);
    }
}
