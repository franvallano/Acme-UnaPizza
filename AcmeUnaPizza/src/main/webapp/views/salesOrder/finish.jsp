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

	
	<security:authorize access="hasRole('DELIVERY_MAN')">
		<jstl:if test="${delivered == true}">
			<form:form action="${requestURI}" modelAttribute="${drivingForm}" >
				<form:hidden path="salesOrderId" />
				<acme:textbox code="salesOrder.drivingTime" path="drivingTime"/>
				<br/>
				
				<acme:submit name="save" code="salesOrder.save"/>
				<input type="button" name="profile.back" value="<spring:message code="cancel" />" 
					onclick="javascript: window.history.back();" />
			</form:form>
		</jstl:if>
	
		<jstl:if test="${undelivered == true}">
			<form:form action="${url}" modelAttribute="${noteDrivingForm}" >
			<form:hidden path="salesOrderId" />
			
			<acme:textbox code="salesOrder.drivingTime" path="drivingTime"/>
			<br/>
			<fieldset>
				<legend><h3><spring:message code="salesOrder.note" /></h3></legend>
				<spring:message code="salesOrder.noteCause" />
					<form:select path="cause">
					<form:options items="${causes}" />
				</form:select>
				<br/><br/>
				<acme:textarea code="salesOrder.noteDescription" path="description"/>
			</fieldset>
			<br/>
			<acme:submit name="save" code="salesOrder.save"/>
			<input type="button" name="profile.back" value="<spring:message code="cancel" />" 
				onclick="javascript: window.history.back();" />
			</form:form>
			
			
		</jstl:if>
			
		
		
	</security:authorize>