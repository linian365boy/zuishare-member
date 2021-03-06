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
import top.zuishare.dto.PageDto;
import top.zuishare.service.ArticleService;
import top.zuishare.spi.model.Article;
import top.zuishare.util.RedisUtil;

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
    public PageDto<Article> getListByPage(int pageNo) {
        PageDto<Article> pageDto = new PageDto<>();
        int pageSize = pageDto.getPageSize();
        logger.info("get articles by page, pageNo => {}, pageSize => {}", pageNo, pageSize);
        List<Article> articles = null;
        long totalNum = 0;
        //先从缓存中获取
        try {
            String articleJsonStr = stringRedisTemplate.opsForValue().get(RedisUtil.getAllPublishArticlesKey());
            //json 反序列化
            articles = gson.fromJson(articleJsonStr,
                    new TypeToken<ArrayList<Article>>() {
                    }.getType());
            int start = (pageNo-1) * pageSize;
            if (CollectionUtils.isEmpty(articles)) {
                articles = articleDao.getArticleByPage(start, pageSize);
                totalNum = articleDao.getArticleSize();
                if (!CollectionUtils.isEmpty(articles)) {
                    //设置文章总数到redis
                    stringRedisTemplate.opsForValue()
                            .set(RedisUtil.getArticleCountKey(),
                                    String.valueOf(totalNum), Constants.TIMEOUTDAYS , TimeUnit.DAYS);
                }
            } else {
                //假分页，后续优化//TODO
                totalNum = Long.parseLong(stringRedisTemplate.opsForValue().get(RedisUtil.getArticleCountKey()));
                if (articles.size() > pageSize) {
                    int end = (start + pageSize) > articles.size() ? articles.size() : (start + pageSize);
                    articles = articles.subList(start, end);
                }
            }
        }catch(Exception e){
            logger.error("redis happend error.", e);
        }
        pageDto.setData(articles);
        pageDto.setTotalNum(totalNum);
        pageDto.setCurrentPageNo(pageNo);
        logger.info("return pageDto articles size => {}, totalNum => {}",
                CollectionUtils.isEmpty(articles)?0:articles.size(), totalNum);
        return pageDto;
    }

    @Override
    public List<Article> getHotArticles(int limit) {
        List<Article> articles = null;
        try {
            String articleJsonStr = stringRedisTemplate.opsForValue().get(RedisUtil.getHotArticlesKey());
            articles = gson.fromJson(articleJsonStr, new TypeToken<ArrayList<Article>>() {
            }.getType());
            if (CollectionUtils.isEmpty(articles)) {
                articles = articleDao.getHotArticles(limit);
                if (!CollectionUtils.isEmpty(articles)) {
                    stringRedisTemplate.opsForValue()
                            .set(RedisUtil.getHotArticlesKey(),
                                    gson.toJson(articles), Constants.TIMEOUTMINUTES, TimeUnit.MINUTES);
                }
            }
        }catch (Exception e){
            logger.error("redis happend error.", e);
        }
        logger.info("get {} hot article => {}", limit, articles);
        return articles;
    }

    @Override
    public Article loadOne(long id) {
        Article article = null;
        try{
            String articleStr = stringRedisTemplate.opsForValue().get(RedisUtil.getArticleKey(id));
            article = gson.fromJson(articleStr, Article.class);
        }catch (Exception e){
            logger.error("redis happend error.", e);
        }
        logger.info("get article => {} from redis", article==null?"null":article.getTitle());
        return article;
    }

    @Override
    public void increaseViewNum(Article article) {
        //点击量+1，放入redis队列
        //key:Article:viewNum value:id:newArticleViewNum
        long articleId = article.getId();
        int newViewNum = article.getViewNum()+1;
        stringRedisTemplate.opsForList().leftPush(RedisUtil.getArticleViewNumKey(),
                articleId + Constants.KEYDELIMITER + newViewNum);
        logger.info("increaseViewNum lpush data to redis", articleId, newViewNum);
    }

    @Override
    public PageDto<Article> getPageListByCateId(int categoryId, int pageNo){
        PageDto<Article> pageDto = new PageDto<>();
        int pageSize = pageDto.getPageSize();
        logger.info("get category articles by page, categoryId => {}, pageNo => {}, pageSize => {}",
                categoryId, pageNo, pageSize);
        List<Article> articles = null;
        long totalNum = 0;
        try{
            String articleJsonStr = stringRedisTemplate.opsForValue()
                    .get(RedisUtil.getCategoryArticlesKey(categoryId));
            //json 反序列化
            articles = gson.fromJson(articleJsonStr,
                    new TypeToken<ArrayList<Article>>() {
                    }.getType());
            //从db获取
            int start = (pageNo-1) * pageSize;
            if (CollectionUtils.isEmpty(articles)) {
                articles = articleDao.getPageListByCateId(categoryId, start, pageSize);
                totalNum = articleDao.getArticleSizeByCateId(categoryId);
                if (!CollectionUtils.isEmpty(articles)) {
                    //设置文章总数到redis
                    stringRedisTemplate.opsForValue()
                            .set(RedisUtil.getArticleCountKeyByCategoryId(categoryId),
                                    String.valueOf(totalNum), Constants.TIMEOUTDAYS , TimeUnit.DAYS);
                }
            }else{
                //假分页，后续优化//TODO
                totalNum = Long.parseLong(stringRedisTemplate.opsForValue().get(RedisUtil.getArticleCountKeyByCategoryId(categoryId)));
                if (articles.size() > pageSize) {
                    int end = (start + pageSize) > articles.size() ? articles.size() : (start + pageSize);
                    articles = articles.subList(start, end);
                }
            }
        }catch (Exception e){
            logger.error("get category articles from redis fail.", e);
        }
        pageDto.setData(articles);
        pageDto.setTotalNum(totalNum);
        pageDto.setCurrentPageNo(pageNo);
        logger.info("return category pageDto articles size => {}, totalNum => {}",
                CollectionUtils.isEmpty(articles)?0:articles.size(), totalNum);
        return pageDto;
    }

}
