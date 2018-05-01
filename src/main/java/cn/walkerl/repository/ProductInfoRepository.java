package cn.walkerl.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cn.walkerl.dataobject.ProductInfo;

/**
 * 商品信息DAO
 * @author WalkerL
 * @date: 2018年4月10日
 */
public interface ProductInfoRepository extends JpaRepository<ProductInfo, String> {

	@Query("select o from ProductInfo o where o.productId=:productId")
	public ProductInfo findByProductId(@Param("productId") String productId);
	
	@Query("select o from ProductInfo o where o.productStatus=:productStatus")
	public List<ProductInfo> findByProductStatus(@Param("productStatus") Integer productStatus);
}
