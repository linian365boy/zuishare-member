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
import top.zuishare.dao.CategoryDao;
import top.zuishare.service.CategoryService;
import top.zuishare.spi.model.Advertisement;
import top.zuishare.spi.model.Category;
import top.zuishare.spi.util.RedisUtil;

/**
 * @author niange
 * @ClassName: CategoryServiceImpl
 * @desp:
 * @date: 2018/6/24 上午11:17
 * @since JDK 1.7
 */

@Service
public class CategoryServiceImpl implements CategoryService {
	
	private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);
    @Autowired
    public CategoryDao categoryDao;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private Gson gson;

    @Override
    public List<Category> queryCategory(){
    	List<Category> cates = null;
        //1、从缓存中获取
    	try {
    		String categoriesStr = stringRedisTemplate.opsForValue().get(RedisUtil.getProductCatesKey());
    		//json 反序列化
    		cates = gson.fromJson(categoriesStr,
                    new TypeToken<ArrayList<Advertisement>>() {
                    }.getType());
            if (CollectionUtils.isEmpty(cates)) {
                //2、再DB中获取
            	cates = getCategoryFromDb();
                logger.info("get product categories from db, cates.size=>{}", cates == null ? 0:cates.size());
            }else{
                logger.info("get product categories from redis, cates.size=>{}", cates == null ? 0:cates.size());
            }
    	}catch(Exception e) {
    		logger.error("redis happend error.", e);
    		cates = getCategoryFromDb();
    	}
    	logger.info("get all cates => {}", cates);
        return cates;
    }
    
    private List<Category> getCategoryFromDb(){
    	List<Category> cates =  categoryDao.queryCategory();
    	// 设置过期时间30天
        if (!CollectionUtils.isEmpty(cates)) {
            stringRedisTemplate.opsForValue()
                    .set(RedisUtil.getProductCatesKey(),
                            gson.toJson(cates), Constants.TIMEOUTDAYS, TimeUnit.DAYS);
        }
        return cates;
    }

}
