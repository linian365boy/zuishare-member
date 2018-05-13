package top.zuishare.service.impl;

import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import top.zuishare.service.WebConfigService;
import top.zuishare.spi.dto.ConstantVariable;
import top.zuishare.spi.model.WebConfig;
import top.zuishare.spi.util.Tools;

/**
 * @author niange
 * @ClassName: WebConfigServiceImpl
 * @desp:
 * @date: 2018/5/6 上午11:50
 * @since JDK 1.7
 */
@Service
public class WebConfigServiceImpl implements WebConfigService {

    private static final Logger logger = LoggerFactory.getLogger(WebConfigServiceImpl.class);

    @Override
    public WebConfig loadWebConfig(String path) {
        String jsonStr = Tools.getJsonStrFromPath(path);
        logger.info("从文件|{}解析的json串为|{}",path,jsonStr);
        return new GsonBuilder().setDateFormat(ConstantVariable.DFTSTR).create().fromJson(jsonStr, WebConfig.class);
    }
}
