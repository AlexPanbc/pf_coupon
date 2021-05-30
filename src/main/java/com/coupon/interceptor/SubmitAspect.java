package com.coupon.interceptor;

import com.alibaba.fastjson.JSON;
import com.coupon.interfaces.NoRepeatSubmit;
import com.yuelvhui.util.exception.ServiceException;
import com.coupon.utils.Constants;
import com.coupon.utils.ErrorCode;
import com.coupon.utils.redis.RedisService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.concurrent.TimeUnit;

/**
 * @author: wangxm
 * @date: 2019/12/27 21:24
 */
@Aspect
@Configuration
public class SubmitAspect {

    private static final Logger logger = LoggerFactory.getLogger(SubmitAspect.class);

    @Autowired
    private RedisService redisService;

    @Around("execution(public * *(..)) && @annotation(com.coupon.interfaces.NoRepeatSubmit)")
    public Object interceptor(ProceedingJoinPoint pjp) {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String key = "";
        if (Constants.CONTENT_TYPE_JSON.equals(request.getContentType())) {
            Object[] parameterValues = pjp.getArgs();
            key = getJsonKey(signature, parameterValues, request);
        } else {
            Method method = signature.getMethod();
            key = getKey(method, request);
        }
        if (!redisService.exists(key)) {
            try {
                redisService.put(key, request.getSession().getId(), 5, TimeUnit.SECONDS);
                return pjp.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
                throw new ServiceException(ErrorCode.ServiceException.getCode(), throwable.getMessage());
            }
        } else {
            logger.info("请勿重复提交");
            throw new ServiceException(ErrorCode.DataRepetition.getCode(), ErrorCode.DataRepetition.getMessage());
        }
    }

    private String getJsonKey(MethodSignature signature, Object[] parameterValues, HttpServletRequest request) {
        Method method = signature.getMethod();
        NoRepeatSubmit noRepeatSubmit = method.getAnnotation(NoRepeatSubmit.class);
        String key = noRepeatSubmit.key();
        String name = method.getName();
        boolean params = noRepeatSubmit.params();
        String split = noRepeatSubmit.split();
        key = key + split + name;
        if (params) {
            String[] parameterNames = signature.getParameterNames();
            for (int i = 0; i < parameterValues.length; i++) {
                try {
                    String s = JSON.toJSONString(parameterValues[i]);
                    key = key + split + parameterNames[i] +split + s;
                } catch (Exception e) {
                    continue;
                }
            }
        }
        return key;
    }


    private String getKey(Method method, HttpServletRequest request) {
        NoRepeatSubmit noRepeatSubmit = method.getAnnotation(NoRepeatSubmit.class);
        String key = noRepeatSubmit.key();
        String name = method.getName();
        boolean params = noRepeatSubmit.params();
        String split = noRepeatSubmit.split();
        key = key + split + name;
        if (params) {
            String[] splitParams = noRepeatSubmit.splitParams();
            if (splitParams != null && splitParams.length > 0) {
                for (String splitParam : splitParams) {
                    key = key + split + request.getParameter(splitParam);
                }
            } else {
                Enumeration enu = request.getParameterNames();
                while (enu.hasMoreElements()) {
                    String paraName = (String) enu.nextElement();
                    key = key + split + request.getParameter(paraName);
                }
            }
        }
        return key;
    }
}
