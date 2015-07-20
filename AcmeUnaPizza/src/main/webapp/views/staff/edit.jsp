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
				<acme:labelDetails code="staff.username" value="${staff.userAccount.username}"/>
				<acme:labelDetails code="staff.name" value="${staff.name}"/>
				<acme:labelDetails code="staff.surname" value="${staff.surname}"/>
				<acme:labelDetails code="staff.email" value="${staff.email}"/>
				<acme:labelDetails code="staff.dni" value="${staff.dni}"/>
				<acme:labelDetails code="staff.ssNumber" value="${staff.ssNumber}"/>
				<acme:dateLabelDetails code="staff.contractStartDate" value="${staff.contractStartDate}" time="false"/>
				<acme:dateLabelDetails code="staff.contractEndDate" value="${staff.contractEndDate}" time="false"/>
				<acme:labelDetails code="staff.phone" value="${staff.phone}"/>
				<acme:labelDetails code="staff.address" value="${staff.address}"/>
				<acme:dateLabelDetails code="staff.birthDate" value="${staff.birthDate}" time="false"/>
				<jstl:if test="${staffType == 'deliveryMan'}">
					<acme:labelDetails code="staff.drivingLicenseNumber" value="${staff.drivingLicenseNumber}"/>
				</jstl:if>
			</fieldset>
		</jstl:if>
	</security:authorize>
	
		
	<br />
	<input type="button" name="cancel" value="<spring:message code="cancel" />" 
		onclick="javascript: window.history.back();" />
	<br />