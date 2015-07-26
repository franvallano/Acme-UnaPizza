<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<!-- 
	Atributos obligatorios del modelo:
		String requestURI 			-- URI a la que hacer la petición para recargar la lista cuando sea necesario
		Collection<Object> entities	-- Lista de entidades que debemos listar
 -->
 
<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="entities" requestURI="${requestURI}" id="row">

	<spring:message code="stuff.name" var="nameHeader" />
	<display:column property="name"
		title="${nameHeader}" sortable="true" />
	
	<spring:message code="stuff.refCode" var="referenceCodeHeader" />
	<display:column property="referenceCode"
		title="${referenceCodeHeader}" sortable="true" />
		
	<spring:message code="stuff.powConsumption" var="powerConsumptionHeader" />
	<display:column property="powerConsumption"
		title="${powerConsumptionHeader}" sortable="true" />	
	
	<spring:message code="stuff.status" var="statusHeader" />
	<display:column property="status"
		title="${statusHeader}" sortable="true" />
		
	<spring:message code="details.details" var="detailsHeader" />
	<display:column title="${detailsHeader}" sortable="true">
		<a href="URLedicion Id=${row.id}"> <spring:message
			code="details.details" />
		</a>
	</display:column>
	
</display:table>

<security:authorize access="hasRole('BOSS')">
	<a href="/stuff/boss/create.do">
		<spring:message code="details.createNew" />
	</a>
</security:authorize>
