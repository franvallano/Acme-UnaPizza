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

<display:table name="salesOrders" pagesize="10" class="displaytag" requestURI="${requestURI}" id="salesOrdersRow">

	<security:authorize access="hasRole('CUSTOMER')">
	
			<spring:message code="salesOrder.referenceNumber" var="referenceNumberHeader" />
			<display:column property="referenceNumber" title="${referenceNumberHeader}" sortable="true"/>
			
			<spring:message code="salesOrder.totalCost" var="totalCostHeader" />
			<display:column property="totalCost" title="${totalCostHeader}" sortable="true"/>
			
			<spring:message code="salesOrder.creationMoment" var="creationMomentHeader" />
			<display:column property="creationMoment" title="${creationMomentHeader}" sortable="true" format="{0,date,dd/MM/yyyy HH:mm}" />
			
			<spring:message code="salesOrder.totalCost" var="totalCostHeader" />
			<display:column property="totalCost" title="${totalCostHeader}" sortable="true"/>
			
			<display:column>
					<a href="salesOrder/customer/details.do?salesOrderId=${salesOrdersRow.id}">
						<spring:message code="salesOrder.details" />
					</a>
			</display:column>
		
		</security:authorize>
		
	</display:table>

