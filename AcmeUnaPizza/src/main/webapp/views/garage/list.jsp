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

<display:table name="garages" pagesize="5" class="displaytag" requestURI="${requestURI}" id="row">
	
	<spring:message code="garage.location" var="locationHeader" />
	<display:column property="location" title="${locationHeader}" />
	
	<display:column>
			<a href="garage/administrator/delete.do?garageId=${row.id}">
				<spring:message code="garage.delete" />
			</a>
	</display:column>
	
</display:table>

<security:authorize access="hasRole('ADMINISTRATOR')">
	<div>
		<a href="garage/administrator/create.do">
			<spring:message	code="garage.create" />
		</a>
	</div>
	
</security:authorize>
