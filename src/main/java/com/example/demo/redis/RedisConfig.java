package com.example.demo.redis;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;
@Configuration
public class RedisConfig{
    @Bean("JedisPoolConfig")
    public JedisPoolConfig jedisPoolConfig(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        // 10 seconds;
        jedisPoolConfig.setMinEvictableIdleTimeMillis(100000L);
        return jedisPoolConfig;
    }
    @Bean("JedisConnectionFactory")
    public JedisConnectionFactory jedisConnectionFactory(
            @Qualifier("JedisPoolConfig") JedisPoolConfig jedisPoolConfig){
        JedisConnectionFactory jedisConnectionFactdory = new JedisConnectionFactory(jedisPoolConfig);

        return jedisConnectionFactdory;
    }
    @Bean(name = "redisTemplate")
    public RedisTemplate redisTemplateConfig(
            @Qualifier("JedisConnectionFactory") JedisConnectionFactory jedisConnectionFactory){
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        return redisTemplate;
    }
}
