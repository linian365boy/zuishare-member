package top.zuishare.dao;

import org.apache.ibatis.annotations.Mapper;
import top.zuishare.spi.model.Advertisement;

import java.util.List;

/**
 * @author niange
 * @ClassName: AdDao
 * @desp:
 * @date: 2018/5/6 上午11:57
 * @since JDK 1.7
 */
@Mapper
public interface AdDao {
    // 获取首页的滚动图片，最多maxLimit张
    public List<Advertisement> getIndexAd(int maxLimit);

}
