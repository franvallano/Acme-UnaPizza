<%--
 * action-2.jsp
 *
 * Copyright (C) 2013 Universidad de Sevilla
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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

	
	<security:authorize access="hasAnyRole('CUSTOMER', 'ADMINISTRATOR', 'BOSS', 'DELIVERY_MAN', 'COOK')">
		<form:form action="${url}" modelAttribute="${passForm}" >
			<acme:password code="profile.actualPassword" path="actualPassword"/>
				<br/>
				<br/>
				<br/>
			<acme:password code="profile.newPassword" path="newPassword"/>
				<br/>
				<br/>
				<br/>
			<acme:password code="profile.repeatNewPassword" path="repeatNewPassword"/>
				<br/>
				<br/>
				<br/><br/><br/>
			<acme:submit name="save" code="profile.save"/>
			<input type="button" name="profile.back" class="btn btn-primary" value="<spring:message code="cancel" />" 
				onclick="javascript: window.history.back();" />
		</form:form>
		
	</security:authorize>