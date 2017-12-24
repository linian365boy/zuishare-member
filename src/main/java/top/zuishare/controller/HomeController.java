package top.zuishare.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import top.zuishare.dto.PageDto;
import top.zuishare.service.ArticleCategoryService;
import top.zuishare.service.ArticleService;
import top.zuishare.spi.model.Article;
import top.zuishare.spi.model.ArticleCategory;

import java.util.List;

/**
 * @author niange
 * @ClassName: HomeController
 * @desp:
 * @date: 2017/11/22 下午10:03
 * @since JDK 1.7
 */
@Controller
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private ArticleService articleService;
    @Autowired
    private ArticleCategoryService articleCategoryService;
    private int hotLimit = 8;

    @RequestMapping(value = {"", "/", "/index", "/home"}, method = RequestMethod.GET)
    public String home( ModelMap map) {
        long start = System.currentTimeMillis();
        PageDto<Article> indexArticles = articleService.getListByPage(1);
        List<ArticleCategory> categories = articleCategoryService.getList();
        List<Article> hotArticles = articleService.getHotArticles(hotLimit);
        map.put("articlesPage", indexArticles);
        map.put("categories", categories);
        map.put("hotArticles", hotArticles);
        logger.info("enter homt page cost {} ms", System.currentTimeMillis() - start);
        return "index";
    }

}
