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
    	<c:if test = "${action.contains('NEW GAME')}">
    		<hr>
       		<p>
       			Welcome trader!
       			<br><br>
       			The goal of this game is to accumulate as much wealth as possible in 100 days. 
       			Each day, you can buy or sell BTC by entering the dollar amount and clicking on the corresponding button.
       			<br>
       			You can also click 'SKIP' to go to the next day. The BTC price will fluctuate based on the news of the day. Buy low and sell high!
       			<br>
       			Will you be the next (mock) Bitcoin Billionaire?
       		</p>
    	</c:if>
    	<c:if test = "${!action.contains('NEW GAME')}">
       		<p><c:out value="${action}"></c:out></p>
    	</c:if>
    </c:if>
</c:forEach>
</body>
</html>