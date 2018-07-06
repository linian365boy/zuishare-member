package top.zuishare.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import top.zuishare.spi.model.News;

/**
 * @author niange
 * @ClassName: NewsDao
 * @desp:
 * @date: 2018/5/21 下午10:52
 * @since JDK 1.7
 */

@Mapper
public interface NewsDao {

    List<News> getNewsList();

    News loadNews(int newsId);

    long countNews();

}
