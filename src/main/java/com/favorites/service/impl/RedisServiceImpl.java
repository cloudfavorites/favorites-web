/*
package com.favorites.service.impl;

import com.favorites.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

*/
/**
 * @Description:
 * @Auth: yuyang
 * @Date: 2017/2/24 16:11
 * @Version: 1.0
 **//*

@Service
public class RedisServiceImpl implements RedisService{


    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void set(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    @Override
    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    @Override
    public void setObject(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public Object getObject(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public boolean expire(String key, long timeout) {
        return redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
    }

    @Override
    public void delete(String key){
        redisTemplate.delete(key);
    }

}
*/
