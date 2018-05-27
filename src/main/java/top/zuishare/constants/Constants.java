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
    public static final String REDIS_ARTICLES_KEY = "list";
    /**
     * 所有正常发布的文章总数，需要分页
     */
    public static final String REDIS_ARTICLES_COUNT_KEY = "count";
    /**
     *所有正常的文章分类
     */
    public static final String REDIS_ARTICLE_CATEGORY_KEY = "list";
    /**
     *热门榜文章
     */
    public static final String REDIS_HOT_ARTICLES_KEY = "viewNum";

    /**
     * 过期时间30天
     */
    public static final int TIMEOUTDAYS = 30;
    /**
     * 过期时间10分钟
     */
    public static final int TIMEOUTMINUTES = 10;
    /**
     * 过期时间10s
     */
    public static final int TIMEOUTSECONDS = 10;
    /**
     * 发布的文章redis的key前缀
     * eg:Article:1:id、Article:2:id
     * 表示id为1的文章，id为2的文章
     */
    public static final String REDIS_ARTICLE_PRE_KEY = "id";
    /**
     * 分类对应的文章列表，缓存redis的key前缀
     * eg:Article:1:categoryId
     * 表示id为1的分类下的文章列表，id为2的分类的文章列表
     */
    public static final String REDIS_CATEGORY_ARTICLES_PRE_KEY = "categoryId";
    /**
     * 分类对应的文章列表数量，缓存redis的key前缀
     * eg:eg:Article:count:1:categoryId
     * 表示id为1的分类下的文章数量
     */
    public static final String REDIS_CATEGORY_ARTICLES_COUNT_KEY = "count";


    /**
     * redis 组成key之间单词的分隔符
     * 对象类型:对象ID:对象属性”来命名一个键。对于多个单词则推荐使用"."分割
     * eg:Article:list
     * eg:Article:1:id
     * eg:Article:1:categoryId
     * eg:Article:list:viewNum
     * eg:ArticleCategory:list
     */
    public static final String KEYDELIMITER = ":";
    /**
     *文章类型
     */
    public static final String ARTICLECLASS = "Article";

    /**
     * 文章分类类型
     */
    public static final String ARTICLECATEGORYCLASS = "ArticleCategory";



    ///////////////////////////////////////////////////////////////////
    /**
     *所有正常发布的ad
     */
    public static final String REDIS_AD_KEY = "list";

    public static final String REDIS_PRODUCT_KEY = "list";

    public static final String ADCLASS = "ad";

    public static final String PRODUCTCLASS = "product";

    /**
     * 所有正常发布的column
     */
    public static final String REDIS_COLUMN_KEY = "list";

    public static final String COLUMNCLASS = "column";
    /**
     * 产品栏目的限定名称
     */
    public static final String PRODUCT_COLUMN_NAME = "products";
    /**
     * 新闻栏目的限定名称
     */
    public static final String NEWS_COLUMN_NAME = "news";

    /**
     * 每页产品展示数量
     */
    public static final Integer PRODUCT_PAGE_SIZE = 6;
    /**
     * 每页新闻展示数量
     */
    public static final Integer NEWS_PAGE_SIZE = 10;
}
