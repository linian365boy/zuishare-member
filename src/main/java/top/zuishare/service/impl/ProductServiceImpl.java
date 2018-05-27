package top.zuishare.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import top.zuishare.constants.Constants;
import top.zuishare.dao.ProductDao;
import top.zuishare.service.ProductService;
import top.zuishare.spi.model.Advertisement;
import top.zuishare.spi.model.Product;
import top.zuishare.util.RedisUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author niange
 * @ClassName: ProductServiceImpl
 * @desp:
 * @date: 2018/5/20 下午9:34
 * @since JDK 1.7
 */

@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
    @Autowired
    private ProductDao productDao;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private Gson gson;

    public List<Product> listProducts(int limit){
        return productDao.getIndexProducts(limit);
    }

    @Override
    public List<Product> queryIndexProduct(int limit) {
        List<Product> products = null;
        //1、从缓存中获取
        try {
            //先从缓存中获取
            //先从缓存中获取
            String productsStr = stringRedisTemplate.opsForValue().get(RedisUtil.getIndexProductsKey());
            //json 反序列化
            products = gson.fromJson(productsStr,
                    new TypeToken<ArrayList<Product>>() {
                    }.getType());
            if (CollectionUtils.isEmpty(products)) {
                //2、再DB中获取
                products = getIndexProductsList(limit);
                logger.info("get ads from db");
            }else{
                logger.info("get ads from redis");
            }
        }catch (Exception e){
            logger.error("redis happend error.", e);
            products = getIndexProductsList(limit);
        }
        return products;
    }


    private List<Product> getIndexProductsList(int limit){
        List<Product> products = productDao.getIndexProductsList(limit);
        // 设置过期时间30天
        if (!CollectionUtils.isEmpty(products)) {
            stringRedisTemplate.opsForValue()
                    .set(RedisUtil.getIndexProductsKey(),
                            gson.toJson(products), Constants.TIMEOUTDAYS, TimeUnit.DAYS);
        }
        return products;
    }

}
