package com.coupon.interfaces;

import java.lang.annotation.*;

/**
 * @author: wangxm
 * @date: 2019/12/27 21:24
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface NoRepeatSubmit {

    /**
     * Key前缀
     */
    String key() default "";

    /**
     * 拼接方式
     */
    String split() default "_";

    /**
     * 参数是否拼接
     */
    boolean params() default false;

    /**
     * 拼接的参数
     */
    String[] splitParams() default {};
}
