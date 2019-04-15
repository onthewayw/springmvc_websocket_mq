package com.wang.util;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Package ${package_name}$
 * @description:
 * @author: wangjiangtao
 * @create: 2018-11-26 18:14
 **/
public class RedisCacheUtil {
    /**
     * RedisTemplate是一个简化Redis数据访问的一个帮助类，
     * 此类对Redis命令进行高级封装，通过此类可以调用ValueOperations和ListOperations等等方法。
     */
    private RedisTemplate redisTemplate;

    public RedisTemplate getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public RedisCacheUtil() {
        System.out.println("qweqwewe");
    }

    /*
     * 获取web在线人员
     * */
    @SuppressWarnings("unchecked")
  /*  public Map<String, UserLoginLog> getOnlineUsers() {
        Map<String, UserLoginLog> onlineUsers = Collections.synchronizedMap(new HashMap<String, UserLoginLog>(64));
        String keypre = "*com:zuma:user:onlineUserList*";
        // 去redis中根据sessionId获取
        Set set = redisTemplate.keys(keypre);
        ArrayList list = new ArrayList(set);
        List<String> dataList = redisTemplate.opsForValue().multiGet(list);
        for (int i = 0; i < dataList.size(); i++) {
            String data = dataList.get(i);
            if (null != dataList.get(i)) {
                @SuppressWarnings("unchecked")
                UserLoginLog jo = JSONObject.parseObject(data, UserLoginLog.class);
                if (jo != null) {
                    // System.out.println("用户登录后产生的session增加用户数");
                    // 存储用户hash
                    onlineUsers.put(jo.getfId().toString(), jo);
                }
            }
        }
        return onlineUsers;
    }*/


    public <T> ValueOperations<String, T> setCacheObject(String key, T value, Long timeout) {
        System.out.println(key + "*****" + value.toString());
        ValueOperations<String, T> operation = redisTemplate.opsForValue();
        operation.set(key, value, timeout, TimeUnit.SECONDS);
        return operation;
    }

    public <T> T getCacheObject(String key) {
        ValueOperations<String, T> operation = redisTemplate.opsForValue();
        return operation.get(key);
    }

    //使用scan进行查询 效率更好
    public Set<String> getCacheObjectAll(String key) {
        Set<String> keys = redisTemplate.keys(key);
        return keys;
    }

    // 批量查询
    public <T> List<T> getCacheByIds(List<String> keys) {
        List<T> dataList = redisTemplate.opsForValue().multiGet(keys);
        return dataList;
    }

    public <T> ListOperations<String, T> setCacheList(String key, List<T> dataList) {
        ListOperations listOperation = redisTemplate.opsForList();
        if (null != dataList) {
            int size = dataList.size();

            for (int i = 0; i < size; ++i) {
                listOperation.rightPush(key, dataList.get(i));
            }
        }
        return listOperation;
    }

    public <T> List<T> getCacheList(String key) {
        List<T> dataList = new ArrayList();
        ListOperations<String, T> listOperation = redisTemplate.opsForList();
        Long size = listOperation.size(key);

        for (int i = 0; (long) i < size; ++i) {
            dataList.add(listOperation.leftPop(key));
        }
        return dataList;
    }

    public <T> List<T> getCacheListIndex(String key, long arg1, long arg2) {
        new ArrayList();
        ListOperations<String, T> listOperation = redisTemplate.opsForList();
        List<T> dataList = listOperation.range(key, arg1, arg2);
        return dataList;
    }

    public <T> BoundSetOperations<String, T> setCacheSet(String key, Set<T> dataSet) {
        BoundSetOperations<String, T> setOperation = redisTemplate.boundSetOps(key);
        Iterator it = dataSet.iterator();

        while (it.hasNext()) {
            setOperation.add((T) new Object[]{it.next()});
        }
        return setOperation;
    }

    /*public Set<T> getCacheSet(String key) {
        Set<T> dataSet = new HashSet();
        BoundSetOperations<String, T> operation = redisTemplate.boundSetOps(key);
        Long size = operation.size();

        for (int i = 0; (long) i < size; ++i) {
            dataSet.add(operation.pop());
        }
        return dataSet;
    }*/

    public <T> HashOperations<String, String, T> setCacheMap(String key, Map<String, T> dataMap) {
        HashOperations hashOperations = redisTemplate.opsForHash();
        if (null != dataMap) {
            Iterator var4 = dataMap.entrySet().iterator();

            while (var4.hasNext()) {
                Map.Entry<String, T> entry = (Map.Entry) var4.next();
                hashOperations.put(key, entry.getKey(), entry.getValue());
            }
        }
        return hashOperations;
    }

    public <T> Map<String, T> getCacheMap(String key) {
        Map<String, T> map = redisTemplate.opsForHash().entries(key);
        return map;
    }

    public <T> HashOperations<String, Integer, T> setCacheIntegerMap(String key, Map<Integer, T> dataMap) {
        HashOperations hashOperations = redisTemplate.opsForHash();
        if (null != dataMap) {
            Iterator iterator = dataMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<Integer, T> entry = (Map.Entry) iterator.next();
                hashOperations.put(key, entry.getKey(), entry.getValue());
            }
        }
        return hashOperations;
    }

    public <T> Map<Integer, T> getCacheIntegerMap(String key) {
        Map<Integer, T> map = redisTemplate.opsForHash().entries(key);
        return map;
    }
}
