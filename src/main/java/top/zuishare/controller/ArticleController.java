package top.zuishare.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import top.zuishare.constants.BussinessConfig;
import top.zuishare.dto.PageDto;
import top.zuishare.service.ArticleCategoryService;
import top.zuishare.service.ArticleService;
import top.zuishare.service.RedisService;
import top.zuishare.spi.model.Article;
import top.zuishare.spi.model.ArticleCategory;
import top.zuishare.util.IpUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author niange
 * @ClassName: ArticleController
 * @desp:
 * @date: 2017/12/23 下午11:59
 * @since JDK 1.7
 */
@Controller
public class ArticleController {

    private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);

    @Autowired
    private ArticleService articleService;
    @Autowired
    private ArticleCategoryService articleCategoryService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private BussinessConfig bussinessConfig;

    @RequestMapping(value = "/articles/{pageNo}")
    public String listByPage(@PathVariable("pageNo") int pageNo, ModelMap map){
        logger.info("enter article list page, pageNo => {}", pageNo);
        long start = System.currentTimeMillis();
        PageDto<Article> indexArticles = articleService.getListByPage(pageNo);
        List<ArticleCategory> categories = articleCategoryService.getList();
        List<Article> hotArticles = articleService.getHotArticles(bussinessConfig.getHotLimit());
        map.put("articlesPage", indexArticles);
        map.put("categories", categories);
        map.put("hotArticles", hotArticles);
        logger.info("enter listByPage page cost {} ms", System.currentTimeMillis() - start);
        return "index";
    }

    @RequestMapping(value = "/article/{id}", method = RequestMethod.GET)
    public String article(@PathVariable("id") long id, ModelMap map, HttpServletRequest request){
        logger.info("get article request param => {}", id);
        long start = System.currentTimeMillis();
        Article article = articleService.loadOne(id);
        if(article == null){
            logger.warn("redis has no cache the article, request articleId => {}", id);
            //跳转到404页面
            return "redirect:/404";
        }
        //点击量新增
        if(redisService.getDisLock(article.getId() +":"+ IpUtil.getIpAddr(request))) {
            articleService.increaseViewNum(article);
        }
        List<ArticleCategory> categories = articleCategoryService.getList();
        List<Article> hotArticles = articleService.getHotArticles(bussinessConfig.getHotLimit());
        map.put("categories", categories);
        map.put("hotArticles", hotArticles);
        map.put("model", article);
        logger.info("enter article => {} page cost {} ms", id, System.currentTimeMillis() - start);
        return "detail";
    }

    @RequestMapping(value = "/article/category/{categoryId}", method = RequestMethod.GET)
    public String categoryArticle(@PathVariable("categoryId") int categoryId,
                                  @RequestParam(value="pageNo", required=false) Integer pageNo ,
                                  ModelMap map){
        logger.info("enter category article list page, categoryId => {}, pageNo => {}", categoryId, pageNo);
        long start = System.currentTimeMillis();
        if(pageNo == null || pageNo < 1){
            pageNo = 1;
        }
        PageDto<Article> cateArticles = articleService.getPageListByCateId(categoryId, pageNo);
        List<ArticleCategory> categories = articleCategoryService.getList();
        List<Article> hotArticles = articleService.getHotArticles(bussinessConfig.getHotLimit());
        map.put("articlesPage", cateArticles);
        map.put("categories", categories);
        map.put("hotArticles", hotArticles);
        if(!CollectionUtils.isEmpty(categories)){
            for(ArticleCategory category : categories){
                if(category.getId() == categoryId){
                    map.put("tempCate", category);
                    break;
                }
            }
        }
        logger.info("enter listByPage page cost {} ms", System.currentTimeMillis() - start);
        return "categoryArticles";
    }

}
