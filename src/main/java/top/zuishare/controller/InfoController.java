package top.zuishare.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import top.zuishare.constants.BussinessConfig;
import top.zuishare.dto.PageDto;
import top.zuishare.service.ArticleCategoryService;
import top.zuishare.service.ArticleService;
import top.zuishare.spi.model.Article;
import top.zuishare.spi.model.ArticleCategory;

import java.util.List;

/**
 * @author niange
 * @ClassName: InfoController
 * @desp:
 * @date: 2018/1/6 下午3:35
 * @since JDK 1.7
 */
@Controller
public class InfoController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ArticleCategoryService articleCategoryService;
    @Autowired
    private BussinessConfig bussinessConfig;
    private static final Logger logger = LoggerFactory.getLogger(InfoController.class);

    @GetMapping("/aboutUs")
    public String aboutUs(ModelMap map){
        logger.info("get aboutUs page.");
        List<ArticleCategory> categories = articleCategoryService.getList();
        List<Article> hotArticles = articleService.getHotArticles(bussinessConfig.getHotLimit());
        map.put("categories", categories);
        map.put("hotArticles", hotArticles);
        return "aboutUs";
    }

    @GetMapping("/deliver")
    public String deliver(ModelMap map){
        logger.info("get deliver page.");
        List<ArticleCategory> categories = articleCategoryService.getList();
        List<Article> hotArticles = articleService.getHotArticles(bussinessConfig.getHotLimit());
        map.put("categories", categories);
        map.put("hotArticles", hotArticles);
        return "deliver";
    }

}
