package cn.walkerl.VO;

import java.io.Serializable;

import lombok.Data;

/**
 * http请求返回的最外层对象
 * @author WalkerL
 * @date: 2018年4月13日 
 * @param <T>
 */

@Data
public class ResultVO<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4356425952901215018L;

	/** 错误码. */
	private Integer code;
	
	/** 提示信息. */
	private String msg;
	
	/** 具体内容. */
	private T data;
}
