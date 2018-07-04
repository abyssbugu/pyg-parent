<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>freemarker指令-list指令</title>
</head>
<body>
<table style="width: 1000px;height: 300px;" border="1">
	<tr>
		<td>角标</td>
		<td>编号</td>
		<td>姓名</td>
		<td>年龄</td>
		<td>性别</td>
		<td>地址</td>
		<td>操作</td>
	</tr>
	<#list pList as p>
	<#if p_index%2==0>
		<tr style="background-color: blue;">
	<#else>
		<tr style="background-color: red;">
	</#if>
	
		<td>${p_index}</td>
		<td>${p.id}</td>
		<td>${p.username!}</td>
		<td>${p.age!}</td>
		<td>${p.sex!}</td>
		<td>${p.address!}</td>
		<td>
			<a href="">删除</a><br>
			<a href="">修改</a>
		</td>
	</tr>
	</#list>
</table>
<h1>共有 ${pList?size} 条</h1>
</body>
</html>