package top.zuishare.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.zuishare.dao.NewsDao;
import top.zuishare.service.NewsService;
import top.zuishare.spi.model.News;

import java.util.List;

/**
 * @author niange
 * @ClassName: NewsServiceImpl
 * @desp:
 * @date: 2018/5/20 下午9:34
 * @since JDK 1.7
 */

@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsDao newsDao;

    @Override
    public List<News> listNews(int colId, int limit) {
        return newsDao.getIndexNews(colId, limit);
    }

    @Override
    public News loadNews(int newsId) {
        return newsDao.loadNews(newsId);
    }
}
