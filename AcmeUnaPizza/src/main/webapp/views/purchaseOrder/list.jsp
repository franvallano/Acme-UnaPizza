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

<display:table name="purchaseOrders" pagesize="10" class="displaytag" requestURI="${requestURI}" id="purchaseOrdersRow">

	<security:authorize access="hasRole('ADMINISTRATOR')">
	
			<spring:message code="purchaseOrder.referenceNumber" var="referenceNumberHeader" />
			<display:column property="referenceNumber" title="${referenceNumberHeader}" sortable="true"/>
			
			<spring:message code="purchaseOrder.totalCost" var="totalCostHeader" />
			<display:column property="totalCost" title="${totalCostHeader}" sortable="true"/>
			
			<spring:message code="purchaseOrder.creationMoment" var="creationMomentHeader" />
			<display:column property="creationMoment" title="${creationMomentHeader}" sortable="true" format="{0,date,dd/MM/yyyy HH:mm}" />
			
			<display:column>
					<a href="purchaseOrder/administrator/details.do?purchaseOrderId=${purchaseOrdersRow.id}">
						<spring:message code="purchaseOrder.details" />
					</a>
			</display:column>
		
		</security:authorize>
		
	</display:table>

