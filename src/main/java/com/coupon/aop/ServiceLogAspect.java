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

/**
 * AOP切面
 *
 * @author wangjun
 */
@Order(2)
@Aspect
@Component
public class ServiceLogAspect {

    private final Logger logger = LoggerFactory.getLogger(ServiceLogAspect.class);

    /**
     * 定义切点 ServicePointcut
     */
    @Pointcut("execution(public * com.coupon.service..*.*(..))")//切入点描述 这个是service包的切入点
    public void LogServicePointcut() {
    }

    @Before("LogServicePointcut()") //在切入点的方法run之前要干的
    public void logBeforeService(JoinPoint joinPoint) {

        //类方法
        logger.info("CLASS_METHOD={}", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        //参数
        logger.info("CLASS_METHOD_ARGS={}", joinPoint.getArgs());
    }

    @AfterReturning(returning = "retService", pointcut = "LogServicePointcut()")
    public void doAfterServiceReturning(Object retService) throws Throwable {
        // 处理完请求，返回内容
        logger.info("METHOD_RESPONSE : " + retService);
    }

}
