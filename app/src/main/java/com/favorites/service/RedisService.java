package com.favorites.service;

/**
 * @Description:
 * @Auth: yuyang
 * @Date: 2017/2/24 16:10
 * @Version: 1.0
 **/
public interface RedisService {

	void set (String key, String value);

	String get (String key);

	void setObject (String key, Object value);

	Object getObject (String key);

	boolean expire (String key, long timeout);

	void delete (String key);

}
