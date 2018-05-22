package cn.walkerl.service;

import cn.walkerl.dataobject.SellerInfo;

public interface SellerService {

	SellerInfo findSellerInfoByOpenid(String openid);
}
