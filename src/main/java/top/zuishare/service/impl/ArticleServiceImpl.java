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
import top.zuishare.dao.ArticleDao;
import top.zuishare.service.ArticleService;
import top.zuishare.spi.model.Article;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author niange
 * @ClassName: ArticleServiceImpl
 * @desp:
 * @date: 2017/12/1 下午11:06
 * @since JDK 1.7
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    private static final Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);

    @Autowired
    private ArticleDao articleDao;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private Gson gson;

    @Override
    public List<Article> getListByPage(int pageNo, int pageSize) {
        List<Article> articles = null;
        //先从缓存中获取
        try {
            String articleJsonStr = stringRedisTemplate.opsForValue().get(Constants.REDIS_ARTICLES_KEY);
            //json 反序列化
            articles = gson.fromJson(articleJsonStr,
                    new TypeToken<ArrayList<Article>>() {
                    }.getType());
            int start = (pageNo-1) * pageSize;
            if (CollectionUtils.isEmpty(articles)) {
                articles = articleDao.getArticleByPage(start, pageSize);
                if (!CollectionUtils.isEmpty(articles)) {
                    stringRedisTemplate.opsForValue()
                            .set(Constants.REDIS_ARTICLES_KEY,
                                    gson.toJson(articles), 30, TimeUnit.DAYS);
                }
            } else {
                //假分页，后续优化//TODO
                if (articles.size() > pageSize) {
                    articles = articles.subList(start, start + pageSize);
                }
            }
        }catch(Exception e){
            logger.error("redis happend error.", e);
        }
        return articles;
    }

    @Override
    public List<Article> getHotArticles(int limit) {
        List<Article> articles = null;
        try {
            String articleJsonStr = stringRedisTemplate.opsForValue().get(Constants.REDIS_HOT_ARTICLES_KEY);
            articles = gson.fromJson(articleJsonStr, new TypeToken<ArrayList<Article>>() {
            }.getType());
            if (CollectionUtils.isEmpty(articles)) {
                articles = articleDao.getHotArticles(limit);
                if (!CollectionUtils.isEmpty(articles)) {
                    stringRedisTemplate.opsForValue()
                            .set(Constants.REDIS_HOT_ARTICLES_KEY,
                                    gson.toJson(articles), 10, TimeUnit.MINUTES);
                }
            }
        }catch (Exception e){
            logger.error("redis happend error.", e);
        }
        return articles;
    }
}
