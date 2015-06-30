<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

	
	<security:authorize access="hasRole('ADMINISTRATOR')">
		<fieldset>
			<acme:labelDetails code="staff.surname" value="${staff.surname}"/>
			<acme:labelDetails code="staff.name" value="${staff.name}"/>
			<acme:labelDetails code="staff.surname" value="${staff.surname}"/>
			<acme:labelDetails code="staff.email" value="${staff.email}"/>
			<acme:labelDetails code="staff.name" value="${staff.name}"/>
			<acme:labelDetails code="staff.ssNumber" value="${staff.ssNumber}"/>
			<acme:dateLabelDetails code="staff.contractStartDate" value="${staff.contractStartDate}" time="false"/>
			<acme:labelDetails code="staff.phone" value="${staff.phone}"/>
			<acme:labelDetails code="staff.address" value="${staff.address}"/>
			<acme:dateLabelDetails code="staff.birthDate" value="${staff.birthDate}" time="false"/>
			<jstl:if test="${isDeliveryMan == true}">
				<acme:labelDetails code="staff.drivingLicenseNumber" value="${staff.drivingLicenseNumber}"/>
			</jstl:if>
		</fieldset>
		
		<br />
		<input type="button" name="back" value="<spring:message code="back" />" 
		onclick="javascript: window.history.back();" />
		
	</security:authorize>
	
