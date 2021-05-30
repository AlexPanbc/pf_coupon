package com.coupon.interfaces;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @program: pf_coupon
 * @description: 系统token
 * @author: Mr.Wang
 * @create: 2019-07-02 20:29
 **/
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ManagerToken {

    boolean required() default true;
}
