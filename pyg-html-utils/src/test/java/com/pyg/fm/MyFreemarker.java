package com.pyg.fm;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.processing.SupportedAnnotationTypes;

import org.junit.Test;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class MyFreemarker {
	
	/**
	 * 需求：freemarker入门案例
	 * 要求：在静态页面中展示一些基本数据：string,long,double....
	 * 模板语法：${key} 获取需要展示数据值 ,花括号中key就是map的key
	 * 生成HTML页面三要素：
	 * 1，模板（需求手动编写模板页面）
	 * 2，api
	 * 3，数据
	 * @throws Exception 
	 */
	@Test
	public void test01() throws Exception {
		
		//创建核心配置对象
		Configuration cf = new Configuration(Configuration.getVersion());
		//指定模板编码
		cf.setDefaultEncoding("utf-8");
		//设置模板路径
		cf.setDirectoryForTemplateLoading(
				new File("/Users/abyss/Dev/Demos/pyg-parent/pyg-html-utils/src"));
		
		//读取模板文件，获取模板对象
		Template template = cf.getTemplate("hello.ftl");
		
		//准备数据
		Map<String,Object> dataModel = new HashMap<String, Object>();
		dataModel.put("name", "凤姐年薪50万!");
		
		//创建输出流对象，把文件写入磁盘
		Writer out = new FileWriter(new File("/Users/abyss/Dev/lessons/ftl/out/first.html"));
		
		//生成HTML
		template.process(dataModel, out);
		
		out.close();
		
	}
	
	
	/**
	 * 需求：freemarker指令-assign指令
	 * 作用：定义常量
	 * 生成HTML页面三要素：
	 * 1，模板（需求手动编写模板页面）
	 * 2，api
	 * 3，数据
	 * @throws Exception 
	 */
	@Test
	public void test02() throws Exception {
		
		//创建核心配置对象
		Configuration cf = new Configuration(Configuration.getVersion());
		//指定模板编码
		cf.setDefaultEncoding("utf-8");
		//设置模板路径
		cf.setDirectoryForTemplateLoading(
				new File("/Users/abyss/Dev/Demos/pyg-parent/pyg-html-utils/src"));
		
		//读取模板文件，获取模板对象
		Template template = cf.getTemplate("assain.ftl");
	
		//创建输出流对象，把文件写入磁盘
		Writer out = new FileWriter(new File("/Users/abyss/Dev/lessons/ftl/out/assign.html"));
		
		//生成HTML
		template.process(null, out);
		
		out.close();
		
	}
	

	/**
	 * 需求：freemarker指令-ifelse指令
	 * 作用：定义常量
	 * 生成HTML页面三要素：
	 * 1，模板（需求手动编写模板页面）
	 * 2，api
	 * 3，数据
	 * @throws Exception 
	 */
	@Test
	public void test03() throws Exception {
		
		//创建核心配置对象
		Configuration cf = new Configuration(Configuration.getVersion());
		//指定模板编码
		cf.setDefaultEncoding("utf-8");
		//设置模板路径
		cf.setDirectoryForTemplateLoading(
				new File("/Users/abyss/Dev/Demos/pyg-parent/pyg-html-utils/src"));
		
		//读取模板文件，获取模板对象
		Template template = cf.getTemplate("ifelse.ftl");
	
		//准备数据
		Map<String,Object> dataModel = new HashMap<String, Object>();
		dataModel.put("flag",3);
		
		//创建输出流对象，把文件写入磁盘
		Writer out = new FileWriter(new File("/Users/abyss/Dev/lessons/ftl/out/ifelse.html"));
		
		//生成HTML
		template.process(dataModel, out);
		
		out.close();
		
	}
	
	
	/**
	 * 需求：freemarker指令-list指令
	 * 作用：定义常量
	 * 生成HTML页面三要素：
	 * 1，模板（需求手动编写模板页面）
	 * 2，api
	 * 3，数据
	 * 例如：有以下集合数据
	 * List<Person> pList = new Arraylist<>();
	 * jsp如何集合值：
	 * <c:foreach item="${pList}" var="p" varStatus="ps">
	 * ${p.id}
	 * ${p.username}
	 * .......
	 * </c:foreach>
	 * FTL如何获取：
	 * <#list pList as p>
	 * ${p.id}
	 * ${p.username}
	 * ......
	 * </#list>
	 * 需求：奇数行显示红色，偶数行 ：蓝色  （ifelse）
	 * 
	 * @throws Exception 
	 */
	@Test
	public void test04() throws Exception {
		
		//创建核心配置对象
		Configuration cf = new Configuration(Configuration.getVersion());
		//指定模板编码
		cf.setDefaultEncoding("utf-8");
		//设置模板路径
		cf.setDirectoryForTemplateLoading(
				new File("/Users/abyss/Dev/Demos/pyg-parent/pyg-html-utils/src"));
		
		//读取模板文件，获取模板对象
		Template template = cf.getTemplate("list.ftl");
	
		//创建集合对象
		List<Person> pList = new ArrayList<Person>();
		//创建person对象
		Person p1 = new Person();
		p1.setId(1000000);
		p1.setUsername("赵敏");
		p1.setAge(13);
		p1.setSex("女");
		p1.setAddress("beijing");
		
		
		Person p2 = new Person();
		p2.setId(100000001);
		p2.setUsername("周芷若");
		p2.setAge(18);
		p2.setSex("女");
		p2.setAddress("sichuan");
		
		
		
		Person p3 = new Person();
		p3.setId(100000002);
		p3.setUsername("小昭");
		p3.setAge(16);
		p3.setSex("女");
		p3.setAddress("bosi");
		
		pList.add(p1);
		pList.add(p2);
		pList.add(p3);
		
		//准备数据
		Map<String,Object> dataModel = new HashMap<String, Object>();
		dataModel.put("pList",pList);
		
		//创建输出流对象，把文件写入磁盘
		Writer out = new FileWriter(new File("/Users/abyss/Dev/lessons/ftl/out/list.html"));
		
		//生成HTML
		template.process(dataModel, out);
		
		out.close();
		
	}
	
	
	/**
	 * 需求：freemarker null值处理
	 * 作用：定义常量
	 * 生成HTML页面三要素：
	 * 1，模板（需求手动编写模板页面）
	 * 2，api
	 * 3，数据
	 * 处理null值3种方式：
	 * 1，dufault内建函数
	 * 语法：变量?default("默认值")
	 * 2,!
	 * 语法：
	 * 1）变量!"默认值"
	 * 2）变量!
	 * 3,if指令
	 * 语法：
	 * <#if username??>
	 * 	${username}
	 * </#if>
	 * @throws Exception 
	 */
	@Test
	public void test05() throws Exception {
		
		//创建核心配置对象
		Configuration cf = new Configuration(Configuration.getVersion());
		//指定模板编码
		cf.setDefaultEncoding("utf-8");
		//设置模板路径
		cf.setDirectoryForTemplateLoading(
				new File("/Users/abyss/Dev/Demos/pyg-parent/pyg-html-utils/src"));
		
		//读取模板文件，获取模板对象
		Template template = cf.getTemplate("userinfo.ftl");
	
		//准备数据
		Map<String,Object> dataModel = new HashMap<String, Object>();
		dataModel.put("username","小昭");
		
		//创建输出流对象，把文件写入磁盘
		Writer out = new FileWriter(new File("/Users/abyss/Dev/lessons/ftl/out/userinfo.html"));
		
		//生成HTML
		template.process(dataModel, out);
		
		out.close();
		
	}
	
	/**
	 * 时间类型格式化
	 * @throws Exception
	 */
	@Test
	public void test06() throws Exception {
		
		//创建核心配置对象
		Configuration cf = new Configuration(Configuration.getVersion());
		//指定模板编码
		cf.setDefaultEncoding("utf-8");
		//设置模板路径
		cf.setDirectoryForTemplateLoading(
				new File("/Users/abyss/Dev/Demos/pyg-parent/pyg-html-utils/src"));
		
		//读取模板文件，获取模板对象
		Template template = cf.getTemplate("date.ftl");
	
		//准备数据
		Map<String,Object> dataModel = new HashMap<String, Object>();
		dataModel.put("today",new Date());
		
		//创建输出流对象，把文件写入磁盘
		Writer out = new FileWriter(new File("/Users/abyss/Dev/lessons/ftl/out/date.html"));
		
		//生成HTML
		template.process(dataModel, out);
		
		out.close();
		
	}
	
	

}
