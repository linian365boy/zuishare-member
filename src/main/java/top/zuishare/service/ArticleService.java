package top.zuishare.service;

import top.zuishare.spi.model.Article;

import java.util.List;

/**
 * @author niange
 * @ClassName: ArticleService
 * @desp:
 * @date: 2017/12/1 下午11:06
 * @since JDK 1.7
 */
public interface ArticleService {

    List<Article> getListByPage(int pageNo, int pageSize);
    List<Article> getHotArticles(int limit);
}
