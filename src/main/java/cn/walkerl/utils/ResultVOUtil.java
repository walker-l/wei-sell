package cn.walkerl.utils;

import cn.walkerl.VO.ResultVO;

public class ResultVOUtil {

	public static ResultVO success(Object object) {
		ResultVO resultVO = new ResultVO<>();
		resultVO.setData(object);
		resultVO.setCode(0);
		resultVO.setMsg("成功");
		return resultVO;
	}
	
	
	//不需要返回数据时，对象为null，调用上一个success方法
	public static ResultVO success() {
		return success(null);
	}
	
	public static ResultVO error(Integer code, String msg) {
		ResultVO resultVO = new ResultVO<>();
		resultVO.setCode(code);
		resultVO.setMsg(msg);
		return resultVO;
	}
}
