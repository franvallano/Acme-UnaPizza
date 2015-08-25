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
 
<security:authorize access="hasRole('BOSS')">
	<display:table pagesize="5" class="table table-striped" keepStatus="true"
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
			
		<spring:message code="stuff.workshop" var="workshopHeader" />
		<display:column property="workShop.company"
			title="${workshopHeader}" sortable="true" />
		
		<spring:message code="stuff.status" var="statusHeader" />
		<display:column property="status"
			title="${statusHeader}" sortable="true" />
			
		<display:column title="${editHeader}" sortable="true">
			<a href="stuff/boss/edit.do?stuffId=${row.id}"> <spring:message
				code="details.edit" />
			</a>
		</display:column>
		
		<display:column title="${repairHeader}" sortable="true">
			<jstl:if test="${row.status == 'MALFUNCTION'}">
				<a href="repair/boss/create.do?stuffId=${row.id}"> <spring:message
					code="details.repair" />
				</a>
			</jstl:if>
		</display:column>
	</display:table>

	<a class="btn btn-primary" href="stuff/boss/create.do">
		<spring:message code="details.createNew" />
	</a>
</security:authorize>
