package top.zuishare.service.impl;

import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.session.SessionProperties;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import top.zuishare.dao.InfoDao;
import top.zuishare.service.InfoService;
import top.zuishare.spi.model.Info;
import top.zuishare.spi.util.RedisUtil;

/**
 * @author niange
 * @ClassName: InfoServiceImpl
 * @desp:
 * @date: 2018/7/8 上午10:31
 * @since JDK 1.7
 */

@Service
public class InfoServiceImpl implements InfoService {
    @Autowired
    public InfoDao infoDao;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private Gson gson;
    private static final Logger logger = LoggerFactory.getLogger(InfoServiceImpl.class);

    public Info loadInfo(String code){
        Info info = null;
        String infoStr = stringRedisTemplate.opsForValue().get(RedisUtil.getInfoKeyByCode(code));
        if(StringUtils.isNotBlank(infoStr)){
            info = gson.fromJson(infoStr, Info.class);
        }else{
            info = loadInfoFromDb(code);
        }
        logger.info("load info data=>{}", info != null ? info.getCode(): null);
        return info;
    }

    private Info loadInfoFromDb(String code){
        Info info = infoDao.loadInfo(code);
        stringRedisTemplate.opsForValue().set(RedisUtil.getInfoKeyByCode(code), gson.toJson(info));
        return info;
    }
}
