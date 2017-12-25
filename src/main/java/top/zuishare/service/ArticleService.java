package top.zuishare.service;

import top.zuishare.dto.PageDto;
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

    PageDto<Article> getListByPage(int pageNo);
    List<Article> getHotArticles(int limit);
    Article loadOne(long id);
    PageDto<Article> getPageListByCateId(int categoryId, int pageNo);
}
