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
    @Autowired
    private CategoryService categoryService;

    /*@RequestMapping("/column/{code}")
    public String toColumn(@PathVariable("code") String code, ModelMap map){
        long start = System.currentTimeMillis();
        common(code, map);
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
    }*/

    @RequestMapping("/column/{code}")
    public String toColumn(@PathVariable("code") String code, ModelMap map){
        long start = System.currentTimeMillis();
        common(code, map);
        switch (code){
            case Constants.PRODUCT_COLUMN_NAME:
                logger.info("enter column page cost {} ms", System.currentTimeMillis() - start);
                return products(map, 1, "/column/"+Constants.PRODUCT_COLUMN_NAME+"/");
            case Constants.NEWS_COLUMN_NAME:
                logger.info("enter column page cost {} ms", System.currentTimeMillis() - start);
                return news(map, 1, "/column/"+Constants.NEWS_COLUMN_NAME+"/");
            case "aboutUs":
                return code;
            case "contactUs":
                return code;
            default:
                // 查滚动图片
                List<Advertisement> ads = adService.queryIndexAd(bussinessConfig.getIndexAds());
                map.put("ads", ads);
                logger.info("enter column page cost {} ms", System.currentTimeMillis() - start);
                return "column";
        }
    }


    public void common(String code, ModelMap map){
        // 查菜单
        List<Column> columns = columnService.queryIndexColumn();
        // 查公司信息
        Company company = companyService.loadCompany(bussinessConfig.getCompanyConfigPath());
        // 查网站信息
        WebConfig config = webConfigService.loadWebConfig(bussinessConfig.getWebConfigPath());

        Column column = columnService.getColumnByCode(code);

        List<Category> categories = categoryService.queryCategory();

        logger.info("query category size => {}", categories == null ? 0 : categories.size());

        map.put("currentColumn", column);
        map.put("columns", columns);
        map.put("company", company);
        map.put("config", config);
        map.put("categories", categories);
    }


    public String products(ModelMap map, int pageNo, String urlPrefix){
        long count = productService.countProducts();
        long totalPage = count%Constants.PRODUCT_PAGE_SIZE == 0 ?
                count/Constants.PRODUCT_PAGE_SIZE : (count/Constants.PRODUCT_PAGE_SIZE)+1;
        map.put("totalPage", totalPage);
        List<Product> products = null;
        if (pageNo > totalPage) {
            pageNo = 1;
        }
        products = productService.listProducts(Constants.PRODUCT_PAGE_SIZE, pageNo);
        map.put("list", products);
        map.put("currentPageNo", pageNo);
        map.put("urlPrefix", urlPrefix);
        logger.info("products page totalPage=>{}, totalNum=>{}", totalPage, count);
        return Constants.PRODUCT_COLUMN_NAME;
    }


    public String news(ModelMap map, int pageNo, String urlPrefix){
        long count = newsService.countNews();
        long totalPage = count%Constants.NEWS_PAGE_SIZE == 0 ?
                count/Constants.NEWS_PAGE_SIZE : (count/Constants.NEWS_PAGE_SIZE)+1;
        if (pageNo > totalPage) {
            pageNo = 1;
        }
        List<News> news = newsService.listNews(Constants.NEWS_PAGE_SIZE, pageNo);
        map.put("list", news);
        map.put("totalPage", totalPage);
        map.put("currentPageNo", pageNo);
        map.put("urlPrefix", urlPrefix);
        logger.info("list news totalPage={}, pageNo=>{}", totalPage, pageNo);
        return Constants.NEWS_COLUMN_NAME;
    }

    public String aboutUs(ModelMap map){
        return "aboutUs";
    }

    public String contactUs(ModelMap map){
        return "contactUs";
    }


    @RequestMapping("/column/products/{pageNo}")
    public String productsByPage(@PathVariable("pageNo") int pageNo, ModelMap map){
        common("products", map);
        return products(map, pageNo, "/column/products/");
    }

    @RequestMapping("/column/news/{pageNo}")
    public String newsByPage(@PathVariable("pageNo") int pageNo, ModelMap map){
        common("news", map);
        return news(map, pageNo, "/column/news/");
    }

    @RequestMapping("/product/cate/{cateId}/{pageNo}")
    public String productsByCatePage(@PathVariable("cateId") int cateId,
                                     @PathVariable("pageNo") int pageNo,
                                     ModelMap map){
        return productsByCateId(cateId, pageNo, "/product/cate/"+cateId+"/",map);
    }

    @RequestMapping("/product/cate/{cateId}")
    public String productsByCate(@PathVariable("cateId") int cateId, ModelMap map){
        return productsByCateId(cateId, 1, "/product/cate/"+cateId+"/", map);
    }

    private String productsByCateId(int cateId, int pageNo, String urlPrefix, ModelMap map){
        common("products", map);
        long count = productService.countProductsByCateId(cateId);
        long totalPage = count%Constants.PRODUCT_PAGE_SIZE == 0 ?
                count/Constants.PRODUCT_PAGE_SIZE : (count/Constants.PRODUCT_PAGE_SIZE)+1;
        if(pageNo > totalPage){
            pageNo = 1;
        }
        List<Product> cateProducts = productService.queryProductsByCateId(cateId, Constants.PRODUCT_PAGE_SIZE, pageNo);
        map.put("list", cateProducts);
        map.put("totalPage", totalPage);
        map.put("currentPageNo", pageNo);
        map.put("urlPrefix", urlPrefix);
        logger.info("get category products totalPage=>{}, totalNum=>{}", totalPage, count);
        return "products";
    }

}
