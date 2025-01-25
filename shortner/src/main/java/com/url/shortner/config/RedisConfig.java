package com.url.shortner.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

@Configuration
public class RedisConfig {

    @Value("${spring.data.redis.host}")
    private String redisUrl;

    @Value("${spring.data.redis.port}")
    private int port;

    @Bean
    public Jedis jedis(){
        return new Jedis(redisUrl,port);
    }
}
