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

<display:table name="complaints" pagesize="10" class="table table-striped" requestURI="${requestURI}" id="complaintRow">
	
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
	
	<security:authorize access="hasRole('CUSTOMER')">
		<display:column>
			<a href="complaint/actor/details.do?complaintId=${complaintRow.id}">
				<spring:message code="complaint.view" />
			</a>
		</display:column>
	</security:authorize>
	
	<security:authorize access="hasRole('ADMINISTRATOR')">
		<display:column>
		<a href="complaint/administrator/details.do?complaintId=${complaintRow.id}">
			<spring:message code="complaint.view" />
		</a>
		</display:column>
			<jstl:if test="${requestURI == 'complaint/administrator/listAvailables.do'}">
				<display:column>
					<a href="complaint/administrator/assign.do?complaintId=${complaintRow.id}" onclick="return confirm('<spring:message code="complaint.alert" />')">
						<spring:message code="complaint.assign" />
					</a>
				</display:column>
			</jstl:if>
			<jstl:if test="${requestURI == 'complaint/actor/list.do'}">
				<display:column>
					<jstl:if test="${complaintRow.state != 'CLOSED' && complaintRow.state != 'CANCELLED'}">
						<a href="complaint/administrator/addResolution.do?complaintId=${complaintRow.id}">
							<spring:message code="complaint.addResolution" />
						</a>
					</jstl:if>
				</display:column>
			</jstl:if>
		
	</security:authorize>

</display:table>

<div>
	<security:authorize access="hasRole('CUSTOMER')">
		<input type="button" class="btn btn-primary" name="new" value="<spring:message code="new" />" 
			onclick="javascript: window.location.replace('complaint/customer/create.do');" />
	</security:authorize>
</div>