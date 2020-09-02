<%--
  Created by IntelliJ IDEA.
  User: 62358
  Date: 2020/8/28
  Time: 17:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
String contextPath = request.getContextPath();
%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<!DOCTYPE html>
<html>
<body>

<form action="<%=contextPath%>/FileProcessServlet.do" method="post" enctype="multipart/form-data">
    First name:<br>
    <input type="text" name="firstname" value="Mickey">
    <br>
    Last name:<br>
    <input type="text" name="lastname" value="Mouse">
    <br><br>
    <input type="file" name="myFile">
    <br><br>
    <input type="submit" value="Submit">
</form>

<p>如果您点击提交，表单数据会被发送到名为 demo_form.asp 的页面。</p>

</body>
</html>
</body>
</html>
