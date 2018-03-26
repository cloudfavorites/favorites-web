package com.favorites.service;

/**
 * @Description:
 * @Auth: yuyang
 * @Date: 2017/2/24 16:10
 * @Version: 1.0
 **/
public interface RedisService {

    public void  set(String key,String value);

    public String get(String key);

    public void  setObject(String key,Object value);

    public Object getObject(String key);

    public boolean expire(String key, long timeout);

    public void delete(String key);

}
