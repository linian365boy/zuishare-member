package top.zuishare.service.impl;

import com.google.gson.GsonBuilder;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import top.zuishare.service.WebConfigService;
import top.zuishare.spi.dto.ConstantVariable;
import top.zuishare.spi.model.WebConfig;
import top.zuishare.spi.util.Tools;
import top.zuishare.util.RedisUtil;

/**
 * @author niange
 * @ClassName: WebConfigServiceImpl
 * @desp:
 * @date: 2018/5/6 上午11:50
 * @since JDK 1.7
 */
@Service
public class WebConfigServiceImpl implements WebConfigService {
	
	@Autowired
    private StringRedisTemplate stringRedisTemplate;
    private static final Logger logger = LoggerFactory.getLogger(WebConfigServiceImpl.class);

    @Override
    public WebConfig loadWebConfig(String path) {
    	// query from redis
    	String webconfigStr = stringRedisTemplate.opsForValue().get(RedisUtil.getCompanyKey());
    	if(StringUtils.isBlank(webconfigStr)) {
    		// query from file
    		webconfigStr = Tools.getJsonStrFromPath(path);
    	}
        logger.info("param path=>{}, webconfig json str =>{}", path, webconfigStr);
        return new GsonBuilder().setDateFormat(ConstantVariable.DFTSTR).create().fromJson(webconfigStr, WebConfig.class);
    }
}
