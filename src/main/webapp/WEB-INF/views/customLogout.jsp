<%--
  Created by IntelliJ IDEA.
  User: kim-yina
  Date: 2021/06/01
  Time: 10:34 오전
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html"; charset="UTF-8">
  <title>Title</title>
</head>
<body>

  <h1>CUSTOM LOGOUT PAGE</h1>

  <form action="/customLogout" method="post">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <button>로그아웃</button>
  </form>



</body>
</html>
