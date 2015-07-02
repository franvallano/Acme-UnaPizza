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

<display:table name="motorbikes" pagesize="5" class="displaytag" requestURI="${requestURI}" id="row">
	
	<spring:message code="motorbike.number" var="numberHeader" />
	<display:column property="number" title="${numberHeader}" />
	
	<spring:message code="motorbike.drivingTime" var="drivingTimeHeader" />
	<display:column property="drivingTime" title="${drivingTimeHeader}" />
	
	<spring:message code="motorbike.licensePlate" var="licensePlateHeader" />
	<display:column property="licensePlate" title="${licensePlateHeader}" />
	
	<spring:message code="motorbike.garage" var="garageHeader" />
	<display:column property="garage" title="${garageHeader}" />
	
	<display:column>
			<a href="motorbike/administrator/delete.do?motorbikeId=${row.id}">
				<spring:message code="motorbike.delete" />
			</a>
	</display:column>
	
	<display:column>
			<a href="motorbike/administrator/details.do?motorbikeId=${row.id}">
				<spring:message code="motorbike.details" />
			</a>
	</display:column>
	
</display:table>

<security:authorize access="hasRole('ADMINISTRATOR')">
	<div>
		<a href="motorbike/administrator/create.do">
			<spring:message	code="motorbike.create" />
		</a>
	</div>
	
</security:authorize>
