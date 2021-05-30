package com.coupon.utils.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;

import java.io.IOException;

/**
 * @program: pf_coupon
 * @description: RedisClient
 * @author: Mr.Wang
 * @create: 2019-12-05 17:09
 **/
@Component
public class RedisClient {

    private static final Logger logger = LoggerFactory.getLogger(RedisClient.class);

    public <T> T invoke(JedisPool pool, RedisClientInvoker<T> clients) {
        T obj = null;
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            obj = clients.invoke(jedis);
        } catch (JedisException | IOException ex) {
            logger.error(ex.getMessage(), ex);
        } finally {
            if (jedis != null) {
                if (jedis.isConnected())
                    jedis.close();
            }
        }
        return obj;
    }
}
