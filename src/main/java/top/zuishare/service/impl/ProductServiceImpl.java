package top.zuishare.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import top.zuishare.constants.Constants;
import top.zuishare.dao.ProductDao;
import top.zuishare.service.ProductService;
import top.zuishare.spi.model.Product;
import top.zuishare.spi.util.RedisUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

    @Override
    public List<Product> listHotProducts(int limit) {
        List<Product> products = null;
        try{
            String productStr = stringRedisTemplate.opsForValue().get(RedisUtil.getHotProductsKey());
            //json 反序列化
            products = gson.fromJson(productStr,
                    new TypeToken<ArrayList<Product>>() {
                    }.getType());
            if (CollectionUtils.isEmpty(products)) {
                //2、再DB中获取
                products = getHotProductsList(limit);
                logger.info("get index hot products from db");
            }else{
                logger.info("get index hot products from redis");
            }
        }catch (Exception e){
            logger.error("redis happend error.", e);
            products = getHotProductsList(limit);
        }
        logger.info("query index hot products => {}", products);
        return products;
    }


    private List<Product> getHotProductsList(int limit) {
        List<Product> products = productDao.getHotProducts(limit);
        // 设置过期时间30天
        if (!CollectionUtils.isEmpty(products)) {
            stringRedisTemplate.opsForValue()
                    .set(RedisUtil.getHotProductsKey(),
                            gson.toJson(products), Constants.TIMEOUTDAYS, TimeUnit.DAYS);
        }
        return products;
    }

    @Override
    public List<Product> listProducts(int limit, int pageNo){
    	List<Product> products = null;
    	int start = (pageNo-1) * limit;
    	try {
            Set<String> productIdsSet = stringRedisTemplate.opsForZSet().reverseRange(
            		RedisUtil.getProductsKey(), start, start + limit-1);
            if (CollectionUtils.isEmpty(productIdsSet)) {
                products = getProductsList(start, limit);
                logger.info("get products from db, start=>{}, limit=>{}, products size=>{}", 
                		start, limit, products == null ? 0 : products.size());
            }else{
                logger.info("get product id set from redis, start=>{}, limit=>{}, id set=>{}", start, limit, productIdsSet);
            	products = new ArrayList<>(limit);
            	List<String> keys = new ArrayList<>(limit);
                for(String productStr : productIdsSet) {
                    keys.add(RedisUtil.getProductDetailKey(Integer.valueOf(productStr)));
                }
            	List<String> productsList = stringRedisTemplate.opsForValue().multiGet(keys);
            	for(String productStr : productsList) {
            		products.add(gson.fromJson(productStr, Product.class));
            	}
                logger.info("get products from redis , start=>{}, limit=>{}, products size=>{}",
                		start, limit, products == null ? 0 : products.size());
            }
    	}catch(Exception e) {
    		logger.error("redis happend error.", e);
            products = getProductsList(start, limit);
    	}
        logger.info("listProducts result => {}", products);
    	return products;
    }

    @Override
    public long countProducts(){
        return stringRedisTemplate.opsForZSet().size(RedisUtil.getProductsKey());
    }

    @Override
    public Product loadProduct(int productId) {
    	//1. query from redis
    	Product product = null;
    	String productStr = stringRedisTemplate.opsForValue().get(RedisUtil.getProductDetailKey(productId));
    	if(StringUtils.isBlank(productStr)) {
    		//2. query from db, and set to redis
    		product = loadProductFromDb(productId);
    	}else {
    		product = gson.fromJson(productStr, Product.class);
    	}
    	return product;
    }

    private List<Product> getProductsList(int start, int limit){
        List<Product> products = productDao.getProductsList();
        Set<TypedTuple<String>> tuples = new HashSet<>();
        if(!CollectionUtils.isEmpty(products)) {
	        for(Product proc : products) {
	        	tuples.add(new DefaultTypedTuple<>(String.valueOf(proc.getId()), proc.getPriority() * 1.0));
	        }
	        // 设置过期时间30天
	        if (!CollectionUtils.isEmpty(products)) {
	        	stringRedisTemplate.opsForZSet().add(RedisUtil.getProductsKey(), tuples);
	        	stringRedisTemplate.expire(RedisUtil.getProductsKey(), Constants.TIMEOUTDAYS, TimeUnit.DAYS);
	        }
	        return products.subList(start, products.size()<limit ? products.size() : limit);
        }
        return null;
    }
    
    private Product loadProductFromDb(int productId) {
    	Product product = productDao.loadProduct(productId);
    	stringRedisTemplate.opsForValue().set(RedisUtil.getProductDetailKey(productId), gson.toJson(product), Constants.TIMEOUTDAYS, TimeUnit.DAYS);
    	return product;
    }
    
    private List<Product> queryProductsByCateIdFromDb(int cateId, int start, int limit){
    	List<Product> products = productDao.queryProductsByCateId(cateId);
    	Set<TypedTuple<String>> tuples = new HashSet<>();
    	if(!CollectionUtils.isEmpty(products)) {
    		for(Product proc : products) {
	        	tuples.add(new DefaultTypedTuple<>(String.valueOf(proc.getId()), proc.getPriority() * 1.0));
	        }
    		// 设置过期时间30天
	        if (!CollectionUtils.isEmpty(products)) {
	        	stringRedisTemplate.opsForZSet().add(RedisUtil.getCateProductKey(cateId), tuples);
	        	stringRedisTemplate.expire(RedisUtil.getCateProductKey(cateId), Constants.TIMEOUTDAYS, TimeUnit.DAYS);
	        }
	        return products.subList(start, products.size()<limit ? products.size() : limit);
    	}
    	return null;
    }

    @Override
    public List<Product> queryProductsByCateId(int cateId, int limit, int pageNo) {
        List<Product> products = null;
        int start = (pageNo-1)*limit;
        try {
            //1. 先从缓存中获取
            Set<String> productIdsSet = stringRedisTemplate.opsForZSet().reverseRange(RedisUtil.getCateProductKey(cateId), start, start+limit-1);
            if (CollectionUtils.isEmpty(productIdsSet)) {
                //2. 再DB中获取
                products = queryProductsByCateIdFromDb(cateId, start ,limit);
                logger.info("get products from db, products size=>{}", products == null ? 0 : products.size());
            }else{
            	products = new ArrayList<>(limit);
            	List<String> keys = new ArrayList<>(limit);
            	for(String proid : productIdsSet){
            	    keys.add(RedisUtil.getProductDetailKey(Integer.valueOf(proid)));
                }
            	List<String> productsStr = stringRedisTemplate.opsForValue().multiGet(keys);
            	for(String jsonStr : productsStr) {
            		products.add(gson.fromJson(jsonStr, Product.class));
            	}
                logger.info("get products from redis, products size=>{}", products == null ? 0 : products.size());
            }
        }catch (Exception e){
            logger.error("redis happend error.", e);
            products = queryProductsByCateIdFromDb(cateId, start, limit);
        }
        return products;
    }

    @Override
    public long countProductsByCateId(int cateId){
    	return stringRedisTemplate.opsForZSet().size(RedisUtil.getCateProductKey(cateId));
    }

}
