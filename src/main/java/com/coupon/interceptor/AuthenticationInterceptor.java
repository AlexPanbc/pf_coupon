package com.coupon.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.coupon.interfaces.SystemToken;
import com.coupon.utils.Constants;
import com.coupon.utils.ErrorCode;
import com.yuelvhui.util.exception.ServiceException;
import com.yuelvhui.util.http.HttpRequest;
import com.yuelvhui.util.http.HttpResponse;
import com.yuelvhui.util.safety.MD5Util;
import com.yuelvhui.util.string.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * @program: utour_user
 * @description: Token拦截器
 * @author: Mr.Wang
 * @create: 2019-05-14 11:38
 **/
public class AuthenticationInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationInterceptor.class);

    @Value("${pf.permissions.merchant}")
    private String merchantTokenIp;

    @Value("${systems.id}")
    private String systemsId;
    @Value("${systems.key}")
    private String systemsKey;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        // 从 http 请求头中取出 token
        String token = httpServletRequest.getHeader(Constants.HTTP_HEADER_AUTHORIZATION);
        // 如果不是映射到方法直接通过
        if (!(object instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();
        //检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(SystemToken.class)) {
            SystemToken systemToken = method.getAnnotation(SystemToken.class);
            if (systemToken.required()) {
                // 执行认证
                if (StringUtil.isBlank(token) || !token.startsWith(Constants.AUTHORIZATION_PREFIX_SYS)) {
                    throw new ServiceException(ErrorCode.TokenVerificationFailure.getCode(), "无请求头或请求头签名校验失败");
                }
                //截取到用户的token
                token = token.substring(Constants.AUTHORIZATION_PREFIX_SYS.length()).toLowerCase();
                long time = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
                String sign = MD5Util.encryptionMD5(StringUtil.append(systemsId, ".", String.valueOf(time), ".", systemsKey));
                String membertoken = StringUtil.append(systemsId, ".", String.valueOf(time), ".", sign);
                HttpResponse response = HttpRequest.get(StringUtil.append(merchantTokenIp, "?token=" + token))
                        .header(Constants.HTTP_HEADER_AUTHORIZATION, membertoken)
                        .charset(Constants.ENCODING_UTF8)
                        .execute();
                String result = new String(response.bodyBytes(), Constants.ENCODING_UTF8);
                JSONObject jsonObject = JSONObject.parseObject(result);
                if (!"SUCCESS".equals(jsonObject.getString("code"))
                        || !"SUCCESS".equals(jsonObject.getString("resultCode"))
                        || !jsonObject.getBoolean("response")) {
                    throw new ServiceException(ErrorCode.TokenVerificationFailure.getCode(), jsonObject.getString("resultMessage"));
                }
            }
        }
        return true;
    }

    private boolean verifyToken(String mchId, String timeStamp, String mchKey, String token) {
        String preSignStr = StringUtil.append(mchId, ".", timeStamp, ".", mchKey);
        if (MD5Sign(preSignStr, token)) {
            return true;
        }
        throw new ServiceException(ErrorCode.TokenVerificationFailure.getCode(), "请求头签名校验失败");
    }

    private boolean MD5Sign(String str, String sign) {
        try {
            String sign2 = MD5Util.encryptionMD5(str);
            if (sign2.equals(sign)) {
                return true;
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {
    }
}
