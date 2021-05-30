package com.coupon.config;

import com.coupon.interceptor.AuthenticationInterceptor;
import com.coupon.interceptor.ManagerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @program: utour_user
 * @description: Token校验拦截器配置
 * @author: Mr.Wang
 * @create: 2019-05-14 14:28
 **/
@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(managerInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }

    @Bean
    public AuthenticationInterceptor authenticationInterceptor() {
        return new AuthenticationInterceptor();
    }

    @Bean
    public ManagerInterceptor managerInterceptor() {
        return new ManagerInterceptor();
    }

}
