package com.coupon.utils;

import com.yuelvhui.util.exception.ServiceException;
import com.yuelvhui.util.safety.MD5Util;
import com.yuelvhui.util.string.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.UUID;

public class SignUtil {

    private static final Logger logger = LoggerFactory.getLogger(SignUtil.class);

    /**
     * 参数签名校验
     *
     * @param params
     * @param sign
     * @return
     */
    public static boolean verifySign(SortedMap<String, String> params, String mchKey, String sign) {
        if (params.containsKey("sign")) {
            params.remove("sign");
        }
        try {
            String signStr = buildSign(params, mchKey);
            if (sign.equals(signStr)) {
                return true;
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        throw new ServiceException(ErrorCode.DataSignIsFailure.getCode(), "签名失败");
    }

    /**
     * 生成参数签名
     *
     * @param params
     * @param mchKey
     * @return
     */
    public static String buildSign(SortedMap<String, String> params, String mchKey) {
        // 参数字符串拼接
        StringBuilder stringBuilder = new StringBuilder();
        for (SortedMap.Entry<String, String> entry : params.entrySet()) {
            stringBuilder.append(entry.getKey()).append('=').append(entry.getValue()).append('&');
        }

        stringBuilder.append("key=").append(mchKey);
        //签名格式：key1=value1&key2=value2&...&key=mchKey

        try {
            return MD5Util.encryptionMD5(stringBuilder.toString());
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return null;
    }

    public static Map<String, String> sortMapStr(Map<String, String> params, String[] keys) {
        Map<String, String> requestMap = new LinkedHashMap<>();
        for (String key : keys) {
            String value = params.get(key);
            if (value != null && value.length() > 0) {
                requestMap.put(key, value);
            }
        }
        return requestMap;
    }

    public static Map<String, Object> sortMapObj(Map<String, Object> params, String[] keys) {
        Map<String, Object> requestMap = new LinkedHashMap<>();
        for (String key : keys) {
            if (params.get(key) != null) {
                requestMap.put(key, params.get(key));
            }
        }
        return requestMap;
    }

    public static String getSignStr(Map<String, Object> params, String merchantKey) {
        //拼接加签字段
        StringBuilder sb = new StringBuilder();
        for (String key : params.keySet()) {
            String value = String.valueOf(params.get(key));
            if (StringUtil.isNotEmpty(value)) {
                sb.append(key).append("=").append(value).append("&");
            }
        }
        sb.append("key").append("=").append(merchantKey);
        return sb.toString();
    }

    /**
     * 生成用于加密的盐值
     *
     * @return
     */
    public static String getSaltValue() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 生成业务系统token
     *
     * @param systemsId
     * @param systemsKey
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String getSysToken(String systemsId, String systemsKey) throws Exception {

        long time = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        String sign = MD5Util.encryptionMD5(StringUtil.append(systemsId, ".", String.valueOf(time), ".", systemsKey));
        return StringUtil.append(systemsId, ".", String.valueOf(time), ".", sign);
    }
}
