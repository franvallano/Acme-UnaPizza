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

<display:table name="garages" pagesize="5" class="displaytag" requestURI="${requestURI}" id="garagesRow">
	
	<security:authorize access="hasRole('ADMINISTRATOR')">
	
		<spring:message code="garage.location" var="locationHeader" />
		<display:column property="location" title="${locationHeader}" />
		
		<spring:message code="garage.size" var="sizeHeader" />
		<display:column property="size" title="${sizeHeader}" />
		
		<display:column>
				<a href="garage/administrator/details.do?garageId=${garagesRow.id}">
					<spring:message code="garage.details" />
				</a>
		</display:column>
		
		<display:column>
				<a href="garage/administrator/edit.do?garageId=${garagesRow.id}">
					<spring:message code="garage.edit" />
				</a>
		</display:column>
		
	</security:authorize>
		
</display:table>

	<input type="button" name="new" value="<spring:message code="new" />" 
		onclick="javascript: window.location.replace('garage/administrator/create.do');" />
	



