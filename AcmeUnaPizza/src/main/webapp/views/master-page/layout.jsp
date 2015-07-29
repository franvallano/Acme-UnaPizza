<%--
 * layout.jsp
 *
 * Copyright (C) 2014 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<base
	href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/" />

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<link rel="shortcut icon" href="favicon.ico"/> 

<script type="text/javascript" src="scripts/jquery.js"></script>
<script type="text/javascript" src="scripts/jquery-ui.js"></script>
<script type="text/javascript" src="scripts/jmenu.js"></script>

<link rel="stylesheet" href="styles/bootstrap.min.css" type="text/css">
<link rel="stylesheet" href="styles/bootstrap.min.css" media="screen"
	type="text/css" />
<link rel="stylesheet" href="styles/displaytag.css" type="text/css">

<title><tiles:insertAttribute name="title" ignore="true" /></title>

<script type="text/javascript">
	$(document).ready(function() {
		$("#jMenu").jMenu();
	});

	function askSubmission(msg, form) {
		if (confirm(msg))
			form.submit();
	}
</script>

<!-- SCRIPT CONTROL DE COOKIES -->
<script type="text/javascript">
function controlcookies() {
         // si variable no existe se crea (al clicar en Aceptar)
    localStorage.controlcookie = (localStorage.controlcookie || 0);
 
    localStorage.controlcookie++; // incrementamos cuenta de la cookie
    cookies_warning.style.display='none'; // Esconde la pol�tica de cookies
	}
</script>

</head>

<body>
	<div class="container case-showcase">

	<div>
		<tiles:insertAttribute name="header" />
	</div>
	<div>
		<h1>
			<tiles:insertAttribute name="title" />
		</h1>
		<tiles:insertAttribute name="body" />	
		<jstl:if test="${message != null}">
			<br />
			<span class="message"><spring:message code="${message}" /></span>
		</jstl:if>	
	</div>
	<div>
		<tiles:insertAttribute name="footer" />
	</div>
	
	<div id="cookies_warning" class="alert alert-info">
		<spring:message code="master.page.cookies" /> 
		<a id="cookie_link" href="law/info.do" class="btn btn-xs btn-primary">
		<spring:message code="master.page.cookies.link" /></a>
		<button class="btn btn-primary" onclick="controlcookies()">Aceptar</button>
	</div>
	<script type="text/javascript">
	if (!localStorage.controlcookie>0){ 
	cookies_warning.setAttribute("style","animation: desaparecer 5s;-webkit-animation: desaparecer 5s;");
	}
	</script>
	
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="scripts/bootstrap.min.js"></script>
</body>
</html>