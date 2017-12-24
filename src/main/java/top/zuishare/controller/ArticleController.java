package top.zuishare.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import top.zuishare.dto.PageDto;
import top.zuishare.service.ArticleCategoryService;
import top.zuishare.service.ArticleService;
import top.zuishare.spi.model.Article;
import top.zuishare.spi.model.ArticleCategory;

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
    private int hotLimit = 8;

    @RequestMapping(value = "/articles/{pageNo}")
    public String listByPage(@PathVariable("pageNo") int pageNo, ModelMap map){
        logger.info("enter article list page, pageNo => {}", pageNo);
        long start = System.currentTimeMillis();
        PageDto<Article> indexArticles = articleService.getListByPage(pageNo);
        List<ArticleCategory> categories = articleCategoryService.getList();
        List<Article> hotArticles = articleService.getHotArticles(hotLimit);
        map.put("articlesPage", indexArticles);
        map.put("categories", categories);
        map.put("hotArticles", hotArticles);
        logger.info("enter listByPage page cost {} ms", System.currentTimeMillis() - start);
        return "index";
    }

    @RequestMapping(value = "/article/{id}", method = RequestMethod.GET)
    public String article(@PathVariable("id") long id, ModelMap map){
        long start = System.currentTimeMillis();
        Article article = articleService.loadOne(id);
        List<ArticleCategory> categories = articleCategoryService.getList();
        List<Article> hotArticles = articleService.getHotArticles(hotLimit);
        map.put("categories", categories);
        map.put("hotArticles", hotArticles);
        map.put("model", article);
        logger.info("enter article => {} page cost {} ms", id, System.currentTimeMillis() - start);
        return "detail";
    }

    @RequestMapping(value = "/article/category/{categoryId}", method = RequestMethod.GET)
    public String categoryArticle(@PathVariable("categoryId") long categoryId){
        return "categoryArticles";
    }

}
