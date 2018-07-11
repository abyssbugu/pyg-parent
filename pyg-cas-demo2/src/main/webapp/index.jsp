<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录成功访问的页面</title>
</head>
<body>
<h1>cas测试系统二：欢迎使用cas单点登录系统： 现在是用户：<%= request.getRemoteUser() %> 登录</h1>

<a href="http://192.168.72.150:8080/cas/logout?service=http://www.baidu.com">单点退出/注销</a>
</body>
</html>