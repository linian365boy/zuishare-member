package top.zuishare.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import top.zuishare.constants.Constants;
import top.zuishare.dao.ArticleDao;
import top.zuishare.service.ArticleService;
import top.zuishare.spi.model.Article;

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

    @Autowired
    private ArticleDao articleDao;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private Gson gson;

    @Override
    public List<Article> getListByPage(int pageNo, int pageSize) {
        int start = (pageNo-1) * pageSize;
        //先从缓存中获取
        String articleJsonStr = stringRedisTemplate.opsForValue().get(Constants.REDIS_ARTICLES_KEY);
        //json 反序列化
        List<Article> articles = gson.fromJson(articleJsonStr,
               new TypeToken<ArrayList<Article>>(){}.getType());
        if(CollectionUtils.isEmpty(articles)) {
            articles = articleDao.getArticleByPage(start, pageSize);
            if(!CollectionUtils.isEmpty(articles)) {
                stringRedisTemplate.opsForValue()
                        .set(Constants.REDIS_ARTICLES_KEY,
                                gson.toJson(articles), 30, TimeUnit.DAYS);
            }
        }else{
            //假分页，后续优化//TODO
            if(articles.size() > pageSize){
                articles = articles.subList(start, start + pageSize);
            }
        }
        return articles;
    }

    @Override
    public List<Article> getHotArticles(int limit) {
        String articleJsonStr = stringRedisTemplate.opsForValue().get(Constants.REDIS_HOT_ARTICLES_KEY);
        List<Article> articles = gson.fromJson(articleJsonStr, new TypeToken<ArrayList<Article>>(){}.getType());
        if(CollectionUtils.isEmpty(articles)){
            articles = articleDao.getHotArticles(limit);
            if(!CollectionUtils.isEmpty(articles)) {
                stringRedisTemplate.opsForValue()
                        .set(Constants.REDIS_HOT_ARTICLES_KEY,
                                gson.toJson(articles), 10, TimeUnit.MINUTES);
            }
        }
        return articles;
    }
}
