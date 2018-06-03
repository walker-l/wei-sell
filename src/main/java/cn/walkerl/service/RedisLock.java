package cn.walkerl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RedisLock {

	@Autowired
	private StringRedisTemplate redisTemplate;
	
	/**
	 * 加锁
	 * @param key ：传入的productId
	 * @param value ： 传入的预设的过期时间点的值（当前时间+超时时间）
	 * @return
	 */
	public boolean lock(String key, String value) {
		/**
		 * 如果可以成功设置，则返回true。
		 * 锁住返回true，否则返回false。
		 * 锁住表示已经是单线程状态，可以确保自己的访问是安全的
         * 没锁住表示会和其他用户的访问存在冲突。
		 * 该方法对应redis中的setnx。
		 */
		if (redisTemplate.opsForValue().setIfAbsent(key, value)) {
			return true;
		}
		
		//取出redis中key对应的value值（注意：这个value跟传入的value不是同一个东西）
		String currentValue = redisTemplate.opsForValue().get(key);
		/**
		 * 如果锁过期了(redis中key对应的value值大于当前系统时间),
		 * 解锁，避免死锁的情况。
		 * 解决死锁和多个线程抢夺死锁的情况
		 */
		if (!StringUtils.isEmpty(currentValue)
				&& Long.parseLong(currentValue) < System.currentTimeMillis()) {
			/**
			 * 获取上一个锁的时间
			 * getAndSet方法：get当前redis中key对应的value值，然后把第二个参数设置进value里。
			 * getAndSet方法这行代码一次只会有一个线程执行,总有个先后。 
			 * 例如：A和B同时运行到这里，A先运行getAndSet方法，A拿到的是之前的过期的锁的时间。
			 * B后运行该方法，B拿到的是A设置后的未过期的锁的时间。
			 */
			String oldValue = redisTemplate.opsForValue().getAndSet(key, value);
			
			/**
			 * 锁住的情况下，多个线程同时都运行到这里，
			 * 只会有其中一个线程拿到锁(第一个运行getAndSet方法那个线程)。
			 */
			if (!StringUtils.isEmpty(oldValue) && oldValue.equals(currentValue)) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * 解锁
	 * @param key
	 * @param value
	 */
	public void unlock(String key, String value) {
		try {
			String currentValue = redisTemplate.opsForValue().get(key);
			if (!StringUtils.isEmpty(currentValue) && currentValue.equals(value)) {
				redisTemplate.opsForValue().getOperations().delete(key);
			}
		} catch (Exception e) {
			log.error("【redis分布式锁】解锁异常，{}", e);
		}
	}
	
	
}
