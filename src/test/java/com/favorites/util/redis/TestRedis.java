package com.favorites.util.redis;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.favorites.domain.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestRedis {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    
	 @Autowired
	private RedisTemplate redisTemplate;

    @Test
    public void test() throws Exception {
        // 保存字符串
        stringRedisTemplate.opsForValue().set("aaa", "111");
        Assert.assertEquals("111", stringRedisTemplate.opsForValue().get("aaa"));
    }
    
    
    @Test
    public void testObj() throws Exception {
    	BoundValueOperations<String, User> ops = redisTemplate.boundValueOps("favorites");
        ops.set(new User("aa", "aa@126.com", "aa", "aa123456"),1000);
        
        System.out.println("redis ====="+ops.get().getUserName());
        System.out.println("Expire ====="+ops.getExpire());
        
        
        redisTemplate.opsForValue().set("test", new User("aa", "aa@126.com", "aa", "aa123456"),1000);
        
        
        System.out.println("test   ====="+redisTemplate.opsForValue().get("test"));

    }


}