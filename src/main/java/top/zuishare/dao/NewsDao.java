package top.zuishare.dao;

import org.apache.ibatis.annotations.Mapper;
import top.zuishare.spi.model.News;

import java.util.List;

/**
 * @author niange
 * @ClassName: NewsDao
 * @desp:
 * @date: 2018/5/21 下午10:52
 * @since JDK 1.7
 */

@Mapper
public interface NewsDao {

    List<News> getIndexNews(int limit);

}
