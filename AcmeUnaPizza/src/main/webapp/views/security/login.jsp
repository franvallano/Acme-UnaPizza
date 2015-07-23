 <%--
 * login.jsp
 *
 * Copyright (C) 2014 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="j_spring_security_check" modelAttribute="credentials" role="form">

<div class="panel panel-default">
	<div class="panel-body">
		<div class="input-group">
		<form:label path="username">
			<spring:message code="security.username" />
		</form:label>
		<form:input path="username" class="form-control"/>	
		<form:errors class="alert alert-danger" path="username" />
		</div>
		<br />
		
		
		<div class="input-group">
		<form:label path="password">
			<spring:message code="security.password" />
		</form:label>
		<form:password path="password" class="form-control"/>	
		<form:errors class="alert alert-danger" path="password" />
		</div>
		<br />
		
		<jstl:if test="${showError == true}">
			<div class="alert alert-danger">
				<spring:message code="security.login.failed" />
			</div>
		</jstl:if>
		
		<input type="submit" value="<spring:message code="security.login" />" />
	</div>
</div>	
	
</form:form>