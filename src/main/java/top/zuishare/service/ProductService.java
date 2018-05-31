package top.zuishare.service;

import top.zuishare.spi.model.Product;

import java.util.List;

/**
 * @author niange
 * @ClassName: ProductService
 * @desp:
 * @date: 2018/5/20 下午8:03
 * @since JDK 1.7
 */
public interface ProductService {

    public List<Product> listProducts(int limit);

    public List<Product> queryIndexProduct(int limit);

}