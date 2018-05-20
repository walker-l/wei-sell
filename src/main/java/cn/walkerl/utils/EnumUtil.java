package cn.walkerl.utils;

import cn.walkerl.enums.CodeEnum;

public class EnumUtil {

	/**
	 * 
	 * @param code  
	 * @param enumClass  枚举的class
	 * @return
	 */
	public static <T extends CodeEnum<?>> T getByCode(Integer code, Class<T> enumClass) {
		for (T each : enumClass.getEnumConstants()) {
			if (code.equals(each.getCode())) {
				return each;
			}
		}
		return null;
	}
}
