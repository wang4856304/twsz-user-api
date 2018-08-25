package com.twsz.service.redis;

public interface RedisService {
    boolean set(String key, String value);
    boolean set(String key, String value, int expire);
    boolean hessianSet(String key, Object value);
    boolean hessianSet(String key, Object value, int expire);
    boolean hSet(String key, String filed, String value);
    boolean hSet(String key, String filed, Object value);
    String get(String key);
    <T> T hessianGet(String key);
    String hGet(String key, String filed);
    <T> T hessianHGet(String key, String filed);
    boolean del(String key);
    boolean hessianDel(String key);
}
