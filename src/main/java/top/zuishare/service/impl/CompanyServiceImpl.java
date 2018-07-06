package top.zuishare.service.impl;

import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import top.zuishare.service.CompanyService;
import top.zuishare.spi.dto.ConstantVariable;
import top.zuishare.spi.model.Company;
import top.zuishare.spi.util.Tools;
import top.zuishare.util.RedisUtil;

/**
 * @author niange
 * @ClassName: CompanyServiceImpl
 * @desp:
 * @date: 2018/5/6 上午11:49
 * @since JDK 1.7
 */
@Service
public class CompanyServiceImpl implements CompanyService {
	
	@Autowired
    private StringRedisTemplate stringRedisTemplate;

    private static final Logger logger = LoggerFactory.getLogger(CompanyServiceImpl.class);

    @Override
    public Company loadCompany(String path) {
    	// query from redis
    	String companyStr = stringRedisTemplate.opsForValue().get(RedisUtil.getCompanyKey());
    	if(companyStr == null) {
    		// query from file
    		companyStr = Tools.getJsonStrFromPath(path);
    	}
        logger.info("param path=>{}, company json str =>{}", path, companyStr);
        return new GsonBuilder().setDateFormat(ConstantVariable.DFSTR).create().fromJson(companyStr, Company.class);
    }
}
