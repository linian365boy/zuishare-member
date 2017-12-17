package top.zuishare.service;

import top.zuishare.spi.model.ArticleCategory;

import java.util.List;

/**
 * @author niange
 * @ClassName: ArticleCategoryServiceImpl
 * @desp:
 * @date: 2017/12/2 上午12:01
 * @since JDK 1.7
 */
public interface ArticleCategoryService {
    /**
     * 获取所有正常的分类，有排序
     * @return
     */
    public List<ArticleCategory> getList();
}
