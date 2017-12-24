package top.zuishare.constants;

/**
 * @author niange
 * @ClassName: Constants
 * @desp:
 * @date: 2017/12/2 下午9:19
 * @since JDK 1.7
 */
public class Constants {
    /**
     *所有正常发布的文章
     */
    public static final String REDIS_ARTICLES_KEY = "articles";
    /**
     * 所有正常发布的文章总数，需要分页
     */
    public static final String REDIS_ARTICLES_COUNT_KEY = "articlesCount";
    /**
     *所有正常的文章分类
     */
    public static final String REDIS_ARTICLE_CATEGORY_KEY = "articleCategorys";
    /**
     *热门榜文章
     */
    public static final String REDIS_HOT_ARTICLES_KEY = "hotArticles";

    /**
     * 过期时间30天
     */
    public static final int TIMEOUTDAYS = 30;
    /**
     * 过期时间10分钟
     */
    public static final int TIMEOUTMINUTES = 10;
    /**
     * 发布的文章redis的key前缀
     */
    public static final String REDIS_ARTICLE_PRE_KEY = "article_";
}
