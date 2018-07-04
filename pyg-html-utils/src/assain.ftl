<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>freemarker指令-assign</title>
<#include "head.ftl"/>
<#--定义常量-->
<#assign linkman="张三丰在武当山"/>
<#--定义常量对象-->
<#assign user={"username":"张三丰","age":12,"address":"武当山"}/>
<#--定义常量json字符串-->
<#assign userinfo='{"username":"张无忌","age":12,"address":"武当山","gongfu":"九阳神功"}'/>
<#--把常量json字符串转换为json对象，赋值给常量对象-->
<#assign users=userinfo?eval/>
</head>
<body>
<h1>您好!${linkman}</h1>
<h1>姓名：${users.username}</h1>
<h1>年龄：${users.age}</h1>
<h1>地址：${users.address}</h1>
<h1>功夫：${users.gongfu}</h1>
</body>
</html>