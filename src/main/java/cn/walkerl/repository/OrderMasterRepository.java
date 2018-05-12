package cn.walkerl.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import cn.walkerl.dataobject.OrderMaster;

public interface OrderMasterRepository extends JpaRepository<OrderMaster, String> {

	OrderMaster findByOrderId(String orderId);
	
	Page<OrderMaster> findByBuyerOpenid(String buyerOpenid, Pageable pageable);
}
