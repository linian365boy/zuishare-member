package top.zuishare.service;

import top.zuishare.spi.model.News;

import java.util.List;

/**
 * @author niange
 * @ClassName: NewsService
 * @desp:
 * @date: 2018/5/20 下午8:03
 * @since JDK 1.7
 */
public interface NewsService {

    List<News> listNews(int colId, int limit);

    News loadNews(int newsId);

}
