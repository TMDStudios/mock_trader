<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>

</head>
<body>
<c:forEach var="action" items="${actions}">
	<c:if test = "${action.contains('Insufficient')}">
       <p style="color: red;"><c:out value="${action}"></c:out></p>
    </c:if>
    <c:if test = "${!action.contains('Insufficient')}">
       <p><c:out value="${action}"></c:out></p>
    </c:if>
</c:forEach>
</body>
</html>