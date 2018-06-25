package com.pyg.jedis;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class SpringJedis {

	@Test
	public void test01() {

		// 加载spring配置文件
		ApplicationContext app = new ClassPathXmlApplicationContext("classpath*:spring/applicationContext-redis.xml");
		JedisPool jp = app.getBean(JedisPool.class);
		// 从连接池中获取jedis对象
		Jedis jedis = jp.getResource();
		// 使用redis命令，操作redis服务器
		jedis.set("itcast", "传智播客3");
		
	

		// 获取值
		String value = jedis.get("itcast");
		System.out.println(value);

	}

}
