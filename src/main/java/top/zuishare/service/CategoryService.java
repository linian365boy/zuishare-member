package top.zuishare.service;

import top.zuishare.spi.model.Category;

import java.util.List;

/**
 * @author niange
 * @ClassName: CategoryService
 * @desp:
 * @date: 2018/6/24 上午11:17
 * @since JDK 1.7
 */
public interface CategoryService {
    List<Category> queryCategory();
}
