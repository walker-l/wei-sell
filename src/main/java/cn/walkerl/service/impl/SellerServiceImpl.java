package cn.walkerl.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.walkerl.dataobject.SellerInfo;
import cn.walkerl.repository.SellerInfoRepository;
import cn.walkerl.service.SellerService;

@Service("SellerServiceImpl")
public class SellerServiceImpl implements SellerService {

	
	@Autowired
	private SellerInfoRepository repository;
	
	
	@Override
	public SellerInfo findSellerInfoByOpenid(String openid) {
		return repository.findByOpenid(openid);
	}

}
