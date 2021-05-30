package com.coupon.config.rabbit;

import com.coupon.utils.rabbitmq.BindingKeyBuilder;
import com.coupon.utils.rabbitmq.ExchangeKeyBuilder;
import com.coupon.utils.rabbitmq.QueueKeyBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: pf_coupon
 * @description: 队列配置
 * @author: Mr.Wang
 * @create: 2019-08-13 16:07
 **/

@Configuration
public class QueueConfig {

    /**
     * durable="true" 持久化 rabbitmq重启的时候不需要创建新的队列
     * auto-delete 表示消息队列没有在使用时将被自动删除 默认是false
     * exclusive  表示该消息队列是否只在当前connection生效,默认是false
     */
    @Bean
    public Queue waittingCouponBatchQueue() {
        return new Queue(QueueKeyBuilder.waittingCouponBatchQueueKey(), true);
    }


    @Bean
    public Queue activityQueue() {
        return QueueBuilder
                .durable(QueueKeyBuilder.activityKey())
                .withArgument("x-dead-letter-exchange", ExchangeKeyBuilder.diceExchangeKey())
                .withArgument("x-dead-letter-routing-key", BindingKeyBuilder.activityBindingDelayKey())
                .build();
//        return new Queue(QueueKeyBuilder.activityKey(), true);
    }

    @Bean
    public Queue activityDelayQueue() {
        return new Queue(QueueKeyBuilder.activityDelayKey(), true);
    }
}
