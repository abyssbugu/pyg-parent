package com.pyg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {
	
	public static void main(String[] args) {		
		//启动入口方法
		//1,加载配置文件
		//2,自动启动内置Tomcat服务器
		SpringApplication.run(App.class, args);
		
	}

}
