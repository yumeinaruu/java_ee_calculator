<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>History page</title>
</head>
<body style="background-color: aliceblue">
<table border="1" style=" margin-left: auto; text-align: center; margin-right: auto">
  <tr>
    <th>Equation</th>
    <th>Result</th>
  </tr>
  <tbody>
  <c:forEach var="line" items="${history}">
    <c:set var="parts" value="${fn:split(line, '|')}"/>
    <tr>
      <c:forEach var="part" items="${parts}">
        <td>${part}</td>
      </c:forEach>
    </tr>
  </c:forEach>
  </tbody>
</table>
<br>
<br>
<form align="center" action="/"> <%--action="/" возвращает на стартовую страницу потому что /--%>
  <input type="submit" value="Home page">
</form>
</body>
</html>
