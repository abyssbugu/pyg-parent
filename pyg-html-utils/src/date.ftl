<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>freemarker-时间格式化</title>
</head>
<body>
<h1>显示时间：${today?time}</h1>
<h1>显示日期：${today?date}</h1>
<h1>显示日期时间：${today?datetime}</h1>
<h1>显示时间格式化：${today?string('yyyy年MM月dd日')}</h1>
</body>
</html>