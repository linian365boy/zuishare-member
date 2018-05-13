package top.zuishare.dao;

import org.apache.ibatis.annotations.Mapper;
import top.zuishare.spi.model.Column;

import java.util.List;

/**
 * @author niange
 * @ClassName: ColumnDao
 * @desp:
 * @date: 2018/5/6 下午4:54
 * @since JDK 1.7
 */
@Mapper
public interface ColumnDao {
    List<Column> getIndexColumn();
}
