package cn.walkerl.utils;

import java.util.Random;

public class KeyUtil {

	/**
	 * 生成唯一的主键
	 * 格式：时间+随机数(6位)
	 * @return
	 */
	public static synchronized String genUniqueKey() {
		Random random = new Random();
		Integer number = random.nextInt(900000) + 100000;
		
		return System.currentTimeMillis() + String.valueOf(number);
	}
	
	
	/**
	 * 生成唯一的商品ID
	 * 格式：时间+随机数（4位）
	 * @return
	 */
	public static synchronized String genUniqueKey4() {
		Random random = new Random();
		Integer number = random.nextInt(9000) + 1000;
		
		return System.currentTimeMillis() + String.valueOf(number);
	}

}
