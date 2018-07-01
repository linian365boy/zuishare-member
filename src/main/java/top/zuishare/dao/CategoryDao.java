package top.zuishare.dao;

import org.apache.ibatis.annotations.Mapper;
import top.zuishare.spi.model.Category;

import java.util.List;

/**
 * @author niange
 * @ClassName: CategoryDao
 * @desp:
 * @date: 2018/6/24 上午11:30
 * @since JDK 1.7
 */

@Mapper
public interface CategoryDao {
    List<Category> queryCategory();
}
