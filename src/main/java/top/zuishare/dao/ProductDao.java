package top.zuishare.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.zuishare.spi.model.Product;

import java.util.List;

/**
 * @author niange
 * @ClassName: ProductDao
 * @desp:
 * @date: 2018/5/21 下午10:52
 * @since JDK 1.7
 */

@Mapper
public interface ProductDao {

    List<Product> getProducts(@Param("limit") int limit, @Param("start") int start);

    long countProducts();

    List<Product> getIndexProductsList(int limit);

    Product loadProduct(int productId);

    List<Product> queryProductsByCateId(@Param("cateId") int cateId,@Param("limit") int limit,@Param("start") int start);

    long countProductsByCateId(int cateId);
}
