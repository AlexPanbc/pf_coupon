package com.coupon.config;

import com.yuelvhui.util.string.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @program: utour_user
 * @description: Redis配置
 * @author: Mr.Wang
 * @create: 2019-05-14 15:19
 **/
@Configuration
public class RedisConfig extends CachingConfigurerSupport {
    private Logger logger = LoggerFactory.getLogger(RedisConfig.class);

    @Autowired
    private RedisConfigProperties redisConfigProperties;

    @Bean(name = "jedisPoolConfig")
    @ConfigurationProperties(prefix = "spring.redis.pool-config")
    public JedisPoolConfig getRedisConfig() {
        JedisPoolConfig config = new JedisPoolConfig();
        return config;
    }

    @Bean(name = "jedisPool")
    public JedisPool jedisPool(@Qualifier(value = "jedisPoolConfig") final JedisPoolConfig jedisPoolConfig) {
        logger.info("Jedis Pool build start ");
        String host = redisConfigProperties.getHost();
        Integer timeout = redisConfigProperties.getTimeout();
        int port = redisConfigProperties.getPort();
        String password = redisConfigProperties.getPassword();
        int database = redisConfigProperties.getDatabase();
        String instanceid = redisConfigProperties.getInstanceid();
        JedisPool jedisPool = null;
        if (StringUtil.isNotEmpty(instanceid)) {
            jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout, password, database, instanceid);
        } else {
            jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout, password, database);
        }
        logger.info("Jedis Pool build success  host={} , port={} , instanceid={}", host, port, instanceid);
        return jedisPool;
    }
}
