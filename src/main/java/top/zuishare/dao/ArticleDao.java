package top.zuishare.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.zuishare.spi.model.Article;

import java.util.List;

/**
 * @author niange
 * @ClassName: ArticleDao
 * @desp:
 * @date: 2017/12/1 下午11:07
 * @since JDK 1.7
 */
@Mapper
public interface ArticleDao {

    List<Article> getArticleByPage(@Param("start") int start,@Param("limit") int limit);

    List<Article> getHotArticles(int limit);

    long getArticleSize();

    List<Article> getPageListByCateId(@Param("categoryId") int categoryId, @Param("start") int start,@Param("limit") int pageSize);

    long getArticleSizeByCateId(int categoryId);

}
