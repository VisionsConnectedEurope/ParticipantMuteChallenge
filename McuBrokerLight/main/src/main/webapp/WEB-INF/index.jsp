<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
    <head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="refresh" content="12600" />
		<meta name="apple-mobile-web-app-capable" content="yes" />
		<meta http-equiv="Cache-control" content="private" />

		<title><fmt:message key="app.title"/></title>

		<link rel="shortcut icon" href="${pageContext.request.contextPath}/favicon.ico" type="image/x-icon"/>
		<link rel="icon" type="image/ico" href="${pageContext.request.contextPath}/favicon.ico"/>
		<link rel="icon" type="image/vnd.microsoft.icon" href="${pageContext.request.contextPath}/favicon.ico"/>

		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/style.css" />
    </head>
	<body class="width1024">
		<div id="wrapper">
			<div id="header-body">
				<div id="header">
					<div id="logo"><a href="${pageContext.request.contextPath}/"><img src="${pageContext.request.contextPath}/graphics/logo.png" /></a></div>
					<div id="app-tab"><p><fmt:message key="app.title"/><sup><fmt:message key="version.sub"/></sup></p></div>
				</div>
				<div id="menubar-body">
					<div id="menubar-body-overlay">
						<div id="menubar"></div>
					</div>
				</div>
			</div>
			<div id="main">
				<div id="content-start">&nbsp;</div>
				<div id="content">
					<h1>The <fmt:message key="app.title"/></h1>
					<p>
						Welcome to the <fmt:message key="app.title"/>!<br /><br />
						The <fmt:message key="app.title"/> has no usable user interface.
						If you want to interact with the <fmt:message key="app.title"/> use the API.<br />

						<a href="${pageContext.request.contextPath}/swagger-ui.html">${pageContext.request.contextPath}/swagger-ui.html</a>
					</p>
					<div class="clear">&nbsp;</div>
				</div>
				<div id="content-end">&nbsp;</div>
			</div>
		</div>
		<div id="footer">
			<div class="space">
				<fmt:message key="companyName"/> <fmt:message key="app.title"/> <fmt:message key="version.nr"/> - &copy; <fmt:message key="year.firstVersion"/> - <fmt:message key="year.currentVersion"/> - <fmt:message key="companyName"/>
			</div>
		</div>
	</body>
</html>