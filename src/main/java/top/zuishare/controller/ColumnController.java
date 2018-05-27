package top.zuishare.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import top.zuishare.constants.BussinessConfig;
import top.zuishare.constants.Constants;
import top.zuishare.service.*;
import top.zuishare.spi.model.*;

import java.util.List;

/**
 * @author niange
 * @ClassName: ColumnController
 * @desp:
 * @date: 2018/5/16 下午10:56
 * @since JDK 1.7
 */

@Controller
public class ColumnController {

    private static final Logger logger = LoggerFactory.getLogger(ColumnController.class);
    @Autowired
    private ColumnService columnService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private WebConfigService webConfigService;
    @Autowired
    private BussinessConfig bussinessConfig;
    @Autowired
    private AdService adService;
    @Autowired
    private ProductService productService;
    @Autowired
    private NewsService newsService;

    @RequestMapping("/column/{code}")
    public String toColumn(@PathVariable("code") String code, ModelMap map){
        long start = System.currentTimeMillis();
        // 查菜单
        List<Column> columns = columnService.queryIndexColumn();
        // 查公司信息
        Company company = companyService.loadCompany(bussinessConfig.getCompanyConfigPath());
        // 查网站信息
        WebConfig config = webConfigService.loadWebConfig(bussinessConfig.getWebConfigPath());

        Column column = columnService.getColumnByCode(code);

        map.put("currentColumn", column);
        map.put("columns", columns);
        map.put("company", company);
        map.put("config", config);

        if(code.equalsIgnoreCase(Constants.PRODUCT_COLUMN_NAME)){
            logger.info("enter column page cost {} ms", System.currentTimeMillis() - start);
            return products(map);
        }else if(code.equalsIgnoreCase(Constants.NEWS_COLUMN_NAME)){
            logger.info("enter column page cost {} ms", System.currentTimeMillis() - start);
            return news(map);
        }else {
            // 查滚动图片
            List<Advertisement> ads = adService.queryIndexAd(bussinessConfig.getIndexAds());
            map.put("ads", ads);
            logger.info("enter column page cost {} ms", System.currentTimeMillis() - start);
            return "column";
        }
    }


    public String products(ModelMap map){
        List<Product> products = productService.listProducts(Constants.PRODUCT_PAGE_SIZE);
        map.put("products", products);
        return Constants.PRODUCT_COLUMN_NAME;
    }


    public String news(ModelMap map){
        List<News> news = newsService.listNews(Constants.NEWS_PAGE_SIZE);
        map.put("news", news);
        return Constants.NEWS_COLUMN_NAME;
    }

}
