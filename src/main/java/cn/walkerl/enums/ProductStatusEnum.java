package cn.walkerl.enums;

import lombok.Getter;

@Getter
public enum ProductStatusEnum {
	UP(0,"在架"),
	DOWN(1,"下架")
	;
	
	private  Integer code;
	
	private String message;
	
	/**
	 * 构造方法
	 * @param code
	 * @param message
	 */
	ProductStatusEnum(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

}
