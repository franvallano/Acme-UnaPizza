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

<display:table name="complaints" pagesize="10" class="displaytag" requestURI="${requestURI}" id="complaintRow">
	
	<spring:message code="complaint.state" var="stateHeader" />
	<display:column property="state" title="${stateHeader}" />
	
	<spring:message code="complaint.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" />
	
	<spring:message code="complaint.creationMoment" var="creationMomentHeader" />
	<display:column property="creationMoment" title="${creationMomentHeader}" sortable="true" format="{0,date,dd/MM/yyyy HH:mm}"/>
	
	<spring:message code="complaint.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}" />
	
	<jstl:if test="${requestURI == 'complaint/actor/list.do'}">
		<spring:message code="complaint.result" var="resultHeader" />
		<display:column property="result" title="${resultHeader}" />
	</jstl:if>
	
	<display:column>
		<a href="complaint/actor/details.do?complaintId=${complaintRow.id}">
			<spring:message code="complaint.view" />
		</a>
	</display:column>

	<security:authorize access="hasRole('ADMINISTRATOR')">
	
			<display:column>
				<jstl:if test="${row.state == 'open'}">
						<a href="complaint/administrator/modifyStateCancelled.do?complaintId=${row.id}">
							<spring:message code="complaint.cancel" />
						</a>
						</jstl:if>
						
			</display:column>
			
			<jstl:if test="${requestURI == 'complaint/actor/list.do'}">
				<display:column>
					<jstl:if test="${row.state ne 'closed' && row.state ne 'cancelled'}">
						<a href="complaint/administrator/addResolution.do?complaintId=${row.id}">
							<spring:message code="complaint.addResolution" />
						</a>
					</jstl:if>
				</display:column>
			</jstl:if>
		
	</security:authorize>

</display:table>

<div>
	<security:authorize access="hasRole('CUSTOMER')">
		<a href="complaint/customer/create.do">
			<spring:message	code="complaint.create" />
		</a>
	</security:authorize>
</div>