<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="/css/style.css">
<title>Mock Trader</title>
</head>
<body>
<h1 class="center-text">Mock Trader</h1>
<p class="center-text">This is an open-source Bitcoin trading game created by TMD Studios.</p>
<p class="center-text"><small>The premise of this game is loosely based on real events. This is by no means financial advice.</small></p>
<table>
	<tr>
	    <th colspan=2>Day <c:out value="${day}"/></th>
	</tr>
	<tr>
	    <th colspan=2>BTC Price: $<c:out value="${btcPrice}"/></th>
	</tr>
	<tr>
	  <th>Your Wealth</th>
	  <th>Latest News</th>
	</tr>
	<tr>
	  <td>Money: $<c:out value="${money}"/></td>
	  <td rowspan=2>Source: <c:out value="${currentNews.source}"/><br><c:out value="${currentNews.news}"/></td>
	</tr>
	<tr>
		<td>BTC: <c:out value="${btc}"/></td>
	</tr>
</table>
<hr>
<table>

  <tr>
    <td>
	    <form action="/buy" method="post">
	    	<span><input type="number" name="amount" placeholder="Amount"/><input class="button" type="submit" value="Buy"/></span>
	    </form>
    </td>
    <td><form action="/skip" method="post"><input class="button-skip" type="submit" value="Skip"/></form></td>
    <td>
	    <form action="/sell" method="post">
	    	<span><input type="number" name="amount" placeholder="Amount"/><input class="button" type="submit" value="Sell"/></span>
	    </form>
    </td>
  </tr>
</table>
<hr>
<iframe src="/activity/" title="Bookmarks Iframe"></iframe>
<br>
<br>
<div class="center-item">
<a href="https://play.google.com/store/apps/details?id=com.tmdstudios.cryptoledgerkotlin">
<img src="https://tmdstudios.files.wordpress.com/2021/11/clbanner-1.png?w=480"/>
</a>
</div>
</body>
</html>