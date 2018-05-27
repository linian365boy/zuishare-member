package top.zuishare.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import top.zuishare.constants.BussinessConfig;
import top.zuishare.service.CompanyService;
import top.zuishare.service.WebConfigService;
import top.zuishare.spi.model.Company;
import top.zuishare.spi.model.WebConfig;

/**
 * @author niange
 * @ClassName: ExceptionController
 * @desp:
 * @date: 2017/11/25 下午4:24
 * @since JDK 1.7
 */
@Controller
public class ExceptionController implements ErrorController {

    private static final String ERROR_PATH = "/errors";
    @Autowired
    private CompanyService companyService;
    @Autowired
    private WebConfigService webConfigService;
    @Autowired
    private BussinessConfig bussinessConfig;

    @RequestMapping("/404")
    public String error404(ModelMap map){
        // 查公司信息
        Company company = companyService.loadCompany(bussinessConfig.getCompanyConfigPath());
        // 查网站信息
        WebConfig config = webConfigService.loadWebConfig(bussinessConfig.getWebConfigPath());
        map.put("company", company);
        map.put("config", config);
        return "errors/404";
    }

    @RequestMapping("/500")
    public String error500(ModelMap map){
        // 查公司信息
        Company company = companyService.loadCompany(bussinessConfig.getCompanyConfigPath());
        // 查网站信息
        WebConfig config = webConfigService.loadWebConfig(bussinessConfig.getWebConfigPath());
        map.put("company", company);
        map.put("config", config);
        return "errors/500";
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
}
