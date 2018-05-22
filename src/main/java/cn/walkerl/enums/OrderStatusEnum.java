package cn.walkerl.enums;

import lombok.Getter;

@Getter
public enum OrderStatusEnum implements CodeEnum<Integer>{
	NEW(0,"新订单"),
	FINISHED(1,"完结"),
	CANCEL(2,"已取消"),
	;

	
	private Integer code;
	private String message;
	
	
	private OrderStatusEnum(Integer code, String message) {
		this.code = code;
		this.message = message;
	}
	
	
}
