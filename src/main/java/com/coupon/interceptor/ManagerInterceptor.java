package com.coupon.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.coupon.interfaces.ManagerToken;
import com.coupon.utils.Constants;
import com.coupon.utils.ErrorCode;
import com.coupon.utils.SignUtil;
import com.yuelvhui.util.exception.ServiceException;
import com.yuelvhui.util.http.HttpRequest;
import com.yuelvhui.util.http.HttpResponse;
import com.yuelvhui.util.string.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @program: utour_user
 * @description: Token拦截器
 * @author: Mr.Wang
 * @create: 2019-05-14 11:38
 **/
public class ManagerInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(ManagerInterceptor.class);

    @Value("${pf.permissions.manager}")
    private String managerTokenIp;

    @Value("${systems.id}")
    private String systemsId;
    @Value("${systems.key}")
    private String systemsKey;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        String token = httpServletRequest.getHeader(Constants.HTTP_HEADER_AUTHORIZATION);// 从 http 请求头中取出 token
        // 如果不是映射到方法直接通过
        if (!(object instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();
        //检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(ManagerToken.class)) {
            ManagerToken managerToken = method.getAnnotation(ManagerToken.class);
            if (managerToken.required()) {
                // 执行认证
                if (StringUtil.isBlank(token) || !token.startsWith(Constants.AUTHORIZATION_PREFIX_SYS)) {
                    throw new ServiceException(ErrorCode.TokenVerificationFailure.getCode(), "无请求头或请求头签名校验失败");
                }

                //截取到用户的token
                token = token.substring(Constants.AUTHORIZATION_PREFIX_SYS.length());
                String payToken = SignUtil.getSysToken(systemsId, systemsKey);

                HttpResponse response = getResponse(managerTokenIp, token, payToken);
                //HttpResponse response = smsRequest(token, managerTokenIp);
                if (response.getStatus() == 200) {
//                    logger.error("调用权限：{},{}", token, response.bodyBytes());
                    JSONObject json = JSON.parseObject(response.bodyBytes(), JSONObject.class);
                    if (StringUtils.isNotEmpty(json.getString("response")) && json.getBoolean("response")) {
                        return true;
                    } else {
                        throw new ServiceException(json.getString("resultCode"), json.getString("resultMessage"));
                    }
                }
                throw new ServiceException(ErrorCode.DataRemoteCallFailure.getCode(), ErrorCode.DataRemoteCallFailure.getMessage());
            }
        }
        return true;
    }

    private static HttpResponse smsRequest(String json, String url) {
        try {
            return HttpRequest.post(url)
                    .contentType(Constants.CONTENT_TYPE_JSON)
                    .charset(Constants.ENCODING_UTF8)
                    .body(json)
                    .execute();
        } catch (Exception e) {
            throw new ServiceException(ErrorCode.DataRemoteCallFailure.getCode(), "请求头签名校验失败，请重试");
        }
    }


    private static HttpResponse getResponse(String url, String token, String payToken) {
        try {
            return HttpRequest.get(StringUtil.append(url, "?token=" + token))
                    .header(Constants.HTTP_HEADER_AUTHORIZATION, payToken)
                    .charset(Constants.ENCODING_UTF8)
                    .execute();
        } catch (Exception e) {
            throw new ServiceException(ErrorCode.DataRemoteCallFailure.getCode(), "请求头签名校验失败，请重试");
        }
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
