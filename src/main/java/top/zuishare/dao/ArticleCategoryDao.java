package top.zuishare.dao;

import org.apache.ibatis.annotations.Mapper;
import top.zuishare.spi.model.ArticleCategory;

import java.util.List;

/**
 * @author niange
 * @ClassName: ArticleCategoryDao
 * @desp:
 * @date: 2017/12/1 下午11:36
 * @since JDK 1.7
 */
@Mapper
public interface ArticleCategoryDao {
    /**
     * 获取全部正常状态的文章分类
     * @return
     */
    List<ArticleCategory> getList();
}
