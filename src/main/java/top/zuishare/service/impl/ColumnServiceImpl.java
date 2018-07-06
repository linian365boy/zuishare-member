package top.zuishare.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import top.zuishare.constants.Constants;
import top.zuishare.dao.ColumnDao;
import top.zuishare.service.ColumnService;
import top.zuishare.spi.model.Column;
import top.zuishare.util.RedisUtil;

/**
 * @author niange
 * @ClassName: ColumnServiceImpl
 * @desp:
 * @date: 2018/5/6 上午11:51
 * @since JDK 1.7
 */
@Service
public class ColumnServiceImpl implements ColumnService {

    private static final Logger logger = LoggerFactory.getLogger(ColumnServiceImpl.class);
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private Gson gson;
    @Autowired
    private ColumnDao columnDao;

    @Override
    public List<Column> queryIndexColumn() {
        List<Column> columns = null;
        //1、从缓存中获取
        try{
            //先从缓存中获取
            String categoryStr = stringRedisTemplate.opsForValue().get(RedisUtil.getIndexColumnKey());
            //json 反序列化
            columns = gson.fromJson(categoryStr,
                    new TypeToken<ArrayList<Column>>() {
                    }.getType());
            if (CollectionUtils.isEmpty(columns)) {
                //2、再DB中获取
                columns = queryIndexColumnFromDB();
                logger.info("get index column from db");
            }else{
                logger.info("get index column from redis");
            }
        }catch (Exception e){
            logger.error("redis happend error. query column from db.", e);
            //再DB中获取
            columns = queryIndexColumnFromDB();
        }
        logger.info("get all index columns => {}", columns);
        return columns;
    }


    private List<Column> queryIndexColumnFromDB(){
        List<Column> columns = columnDao.getIndexColumn();
        // 设置过期时间30天
        if (!CollectionUtils.isEmpty(columns)) {
            stringRedisTemplate.opsForValue()
                    .set(RedisUtil.getIndexColumnKey(),
                            gson.toJson(columns), Constants.TIMEOUTDAYS, TimeUnit.DAYS);
        }
        return columns;
    }

    public Column getColumnByCode(String code){
        return columnDao.getColumnByCode(code);
    }

}