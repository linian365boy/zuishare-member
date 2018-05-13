package top.zuishare.service.impl;

import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import top.zuishare.service.CompanyService;
import top.zuishare.spi.dto.ConstantVariable;
import top.zuishare.spi.model.Company;
import top.zuishare.spi.util.Tools;

/**
 * @author niange
 * @ClassName: CompanyServiceImpl
 * @desp:
 * @date: 2018/5/6 上午11:49
 * @since JDK 1.7
 */
@Service
public class CompanyServiceImpl implements CompanyService {

    private static final Logger logger = LoggerFactory.getLogger(CompanyServiceImpl.class);

    @Override
    public Company loadCompany(String path) {
        String jsonStr = Tools.getJsonStrFromPath(path);
        logger.info("从文件|{}解析的json串为|{}",path,jsonStr);
        return new GsonBuilder().setDateFormat(ConstantVariable.DFSTR).create().fromJson(jsonStr, Company.class);
    }
}
