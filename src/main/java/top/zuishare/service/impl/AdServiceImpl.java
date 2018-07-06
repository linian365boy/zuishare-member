package top.zuishare.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import top.zuishare.constants.Constants;
import top.zuishare.dao.AdDao;
import top.zuishare.service.AdService;
import top.zuishare.spi.model.Advertisement;
import top.zuishare.spi.util.RedisUtil;

/**
 * @author niange
 * @ClassName: AdServiceImpl
 * @desp:
 * @date: 2018/5/6 上午11:47
 * @since JDK 1.7
 */
@Service
public class AdServiceImpl implements AdService {

    private static final Logger logger = LoggerFactory.getLogger(AdServiceImpl.class);
    @Autowired
    private AdDao adDao;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private Gson gson;

    @Override
    public List<Advertisement> queryIndexAd(int maxLimit) {
        List<Advertisement> ads = null;
        //1、从缓存中获取
        try{
            //先从缓存中获取
            String adsStr = stringRedisTemplate.opsForValue().get(RedisUtil.getAdsKey());
            //json 反序列化
            ads = gson.fromJson(adsStr,
                    new TypeToken<ArrayList<Advertisement>>() {
                    }.getType());
            if (CollectionUtils.isEmpty(ads)) {
                //2、再DB中获取
                ads = queryAdsFromDB(maxLimit);
                logger.info("get ads from db, ads.size=>{}", ads == null ? 0:ads.size());
            }else{
                logger.info("get ads from redis, ads.size=>{}", ads == null ? 0:ads.size());
            }
        }catch (Exception e){
            logger.error("redis happend error.", e);
            ads = queryAdsFromDB(maxLimit);
        }
        logger.info("get all index ads => {}", ads);
        return ads;
    }


    private List<Advertisement> queryAdsFromDB(int maxLimit){
        List<Advertisement> ads = adDao.getIndexAd(maxLimit);
        // 设置过期时间30天
        if (!CollectionUtils.isEmpty(ads)) {
            stringRedisTemplate.opsForValue()
                    .set(RedisUtil.getAdsKey(),
                            gson.toJson(ads), Constants.TIMEOUTDAYS, TimeUnit.DAYS);
        }
        return ads;
    }

}
