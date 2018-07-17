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

    List<Product> listProducts(int limit, int pageNo);

    List<Product> listHotProducts(int limit);

    long countProducts();

    Product loadProduct(int productId);

    List<Product> queryProductsByCateId(int cateId, int limit, int pageNo);

    long countProductsByCateId(int cateId);
}
