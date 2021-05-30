package com.coupon.config.rabbit;

import com.coupon.utils.rabbitmq.BindingKeyBuilder;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: pf_coupon
 * @description: 绑定关系配置
 * @author: Mr.Wang
 * @create: 2019-08-13 17:56
 **/
@Configuration
public class BindingConfig {

    @Autowired
    private QueueConfig queueConfig;
    @Autowired
    private ExchangeConfig exchangeConfig;

    @Bean
    public Binding waittingCouponBatchBinding() {
        return BindingBuilder.bind(queueConfig.waittingCouponBatchQueue()).to(exchangeConfig.directExchange()).with(BindingKeyBuilder.waittingCouponBatchBindingKey());
    }

    @Bean
    public Binding activityBinding() {
        return BindingBuilder.bind(queueConfig.activityQueue()).to(exchangeConfig.directExchange()).with(BindingKeyBuilder.activityBindingKey());
    }

    @Bean
    public Binding activityBindingDelay() {
        return BindingBuilder.bind(queueConfig.activityDelayQueue()).to(exchangeConfig.directExchange()).with(BindingKeyBuilder.activityBindingDelayKey());
    }
}
