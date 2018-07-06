package top.zuishare.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.google.gson.Gson;

import top.zuishare.constants.Constants;
import top.zuishare.dao.ProductDao;
import top.zuishare.service.ProductService;
import top.zuishare.spi.model.Product;
import top.zuishare.util.RedisUtil;

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

    @Override
    public List<Product> listProducts(int limit, int pageNo){
        return productDao.getProducts(limit, (pageNo-1) * limit);
    }

    @Override
    public long countProducts(){
        return productDao.countProducts();
    }

    @Override
    public List<Product> queryIndexProduct(int limit) {
        List<Product> products = null;
        //1、从缓存中获取
        try {
            //先从缓存中获取
            Set<String> productSets = stringRedisTemplate.opsForZSet().range(RedisUtil.getProductsKey(), 0, limit-1);
            if (CollectionUtils.isEmpty(productSets)) {
                //2、再DB中获取
                products = getIndexProductsList(limit);
                logger.info("get products from db");
            }else{
            	products = new ArrayList<>(limit);
            	for(String productStr : productSets) {
            		products.add(gson.fromJson(productStr, Product.class));
            	}
                logger.info("get products from redis");
            }
        }catch (Exception e){
            logger.error("redis happend error.", e);
            products = getIndexProductsList(limit);
        }
        return products;
    }

    @Override
    public Product loadProduct(int productId) {
        return productDao.loadProduct(productId);
    }

    private List<Product> getIndexProductsList(int limit){
        List<Product> products = productDao.getProductsList();
        Set<TypedTuple<String>> tuples = new HashSet<>();
        if(!CollectionUtils.isEmpty(products)) {
	        for(Product proc : products) {
	        	tuples.add(new DefaultTypedTuple<String>(gson.toJson(proc), System.nanoTime() * 1.0));
	        }
	        // 设置过期时间30天
	        if (!CollectionUtils.isEmpty(products)) {
	        	stringRedisTemplate.opsForZSet().add(RedisUtil.getProductsKey(), tuples);
	        	stringRedisTemplate.expire(RedisUtil.getProductsKey(), Constants.TIMEOUTDAYS, TimeUnit.DAYS);
	        }
	        return products.subList(0, products.size()<limit ? products.size() : limit);
        }
        return null;
    }

    @Override
    public List<Product> queryProductsByCateId(int cateId, int limit, int pageNo) {
        return productDao.queryProductsByCateId(cateId, limit, (pageNo-1) * limit);
    }

    @Override
    public long countProductsByCateId(int cateId){
        return productDao.countProductsByCateId(cateId);
    }

}
