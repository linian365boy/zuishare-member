package top.zuishare.util;

import top.zuishare.constants.Constants;

/**
 * @author niange
 * @ClassName: RedisUtil
 * @desp:
 * @date: 2017/12/30 上午10:51
 * @since JDK 1.7
 */
public class RedisUtil {

    /**
     * 获取所有正常发布的文章Key
     * eg:Article:list
     * @return
     */
    public static String getAllPublishArticlesKey(){
        return new StringBuilder()
                .append(Constants.ARTICLECLASS)
                .append(Constants.KEYDELIMITER)
                .append(Constants.REDIS_ARTICLES_KEY)
                .toString();
    }

    /**
     * 获取所有正常的文章分类Key
     * eg:ArticleCategory:list
     * @return
     */
    public static String getAllPublishCategoryKey(){
        return new StringBuilder()
                .append(Constants.ARTICLECATEGORYCLASS)
                .append(Constants.KEYDELIMITER)
                .append(Constants.REDIS_ARTICLE_CATEGORY_KEY)
                .toString();
    }

    /**
     * 热门榜文章Key
     * eg:Article:list:viewNum
     * @return
     */
    public static String getHotArticlesKey(){
        return new StringBuilder()
                .append(Constants.ARTICLECLASS)
                .append(Constants.KEYDELIMITER)
                .append(Constants.REDIS_ARTICLES_KEY)
                .append(Constants.KEYDELIMITER)
                .append(Constants.REDIS_HOT_ARTICLES_KEY)
                .toString();
    }

    /**
     * 单个文章key
     * eg:Article:1:id
     * @return
     */
    public static String getArticleKey(long articleId){
        return new StringBuilder()
                .append(Constants.ARTICLECLASS)
                .append(Constants.KEYDELIMITER)
                .append(articleId)
                .append(Constants.KEYDELIMITER)
                .append(Constants.REDIS_ARTICLE_PRE_KEY)
                .toString();
    }

    /**
     * 分类文章列表Key
     * eg:Article:1:categoryId
     * @return
     */
    public static String getCategoryArticlesKey(int articleCategoryId){
        return new StringBuilder()
                .append(Constants.ARTICLECLASS)
                .append(Constants.KEYDELIMITER)
                .append(articleCategoryId)
                .append(Constants.KEYDELIMITER)
                .append(Constants.REDIS_CATEGORY_ARTICLES_PRE_KEY)
                .toString();
    }

    /**
     * 所有正常发布的文章总数，分页计算使用
     * eg:Article:count
     * @return
     */
    public static String getArticleCountKey(){
        return new StringBuilder()
                .append(Constants.ARTICLECLASS)
                .append(Constants.KEYDELIMITER)
                .append(Constants.REDIS_ARTICLES_COUNT_KEY)
                .toString();

    }

    /**
     * 所有分类正常发布的文章总数，分页计算使用
     * eg:Article:count:1:categoryId
     * 分类id为1的文章列表总数
     * @return
     */
    public static String getArticleCountKeyByCategoryId(int categoryId){
        return new StringBuilder()
                .append(Constants.ARTICLECLASS)
                .append(Constants.KEYDELIMITER)
                .append(Constants.REDIS_CATEGORY_ARTICLES_COUNT_KEY)
                .append(Constants.KEYDELIMITER)
                .append(categoryId)
                .append(Constants.KEYDELIMITER)
                .append(Constants.REDIS_CATEGORY_ARTICLES_PRE_KEY)
                .toString();

    }

    /**
     * 文章的点击量队列key
     * eg:Article:viewNum
     * key:Article:viewNum
     * value:#{id}:#{newArticleViewNum}
     * @return
     */
    public static String getArticleViewNumKey(){
        return new StringBuilder()
                .append(Constants.ARTICLECLASS)
                .append(Constants.KEYDELIMITER)
                .append(Constants.REDIS_HOT_ARTICLES_KEY)
                .toString();
    }


    public static String getIndexColumnKey(){
        return new StringBuilder()
                .append(Constants.COLUMNCLASS)
                .append(Constants.KEYDELIMITER)
                .append(Constants.REDIS_COLUMN_KEY)
                .toString();
    }

    public static String getIndexAdsKey(){
        return new StringBuilder()
                .append(Constants.ADCLASS)
                .append(Constants.KEYDELIMITER)
                .append(Constants.REDIS_AD_KEY)
                .toString();
    }

}
