<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>



	<security:authorize access="hasRole('ADMINISTRATOR')">

		<jstl:if test="${details == true}">
			<fieldset>
				<acme:labelDetails code="garage.location" value="${garage.location}"/>
				<acme:labelDetails code="garage.size" value="${garage.size}"/>
				<acme:labelDetails code="totalMotorbikes" value="${totalMotorbikesByGarage}"/>
			</fieldset>
		</jstl:if>
		
		<jstl:if test="${edit == true}">
			<form:form action="${requestURI}" modelAttribute="garage">

				<form:hidden path="id" />
				<form:hidden path="version" />
				<form:hidden path="motorbikes" />
			
				<acme:textbox code="garage.location" path="location"/>
				<br />
				<acme:textbox code="garage.size" path="size"/>
				<br />
				<acme:labelDetails code="totalMotorbikes" value="${totalMotorbikesByGarage}"/>
				
				<br />
				
				<acme:submit name="save" code="garage.save"/>
			</form:form>
			
		</jstl:if>
	</security:authorize>
	
	<br />
	<input type="button" name="cancel" value="<spring:message code="cancel" />" 
		onclick="javascript: window.location.replace('garage/administrator/list.do');" />
	<br />