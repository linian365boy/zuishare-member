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

    /**
     * 获取首页栏目列表key
     * @return
     */
    public static String getIndexColumnKey(){
        return new StringBuilder()
                .append(Constants.COLUMNCLASS)
                .append(Constants.KEYDELIMITER)
                .append(Constants.REDIS_LIST_KEY)
                .toString();
    }
    
    /**
     * 获取产品列表key
     * products:list
     * @return
     */
    public static String getProductsKey(){
        return new StringBuilder()
                .append(Constants.PRODUCTCLASS)
                .append(Constants.KEYDELIMITER)
                .append(Constants.REDIS_LIST_KEY)
                .toString();
    }
    
    /**
     * 获取新闻列表key
     * news:list
     * @return
     */
    public static String getNewsKey(){
        return new StringBuilder()
                .append(Constants.NEWSCLASS)
                .append(Constants.KEYDELIMITER)
                .append(Constants.REDIS_LIST_KEY)
                .toString();
    }
    
    /**
     * 获取分类产品列表key
     * category:5:id:products:list
     * @return
     */
    public static String getCateProductKey(int cateId){
        return new StringBuilder()
                .append(Constants.CATEGORYCLASS)
                .append(Constants.KEYDELIMITER)
                .append(cateId)
                .append(Constants.KEYDELIMITER)
                .append(Constants.ID)
                .append(Constants.PRODUCTCLASS)
                .append(Constants.KEYDELIMITER)
                .append(Constants.REDIS_LIST_KEY)
                .toString();
    }
    
    /**
     * 获取产品详情key
     * product:5:id
     * @return
     */
    public static String getProductDetailKey(int productId) {
    	return new StringBuilder()
    			.append(Constants.PRODUCTCLASS)
    			.append(productId)
    			.append(Constants.ID)
    			.toString();
    }
    
    /**
     * 获取新闻详情key
     * product:5:id
     * @return
     */
    public static String getNewsDetailKey(int newsId) {
    	return new StringBuilder()
    			.append(Constants.NEWSCLASS)
    			.append(newsId)
    			.append(Constants.ID)
    			.toString();
    }
    
    /**
     * 获取公司信息key
     * company
     * @return
     */
    public static String getCompanyKey() {
    	return new StringBuilder()
    			.append(Constants.COMPANYCLASS)
    			.toString();
    }
    
    /**
     * 获取webconfig信息key
     * webconfig
     * @return
     */
    public static String getWebConfigKey() {
    	return new StringBuilder()
    			.append(Constants.WEBCONFIG)
    			.toString();
    }
    
    /**
     * 获取column列表信息key
     * column:list
     * @return
     */
    public static String getColumnsKey() {
    	return new StringBuilder()
    			.append(Constants.COLUMNCLASS)
    			.append(Constants.KEYDELIMITER)
    			.append(Constants.REDIS_LIST_KEY)
    			.toString();
    }
    
    /**
     * 获取产品分类列表信息key
     * category:list
     * @return
     */
    public static String getProductCatesKey() {
    	return new StringBuilder()
    			.append(Constants.CATEGORYCLASS)
    			.append(Constants.KEYDELIMITER)
    			.append(Constants.REDIS_LIST_KEY)
    			.toString();
    }
    
    /**
     * 获取滚动图片列表信息key
     * ad:list
     * @return
     */
    public static String getAdsKey() {
    	return new StringBuilder()
    			.append(Constants.ADCLASS)
    			.append(Constants.KEYDELIMITER)
    			.append(Constants.REDIS_LIST_KEY)
    			.toString();
    }
    
    /**
     * 获取栏目详情key，根据code
     * column:aboutus:code
     * @return
     */
    public static String getColumnKeyByCode(String code) {
    	return new StringBuilder()
    			.append(Constants.COLUMNCLASS)
    			.append(Constants.KEYDELIMITER)
    			.append(code)
    			.append(Constants.KEYDELIMITER)
    			.append(Constants.CODE_STR)
    			.toString();
    }
    
    
    /**
     * 获取栏目详情key，根据code
     * column:5:id
     * @return
     */
    public static String getColumnKeyById(int columnId) {
    	return new StringBuilder()
    			.append(Constants.COLUMNCLASS)
    			.append(Constants.KEYDELIMITER)
    			.append(columnId)
    			.append(Constants.KEYDELIMITER)
    			.append(Constants.ID)
    			.toString();
    }
    
}
