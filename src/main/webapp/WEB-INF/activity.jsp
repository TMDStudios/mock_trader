<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>

</head>
<body>
<c:forEach var="action" items="${actions}">
	<p><c:out value="${action}"></c:out></p>
</c:forEach>
</body>
</html>