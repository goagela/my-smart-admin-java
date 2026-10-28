package com.example.mysabase.module.support.redis;

import com.alibaba.fastjson.JSON;
import com.example.mysabase.common.domian.SystemEnvironment;
import com.example.mysabase.common.enumeration.SystemEnvironmentEnum;
import com.example.mysabase.constant.RedisKeyConst;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private ValueOperations<String,String> redisValueOperations;

    @Resource
    private SystemEnvironment systemEnvironment;

    /**
     * 生成redis key
     * @param prefix
     * @param key
     * @return
     */
    public String generateRedisKey(String prefix, String key) {
        SystemEnvironmentEnum currentEnvironment = systemEnvironment.getCurrentEnvironment();
        return systemEnvironment.getProjectName() + RedisKeyConst.SEPARATOR + currentEnvironment.getValue() +  RedisKeyConst.SEPARATOR + prefix + key;
    }

    /**
     * 设置缓存
     */
    public void set(String key, String value) {
        redisValueOperations.set(key, value);
    }
    public void set(Object key, Object value) {
        String jsonString = JSON.toJSONString(value);
        redisValueOperations.set(key.toString(), jsonString);
    }
    public void set(String key, String value, long second) {
        redisValueOperations.set(key, value, second, TimeUnit.SECONDS);
    }
    public void set(Object key, Object value, long second) {
        String jsonString = JSON.toJSONString(value);
        if (second > 0) {
            redisValueOperations.set(key.toString(), jsonString, second, TimeUnit.SECONDS);
        } else {
            set(key.toString(), jsonString);
        }
    }

    /**
     * 读取缓存
     */
    public String get(String key) {
        return key == null ? null : redisValueOperations.get(key);
    }
    public <T> T getObject(String key, Class<T> clazz) {
        Object json = this.get(key);
        if (json == null) {
            return null;
        }
        T obj = JSON.parseObject(json.toString(), clazz);
        return obj;
    }

    /**
     * 删除缓存
     */
    public void delete(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete((Collection<String>) CollectionUtils.arrayToList(key));
            }
        }
    }
    public void delete(List<String> keyList) {
        if (CollectionUtils.isEmpty(keyList)) {
            return;
        }
        redisTemplate.delete(keyList);
    }
}
