<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Result</title>
</head>
<body style="background-color: aliceblue">
<h1 style="text-align: center">Your result: </h1>
<hr>
<br>
<h2 style="text-align: center"> ${result} </h2>
<br>
<br>
<form align="center" action="/" method="get"> <%--action="/" возвращает на стартовую страницу потому что /--%>
    <input type="submit" value="Home page">
</form>
<br>
<form align="center" action="/history" method="get"> <%--action="/" возвращает на стартовую страницу потому что /--%>
    <input type="submit" value="History page">
</form>
</body>
</html>
