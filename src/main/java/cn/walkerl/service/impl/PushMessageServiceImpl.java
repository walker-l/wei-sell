package cn.walkerl.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.walkerl.config.WechatAccountConfig;
import cn.walkerl.dto.OrderDTO;
import cn.walkerl.service.PushMessageService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;


@Service("PushMessageServiceImpl")
@Slf4j
public class PushMessageServiceImpl implements PushMessageService {

	@Autowired
	private WxMpService wxMpService;
	
	@Autowired
	private WechatAccountConfig accountConfig;
	
	@Override
	public void orderStatus(OrderDTO orderDTO) {
		WxMpTemplateMessage templateMessage = new WxMpTemplateMessage();
		
		//调用方法，传入参数，构建微信模板消息
		templateMessage.setTemplateId(accountConfig.getTemplateId().get("orderStatus"));
		templateMessage.setToUser(orderDTO.getBuyerOpenid());
		
		//构建微信模板消息的具体内容，注意，list的键名要和微信公众平台模板里的键名一致
		List<WxMpTemplateData> data = Arrays.asList(
				new WxMpTemplateData("first", "亲，请记得收货。"),
				//商家名称
				new WxMpTemplateData("keyword1", "微信点餐"),
				//商家电话
				new WxMpTemplateData("keyword2", "188688124864"),
				//订单号
				new WxMpTemplateData("keyword3", orderDTO.getOrderId()),
				//订单状态
				new WxMpTemplateData("keyword4", orderDTO.getOrderStatusEnum().getMessage()),
				//总价
				new WxMpTemplateData("keyword5", "￥" + orderDTO.getOrderAmount()),
				new WxMpTemplateData("remark", "欢迎再次光临！")
				);
		
		templateMessage.setData(data);
		
		//发送模板消息。另外要注意微信公众平台里IP白名单的设置，该设置会影响信息是否正常发送
		try {
			wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
		} catch (WxErrorException e) {
			//消息发送失败并不影响主业务，故此不抛出异常
			log.error("【微信模板消息】发送失败，{}", e);
		}

	}

}
