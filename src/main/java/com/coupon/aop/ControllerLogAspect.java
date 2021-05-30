package com.coupon.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * AOP切面
 */
@Order(1)
@Aspect
@Component
public class ControllerLogAspect {

    private final Logger logger = LoggerFactory.getLogger(ControllerLogAspect.class);

    // 定义切点 ControllerPointcut
    @Pointcut("execution(public * com.coupon.controller..*.*(..))")//切入点描述 这个是controller包的切入点
    public void LogControllerPointcut() {
    }

    @Before("LogControllerPointcut()") //在切入点的方法run之前要干的
    public void logBeforeController(JoinPoint joinPoint) {

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //有格式的打印日志，收集日志信息到ES
        logger.info(System.currentTimeMillis() + "|" + request.getRequestURL().toString() + "|" + request.getMethod() + "|" +
                joinPoint.getSignature().getDeclaringTypeName() + "|" + Arrays.toString(joinPoint.getArgs()));
        // 记录下请求内容
        logger.info("REQUEST_URL : " + request.getRequestURL().toString());

        logger.info("HTTP_METHOD : " + request.getMethod());

        logger.info("REQUEST_IP : " + request.getRemoteAddr());

        logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());

        logger.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));
        //类方法
        logger.info("CLASS_METHOD={}", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        //参数
        logger.info("CLASS_METHOD_ARGS={}", joinPoint.getArgs());

    }

    @AfterReturning(returning = "retController", pointcut = "LogControllerPointcut()")
    public void doAfterControllerReturning(Object retController) throws Throwable {
        // 处理完请求，返回内容
        logger.info("API_RESPONSE : " + retController);
    }

}
