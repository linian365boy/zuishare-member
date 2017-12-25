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
     * eg:article_1、article_2
     * 表示id为1的文章，id为2的文章
     */
    public static final String REDIS_ARTICLE_PRE_KEY = "article_";
    /**
     * 分类对应的文章列表，缓存redis的key前缀
     * eg:category_articles_1、category_articles_2
     * 表示id为1的分类下的文章列表，id为2的分类的文章列表
     */
    public static final String REDIS_CATEGORY_ARTICLES_PRE_KEY = "category_articles_";
    /**
     * 分类对应的文章列表数量，缓存redis的key前缀
     * eg:categoryArticleCount_1
     * 表示id为1的分类下的文章数量
     */
    public static final String REDIS_CATEGORY_ARTICLES_COUNT_KEY = "categoryArticleCount_";
}
