package top.zuishare.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import top.zuishare.constants.BussinessConfig;
import top.zuishare.constants.Constants;
import top.zuishare.dto.ResultDto;
import top.zuishare.service.*;
import top.zuishare.spi.model.*;
import top.zuishare.spi.util.Tools;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author niange
 * @ClassName: ViewController
 * @desp:
 * @date: 2018/6/7 下午10:27
 * @since JDK 1.7
 */

@Controller
public class ViewController {

    @Autowired
    private FeedbackService feedbackService;
    @Autowired
    private MailService mailService;
    @Autowired
    private ProductService productService;
    @Autowired
    private NewsService newsService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private BussinessConfig bussinessConfig;
    @Autowired
    private AdService adService;
    @Autowired
    private WebConfigService webConfigService;
    @Autowired
    private ColumnService columnService;
    @Autowired
    private CategoryService categoryService;

    private static final Logger logger = LoggerFactory.getLogger(ViewController.class);

    /**
     * addFeedback:客户留言反馈
     * @author tanfan
     * @return
     * @since JDK 1.7
     */
    @RequestMapping(value = "/feedback/add", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto addFeedback(Feedback feedback){
        logger.info("receive fedback |{}", feedback);
        ResultDto<Map<String, String>> vo = new ResultDto<>();
        Map<String,String> messageMap = new HashMap<>();
        try{
            //全名为空
            if(StringUtils.isBlank(feedback.getName())){
                messageMap.put("name", "Please fill the Full Name.");
            }
            //邮箱为空
            if(!StringUtils.isBlank(feedback.getEmail())){
                //邮箱格式
                if(!Tools.checkEmail(feedback.getEmail())){
                    messageMap.put("email", "Email address seems invalid.");
                }
            }
            //留言内容为空
            if(StringUtils.isBlank(feedback.getContent())){
                messageMap.put("content", "Please fill the Message.");
            }
            if(!messageMap.isEmpty()){
                vo.setCode(Constants.CODE_202);
                vo.setMessage("Please fill the required field or confirm the fields and send it again.");
                vo.setObj(messageMap);
                return vo;
            }
            feedbackService.addFeedback(feedback);
            StringBuffer sb = new StringBuffer();
            sb.append("客户留言内容见下：<br/>");
            sb.append(String.format("Full Name：%s<br/>",feedback.getName()));
            sb.append(String.format("Email Address：%s<br/>",feedback.getEmail()));
            sb.append(String.format("Telephone：%s<br/>",feedback.getTelePhone()));
            sb.append(String.format("Message：%s<br/>",feedback.getContent()));
            //异步sendEmail
            mailService.sendHtmlEmails(
                    String.format("inquiry----%s",feedback.getName()),
                    sb.toString());
            logger.info("客户留言信息|{}",feedback);
            vo.setCode(Constants.SUCCESS_CODE);
            vo.setMessage("succeed send email.");
        }catch(Exception e){
            logger.error("客户留言出现错误！",e);
            vo.setCode(Constants.ERROR_CODE);
            vo.setMessage("server error.");
        }
        logger.info("addFeedback return data => {}", vo);
        return vo;
    }

    @RequestMapping("/product/{productId}")
    public String productDetail(@PathVariable("productId") int productId, ModelMap map){
        Product product = productService.loadProduct(productId);
        if (product == null)
            return "redirect:errors/404";
        // 查公司信息
        Company company = companyService.loadCompany(bussinessConfig.getCompanyConfigPath());
        // 查网站信息
        WebConfig config = webConfigService.loadWebConfig(bussinessConfig.getWebConfigPath());
        // 查菜单
        List<Column> columns = columnService.queryIndexColumn();
        List<Category> categories = categoryService.queryCategory();
        map.put("config", config);
        map.put("columns", columns);
        map.put("product", product);
        map.put("company", company);
        map.put("currentColumn", columnService.getColumnByCode("products"));
        map.put("categories", categories);
        return "product_detail";
    }


    @RequestMapping("/news/{newsId}")
    public String newsDetail(@PathVariable("newsId") int newsId, ModelMap map){
        News news = newsService.loadNews(newsId);
        if (news == null) {
            return "redirect:errors/404";
        }
        // 查公司信息
        Company company = companyService.loadCompany(bussinessConfig.getCompanyConfigPath());
        // 查滚动图片
        List<Advertisement> ads = adService.queryIndexAd(bussinessConfig.getIndexAds());
        // 查网站信息
        WebConfig config = webConfigService.loadWebConfig(bussinessConfig.getWebConfigPath());
        // 查菜单
        List<Column> columns = columnService.queryIndexColumn();
        List<Category> categories = categoryService.queryCategory();
        map.put("columns", columns);
        map.put("config", config);
        map.put("ads", ads);
        map.put("news", news);
        map.put("company", company);
        map.put("currentColumn", columnService.getColumnByCode("news"));
        map.put("categories", categories);
        return "news_detail";
    }

}
