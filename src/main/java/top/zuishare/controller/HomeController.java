package top.zuishare.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import top.zuishare.constants.BussinessConfig;
import top.zuishare.service.*;
import top.zuishare.spi.model.*;

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
    private AdService adService;
    @Autowired
    private ColumnService columnService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private WebConfigService webConfigService;
    @Autowired
    private BussinessConfig bussinessConfig;
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = {"", "/", "/index", "/home"}, method = RequestMethod.GET)
    public String home( ModelMap map) {
        long start = System.currentTimeMillis();
        // 查滚动图片
        List<Advertisement> ads = adService.queryIndexAd(bussinessConfig.getIndexAds());
        // 查菜单
        List<Column> columns = columnService.queryIndexColumn();
        // 查公司信息
        Company company = companyService.loadCompany(bussinessConfig.getCompanyConfigPath());
        // 查网站信息
        WebConfig config = webConfigService.loadWebConfig(bussinessConfig.getWebConfigPath());
        // 首页展示的产品
        List<Product> indexProducts = productService.queryIndexProduct(bussinessConfig.getIndexProductsSize());

        List<Category> categories = categoryService.queryCategory();

        map.put("currentColumn", new Column());
        map.put("ads", ads);
        map.put("columns", columns);
        map.put("company", company);
        map.put("config", config);
        map.put("indexProducts", indexProducts);
        map.put("categories", categories);

        logger.info("enter home page cost {} ms", System.currentTimeMillis() - start);
        return "index";
    }

}
