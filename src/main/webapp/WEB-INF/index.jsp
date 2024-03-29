<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<meta
  name="description"
  content="Mock Trader is an open-source Bitcoin trading game. The player starts with $10,000 and is tasked with accumulating as much wealth as possible in 100 turns (days)."
/>
<meta name="robots" content="index,follow" />
<meta property="og:image" content="https://tmdstudios.files.wordpress.com/2022/03/mocktraderthumb.png" />
<meta property="og:title" content="Mock Trader">
<meta name="description" property="og:description" content="Mock Trader is an open-source Bitcoin trading game. The player starts with $10,000 and is tasked with accumulating as much wealth as possible in 100 turns (days).">
<meta name="twitter:card" value="summary">
<link rel="stylesheet" type="text/css" href="/css/style.css">
<title>Mock Trader</title>
</head>
<body>
<div class="center"><img class="center-img" src="https://tmdstudios.files.wordpress.com/2022/03/mocktrader.png"/></div>
<p class="center-text">This is an open-source Bitcoin trading game created by <a href="https://tmdstudios.wordpress.com">TMD Studios</a>.</p>
<p class="center-text"><small>The code is available on <a href="https://github.com/TMDStudios/mock_trader">GitHub</a>.</small></p>
<p class="center-text"><small>The premise of this game is loosely based on real events. This is by no means financial advice.</small></p>
<table>
	<tr>
	    <th colspan=2>Day <c:out value="${day}"/></th>
	</tr>
	<tr>
		<c:if test="${trend>0}">
			<th colspan=2><div>BTC Price: $<fmt:formatNumber pattern="0.00" value="${btcPrice}"/></div>
			<div style="color: green;">+<fmt:formatNumber pattern="0.00" value="${trend}"/>%</div></th>
		</c:if>
		<c:if test="${trend<0}">
			<th colspan=2><div>BTC Price: $<fmt:formatNumber pattern="0.00" value="${btcPrice}"/></div>
			<div style="color: red;"><fmt:formatNumber pattern="0.00" value="${trend}"/>%</div></th>
		</c:if>
		<c:if test="${trend==0}">
			<th colspan=2><div>BTC Price: $<fmt:formatNumber pattern="0.00" value="${btcPrice}"/></div>
			<div><fmt:formatNumber pattern="0.00" value="${trend}"/>%</div></th>
		</c:if>
	</tr>
	<tr>
	  <th>Your Wealth</th>
	  <th>Latest News</th>
	</tr>
	<tr>
	  <td>Money: $<fmt:formatNumber pattern="#.00" value="${money}"/></td>
	  	<c:if test="${day!=0}">
			<td rowspan=3>
				<p style="font-size: large;">Source: <c:out value="${currentNews.source}"/></p>
				<p><c:out value="${currentNews.news}"/></p>
			</td>
		</c:if>
		<c:if test="${day==0}">
			<td rowspan=3></td>
		</c:if> 
	</tr>
	<tr>
		<td>BTC: <fmt:formatNumber pattern="0.00000000" value="${btc}"/></td>
	</tr>
	<tr>
		<th>Total Wealth: $<fmt:formatNumber pattern="0.00" value="${total}"/></th>
	</tr>
</table>
<hr>
<table>

  <tr>
  	<c:if test="${day<100}">
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
	</c:if> 
    <c:if test="${day==100}">
		<td colspan=3>
			<p class="center-text" style="font-size: x-large">Game Over</p>
			<p style="font-size: large">Your total wealth is $<fmt:formatNumber pattern="0.00" value="${total}"/></p>
			<p style="font-size: large"><a href="/play_again/">Play Again</a></p>
		</td>
	</c:if> 
  </tr>
</table>
<hr>
<iframe src="/activity/" title="Bookmarks Iframe"></iframe>
<p class="center-text"><small><a href="/play_again/">Reset</a></small></p>
<br>
<div class="center-item">
<a href="${link}">
<img style="padding: 8px" src="${banner}"/>
</a>
</div>
</body>
</html>