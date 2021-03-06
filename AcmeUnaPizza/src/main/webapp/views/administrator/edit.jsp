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


	<security:authorize access="hasRole('ADMINISTRATOR')">

		<jstl:if test="${details == true}">
			<fieldset>
				<acme:labelDetails code="administrator.username" value="${administrator.userAccount.username}"/>
				<acme:labelDetails code="administrator.name" value="${administrator.name}"/>
				<acme:labelDetails code="administrator.surname" value="${administrator.surname}"/>
				<acme:labelDetails code="administrator.email" value="${administrator.email}"/>
			</fieldset>
		</jstl:if>
		
	</security:authorize>
	
	
	<br />
	<input type="button" name="cancel" class="btn btn-primary" value="<spring:message code="cancel" />" 
		onclick="javascript: window.history.back();" />
	<br />