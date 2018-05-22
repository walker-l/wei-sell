package cn.walkerl.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.walkerl.dataobject.SellerInfo;

public interface SellerInfoRepository extends JpaRepository<SellerInfo, String> {

	SellerInfo findByOpenid(String openid);
}
