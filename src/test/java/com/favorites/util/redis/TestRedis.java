/*
package com.favorites.util.redis;

import com.favorites.domain.User;
import com.favorites.service.RedisService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestRedis {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    
    @Autowired
	private RedisTemplate redisTemplate;

    @Autowired
    private RedisService redisService;

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

    @Test
    public void testRedisService(){
        redisService.set("test","123");
        System.out.println("redis test: "+redisService.get("test"));
        redisService.setObject("user",new User("aa@126.com", "aa", "aa123456", "aa"));
        User user = (User) redisService.getObject("user");
        System.out.println("redis user: "+user.toString());
        boolean f = redisService.expire("user",1);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("redis expire: "+f);
        System.out.println("redis expire user: "+ redisService.getObject("user"));
        redisService.delete("collector");
        System.out.println("redis delete test: "+redisService.get("collector"));
    }

}*/
