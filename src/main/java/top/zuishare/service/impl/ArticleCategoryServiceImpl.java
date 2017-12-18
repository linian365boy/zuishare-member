package top.zuishare.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import top.zuishare.constants.Constants;
import top.zuishare.dao.ArticleCategoryDao;
import top.zuishare.service.ArticleCategoryService;
import top.zuishare.spi.model.ArticleCategory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author niange
 * @ClassName: ArticleCategoryServiceImpl
 * @desp:
 * @date: 2017/12/2 上午12:01
 * @since JDK 1.7
 */
@Service
public class ArticleCategoryServiceImpl implements ArticleCategoryService {

    @Autowired
    private ArticleCategoryDao articleCategoryDao;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private Gson gson;

    public List<ArticleCategory> getList(){
        //先从缓存中获取
        String categoryStr = stringRedisTemplate.opsForValue().get(Constants.REDIS_ARTICLE_CATEGORY_KEY);
        //json 反序列化
        List<ArticleCategory> categories = gson.fromJson(categoryStr,
               new TypeToken<ArrayList<ArticleCategory>>(){}.getType());
        if(CollectionUtils.isEmpty(categories)){
            categories =articleCategoryDao.getList();
            // 设置过期时间30天
            if(!CollectionUtils.isEmpty(categories)) {
                stringRedisTemplate.opsForValue()
                        .set(Constants.REDIS_ARTICLE_CATEGORY_KEY,
                                gson.toJson(categories), 30, TimeUnit.DAYS);
            }
        }
        return categories;
    }

}
