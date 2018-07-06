package top.zuishare.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import top.zuishare.dao.NewsDao;
import top.zuishare.service.NewsService;
import top.zuishare.spi.model.News;

import java.util.List;

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
        return newsDao.getIndexNews(limit, (pageNo-1) * limit);
    }

    @Override
    public News loadNews(int newsId) {
        return newsDao.loadNews(newsId);
    }

    @Override
    public long countNews(){
        return newsDao.countNews();
    }

}
