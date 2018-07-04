<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>freemarker null值处理</title>
</head>
<body>
<h1>第一种null值处理方式：内建函数default</h1>
<h1>获取值(默认值)：${username?default("默认值")}</h1>
<h1>获取值()：${username?default("")}</h1>

<hr size="2" color="blue">
<h1>第二种null值处理方式：！</h1>
<h1>获取值(默认值)：${username!"默认值"}</h1>
<h1>获取值()：${username!}</h1>


<hr size="2" color="blue">
<h1>第三种null值处理方式：if指令</h1>
<h1>
	<#if username??>
		${username}
	</#if>
</h1>

</body>
</html>