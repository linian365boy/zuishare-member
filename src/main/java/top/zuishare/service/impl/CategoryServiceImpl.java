package top.zuishare.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.zuishare.dao.CategoryDao;
import top.zuishare.service.CategoryService;
import top.zuishare.spi.model.Category;

import java.util.List;

/**
 * @author niange
 * @ClassName: CategoryServiceImpl
 * @desp:
 * @date: 2018/6/24 上午11:17
 * @since JDK 1.7
 */

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    public CategoryDao categoryDao;

    @Override
    public List<Category> queryCategory(){
        return categoryDao.queryCategory();
    }

}
