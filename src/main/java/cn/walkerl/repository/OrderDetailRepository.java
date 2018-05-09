package cn.walkerl.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.walkerl.dataobject.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, String> {

	List<OrderDetail> findByOrderId(String orderId);
}
