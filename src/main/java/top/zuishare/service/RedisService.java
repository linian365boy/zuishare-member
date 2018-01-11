package top.zuishare.service;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import top.zuishare.constants.Constants;

import java.util.concurrent.TimeUnit;

/**
 * @author niange
 * @ClassName: RedisService
 * @desp: 操作redis缓存
 * @date: 2018/1/5 下午11:14
 * @since JDK 1.7
 */
@Service
public class RedisService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 分布式锁解决并发，幂等性
     * @param key
     * @return
     */
    public boolean getDisLock(String key){
        boolean disLockFlag = stringRedisTemplate.opsForValue().setIfAbsent(key, String.valueOf(NumberUtils.LONG_ZERO));
        stringRedisTemplate.expire(key, Constants.TIMEOUTSECONDS, TimeUnit.SECONDS);
        return disLockFlag;
    }

}
