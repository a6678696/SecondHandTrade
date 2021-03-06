package com.ledao.util;

import com.google.gson.Gson;
import com.ledao.entity.ConfigProperties;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * Redis工具类
 *
 * @author LeDao
 * @company
 * @create 2022-01-17 1:21
 */
@Component
public class RedisUtil {

    /**
     * 维护一个本类的静态变量
     */
    private static RedisUtil redisUtil;

    @Resource
    private ConfigProperties configProperties;

    /**
     * 并使用@PostConstruct注解标记工具类,初始化Bean
     */
    @PostConstruct
    public void init() {
        redisUtil = this;
        redisUtil.configProperties = this.configProperties;
    }

    /**
     * 获取Redis连接
     *
     * @return
     */
    private static JedisPool getRedisLink() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(100);
        jedisPoolConfig.setMaxIdle(10);
        return new JedisPool(jedisPoolConfig, redisUtil.configProperties.getHost(), redisUtil.configProperties.getPort());
    }

    /**
     * 关闭Redis连接
     *
     * @param jedisPool
     * @param jedis
     */
    private static void closeRedis(JedisPool jedisPool, Jedis jedis) {
        jedis.close();
        jedisPool.close();
    }

    /**
     * 获取Jedis
     *
     * @param jedisPool
     * @return
     */
    private static Jedis getJedis(JedisPool jedisPool) {
        Jedis jedis = jedisPool.getResource();
        jedis.auth(redisUtil.configProperties.getPassword());
        return jedis;
    }

    /**
     * Java实体转化为json
     *
     * @param o
     * @return
     */
    public static String entityToJson(Object o) {
        Gson gson = new Gson();
        return gson.toJson(o);
    }

    /**
     * json转化为Java实体
     *
     * @param json
     * @param o
     * @return
     */
    public static Object jsonToEntity(String json, Object o) {
        Gson gson = new Gson();
        o = gson.fromJson(json, o.getClass());
        return o;
    }

    /**
     * 设置key
     *
     * @param key
     * @param value
     * @return
     */
    public static boolean setKey(String key, String value) {
        JedisPool jedisPool = getRedisLink();
        Jedis jedis = getJedis(jedisPool);
        String result = jedis.set(key, value);
        closeRedis(jedisPool, jedis);
        return "OK".equals(result);
    }

    /**
     * 获取key对应的值
     *
     * @param key
     * @return
     */
    public static String getKeyValue(String key) {
        JedisPool jedisPool = getRedisLink();
        Jedis jedis = getJedis(jedisPool);
        String result = jedis.get(key);
        closeRedis(jedisPool, jedis);
        return result;
    }

    /**
     * key是否存在
     *
     * @param key
     * @return
     */
    public static boolean existKey(String key) {
        JedisPool jedisPool = getRedisLink();
        Jedis jedis = getJedis(jedisPool);
        boolean result = jedis.exists(key);
        closeRedis(jedisPool, jedis);
        return result;
    }

    /**
     * 删除key
     *
     * @param key
     * @return
     */
    public static boolean delKey(String key) {
        JedisPool jedisPool = getRedisLink();
        Jedis jedis = getJedis(jedisPool);
        Long result;
        //key存在就删除
        if (existKey(key)) {
            result = jedis.del(key);
        } else {
            result = 0L;
        }
        closeRedis(jedisPool, jedis);
        return result > 0;
    }

    /**
     * 从右边添加元素
     *
     * @param key
     * @param value
     * @return
     */
    public static boolean listRightPush(String key, String value) {
        JedisPool jedisPool = getRedisLink();
        Jedis jedis = getJedis(jedisPool);
        Long result = jedis.rpush(key, value);
        closeRedis(jedisPool, jedis);
        return result > 0;
    }

    /**
     * 从右边弹出元素
     *
     * @param key
     * @return
     */
    public static boolean listRightPop(String key) {
        JedisPool jedisPool = getRedisLink();
        Jedis jedis = getJedis(jedisPool);
        String result = jedis.rpop(key);
        closeRedis(jedisPool, jedis);
        return "OK".equals(result);
    }

    /**
     * 获取list的元素个数
     *
     * @param key
     * @return
     */
    public static Long listLength(String key) {
        JedisPool jedisPool = getRedisLink();
        Jedis jedis = getJedis(jedisPool);
        Long result = jedis.llen(key);
        closeRedis(jedisPool, jedis);
        return result;
    }

    /**
     * 获取全部元素集合
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public static List<String> listRange(String key, Long start, Long end) {
        JedisPool jedisPool = getRedisLink();
        Jedis jedis = getJedis(jedisPool);
        List<String> resultList = jedis.lrange(key, start, end);
        closeRedis(jedisPool, jedis);
        return resultList;
    }

    /**
     * 给指定key设置过期时间(秒)
     *
     * @param key
     * @param seconds
     */
    public static void setKeyTime(String key, int seconds) {
        JedisPool jedisPool = getRedisLink();
        Jedis jedis = getJedis(jedisPool);
        jedis.expire(key, seconds);
        closeRedis(jedisPool, jedis);
    }
}
