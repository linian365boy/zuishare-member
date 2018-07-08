package top.zuishare.dao;

import org.apache.ibatis.annotations.Mapper;
import top.zuishare.spi.model.Info;

/**
 * @author niange
 * @ClassName: InfoDao
 * @desp:
 * @date: 2018/7/8 上午10:32
 * @since JDK 1.7
 */
@Mapper
public interface InfoDao {

    Info loadInfo(String code);

}
