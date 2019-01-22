<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>cas入门案例1</title>
</head>
<body>
欢迎进入cas课程1内容，<%= request.getRemoteUser() %><br>
<a href="http://localhost:8087/cas/logout?service=http://www.baidu.com">退出登录</a>
</body>
</html>
