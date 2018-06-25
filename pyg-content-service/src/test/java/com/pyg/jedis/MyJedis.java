package com.pyg.jedis;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class MyJedis {
	
	
	/**
	 * 需求：不使用连接池，简单操作
	 */
	@Test
	public void test01() {
		
		//创建jedis客户端对象，连接redis服务器
		Jedis jedis = new Jedis("192.168.72.150", 6379);
		//使用redis命令，操作redis服务器
		jedis.set("bazz", "pretty good");
		
		//获取值
		String value = jedis.get("bazz");
		System.out.println(value);
		
	}
	
	/**
	 * 需求：不使用连接池，简单操作
	 */
	@Test
	public void test02() {
		
		//创建连接池对象
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		//设置最大连接池
		poolConfig.setMaxTotal(3000);
		//最大空闲数
		poolConfig.setMaxIdle(300);
		
		//创建连接池对象
		JedisPool jp = new JedisPool(poolConfig, "192.168.72.150", 6379);
		
		//从连接池中获取一个jedis对象
		Jedis jedis = jp.getResource();
		
		
		//使用redis命令，操作redis服务器
		jedis.set("bazz", "hello word");
		
		//获取值
		String value = jedis.get("bazz");
		System.out.println(value);
		
	}
	

}
