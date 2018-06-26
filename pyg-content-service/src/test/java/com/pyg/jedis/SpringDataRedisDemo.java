package com.pyg.jedis;

import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath*:spring/applicationContext-redis.xml")
public class SpringDataRedisDemo {
	
	//注入redis模板对象
	@Autowired
	private RedisTemplate redisTemplate;
	
	/**
	 * String类型
	 * 
	 */
	@Test
	public void strKV() {
		redisTemplate.boundValueOps("itcast").set("咱们都是中国人");
		//获取值
		String value  = (String) redisTemplate.boundValueOps("itcast").get();
		System.out.println(value);
	}
	
	/**
	 * Set类型
	 * 
	 */
	@Test
	public void strSet() {
		redisTemplate.boundSetOps("user").add("刘备");
		redisTemplate.boundSetOps("user").add("张飞");
		redisTemplate.boundSetOps("user").add("关羽");
		//获取值
		Set set = redisTemplate.boundSetOps("user").members();
		System.out.println(set);
	
	}
	
	
	/**
	 * List类型
	 * 
	 */
	@Test
	public void strList() {
		redisTemplate.boundListOps("myList").rightPush("唐僧");
		redisTemplate.boundListOps("myList").rightPush("孙悟空");
		redisTemplate.boundListOps("myList").rightPush("八戒");

		//获取值
		//String value = (String) redisTemplate.boundListOps("myList").leftPop();
		List list = redisTemplate.boundListOps("myList").range(0, -1);
		
		System.out.println(list);
	
	}
	
	/**
	 * hash类型
	 * 
	 */
	@Test
	public void strHash() {
		redisTemplate.boundHashOps("user1").put("age", 23);
		redisTemplate.boundHashOps("user1").put("username", "沙和尚");
		redisTemplate.boundHashOps("user1").put("address", "流沙河");

		//获取值
		List list = redisTemplate.boundHashOps("user1").values();
		
		System.out.println(list);
	
	}
	
	

}
