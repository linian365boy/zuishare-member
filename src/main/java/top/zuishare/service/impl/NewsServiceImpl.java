package top.zuishare.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
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
import top.zuishare.dao.NewsDao;
import top.zuishare.service.NewsService;
import top.zuishare.spi.model.News;
import top.zuishare.spi.util.RedisUtil;

/**
 * @author niange
 * @ClassName: NewsServiceImpl
 * @desp:
 * @date: 2018/5/20 下午9:34
 * @since JDK 1.7
 */

@Service
public class NewsServiceImpl implements NewsService {
	private static final Logger logger = LoggerFactory.getLogger(NewsServiceImpl.class);
    @Autowired
    private NewsDao newsDao;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private Gson gson;

    @Override
    public List<News> listNews(int limit, int pageNo) {
    	List<News> news = null;
    	int start = (pageNo-1) * limit;
    	try {
            Set<String> newsIdSets = stringRedisTemplate.opsForZSet().range(
            		RedisUtil.getNewsKey(), start, start + limit-1);
            if (CollectionUtils.isEmpty(newsIdSets)) {
            	news = getNewsList(start, limit);
                logger.info("get news from db, start=>{}, limit=>{}, news size=>{}", 
                		start, limit, news == null ? 0 : news.size());
            }else{
            	news = new ArrayList<>(limit);
            	Set<String> keys = new HashSet<>(limit);
            	for(String newsId : newsIdSets){
					keys.add(RedisUtil.getNewsDetailKey(Integer.valueOf(newsId)));
				}
            	List<String> newsList = stringRedisTemplate.opsForValue().multiGet(keys);
            	for(String jsonStr : newsList) {
            		news.add(gson.fromJson(jsonStr, News.class));
            	}
                logger.info("get news from redis , start=>{}, limit=>{}, news size=>{}",
                		start, limit, news == null ? 0 : news.size());
            }
    	}catch(Exception e) {
    		logger.error("redis happend error.", e);
    		news = getNewsList(start, limit);
    	}
    	logger.info("listNews return news=>{}", news);
    	return news;
    }

    @Override
    public News loadNews(int newsId) {
        return loadNewsFromDb(newsId);
    }
    
    private List<News> getNewsList(int start, int limit){
    	List<News> newsList = newsDao.getNewsList();
        Set<TypedTuple<String>> tuples = new HashSet<>();
        if(!CollectionUtils.isEmpty(newsList)) {
	        for(News news : newsList) {
				long autoId = stringRedisTemplate.opsForValue().increment(RedisUtil.getGenerateIncreaseKey() , 1);
	        	tuples.add(new DefaultTypedTuple<String>(String.valueOf(news.getId()), System.currentTimeMillis()+autoId * 1.0));
	        }
	        // 设置过期时间30天
	        if (!CollectionUtils.isEmpty(newsList)) {
	        	stringRedisTemplate.opsForZSet().add(RedisUtil.getNewsKey(), tuples);
	        	stringRedisTemplate.expire(RedisUtil.getNewsKey(), Constants.TIMEOUTDAYS, TimeUnit.DAYS);
	        }
	        return newsList.subList(start, newsList.size()<limit ? newsList.size() : limit);
        }
        return null;
    }
    
    private News loadNewsFromDb(int newsId) {
    	News news = null;
    	String newsStr = stringRedisTemplate.opsForValue().get(RedisUtil.getNewsDetailKey(newsId));
    	if(StringUtils.isBlank(newsStr)) {
    		news = newsDao.loadNews(newsId);
    		stringRedisTemplate.opsForValue().set(RedisUtil.getNewsDetailKey(newsId), gson.toJson(news), Constants.TIMEOUTDAYS, TimeUnit.DAYS);
    		logger.info("load news from db, news data=>{}", news);
    	}else {
    		news = gson.fromJson(newsStr, News.class);
    		logger.info("load news from redis, news data=>{}", news);
    	}
    	return news;
    }

    @Override
    public long countNews(){
        return stringRedisTemplate.opsForZSet().size(RedisUtil.getNewsKey());
    }

}
