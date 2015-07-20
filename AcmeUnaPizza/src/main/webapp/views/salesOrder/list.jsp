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
		
		<security:authorize access="hasAnyRole('BOSS', 'COOK', 'DELIVERY_MAN')">
	
			<spring:message code="salesOrder.referenceNumber" var="referenceNumberHeader" />
			<display:column property="referenceNumber" title="${referenceNumberHeader}" sortable="true"/>
			
			<spring:message code="salesOrder.creationMoment" var="creationMomentHeader" />
			<display:column property="creationMoment" title="${creationMomentHeader}" sortable="true" format="{0,date,dd/MM/yyyy HH:mm}" />
			
			<spring:message code="salesOrder.totalCost" var="totalCostHeader" />
			<display:column property="totalCost" title="${totalCostHeader}" sortable="true"/>
			
			<spring:message code="salesOrder.state" var="stateHeader" />
			<display:column property="state" title="${stateHeader}" sortable="true"/>
			
			<security:authorize access="hasRole('BOSS')">
			
				<display:column>
					<a href="salesOrder/boss/details.do?salesOrderId=${salesOrdersRow.id}">
						<spring:message code="salesOrder.details" />
					</a>
				</display:column>
				
				<jstl:if test="${opened == true}">
					<display:column>
						<jstl:if test="${salesOrdersRow.boss == null}">
							<a href="salesOrder/boss/assignBoss.do?salesOrderId=${salesOrdersRow.id}" onclick="return confirm('<spring:message code="salesOrder.confirm.assignBoss" />')">
							<spring:message code="salesOrder.assignBoss" /></a>
						</jstl:if>
					</display:column>
					
					<display:column>
						<a href="salesOrder/boss/cancel.do?salesOrderId=${salesOrdersRow.id}" onclick="return confirm('<spring:message code="salesOrder.confirm.cancel" />')">
							<spring:message code="salesOrder.cancel" />
						</a>
					</display:column>
				</jstl:if>
				
				<jstl:if test="${inProcess == true}">
					<display:column>
						<jstl:if test="${salesOrdersRow.state != 'ONITSWAY'}">
							<a href="salesOrder/boss/cancel.do?salesOrderId=${salesOrdersRow.id}" onclick="return confirm('<spring:message code="salesOrder.confirm.cancel" />')">
							<spring:message code="salesOrder.cancel" /></a>
						</jstl:if>
					</display:column>
				</jstl:if>
				<jstl:if test="${note == true}">
					<display:column>
						<a href="note/boss/note.do?salesOrderId=${salesOrdersRow.id}">
						<spring:message code="salesOrder.note" /></a>
					</display:column>
				</jstl:if>
			</security:authorize>
			
			<security:authorize access="hasRole('COOK')">
				<jstl:if test="${assign == true}">
					<display:column>
						<a href="salesOrder/cook/assignAndCooking.do?salesOrderId=${salesOrdersRow.id}" onclick="return confirm('<spring:message code="salesOrder.confirm.assignCook" />')">
							<spring:message code="salesOrder.assignAndCooking" /></a>
					</display:column>
				</jstl:if>
				<jstl:if test="${prepared == true}">
					<display:column>
						<a href="salesOrder/cook/prepared.do?salesOrderId=${salesOrdersRow.id}" onclick="return confirm('<spring:message code="salesOrder.confirm.prepared" />')">
							<spring:message code="salesOrder.prepared" /></a>
					</display:column>
				</jstl:if>
			</security:authorize>
			
			<security:authorize access="hasRole('DELIVERY_MAN')">
				<jstl:if test="${finish == true}">
					<display:column>
						<a href="salesOrder/deliveryMan/toFinish.do?salesOrderId=${salesOrdersRow.id}" onclick="return confirm('<spring:message code="salesOrder.confirm.toFinish" />')">
							<spring:message code="salesOrder.toFinish" /></a>
					</display:column>
					<display:column>
						<a href="salesOrder/deliveryMan/notFinish.do?salesOrderId=${salesOrdersRow.id}" onclick="return confirm('<spring:message code="salesOrder.confirm.notFinish" />')">
							<spring:message code="salesOrder.notFinish" /></a>
					</display:column>
				</jstl:if>
				
				<jstl:if test="${onitsway == true}">
					<display:column>
						<a href="salesOrder/deliveryMan/onItsWay.do?salesOrderId=${salesOrdersRow.id}" onclick="return confirm('<spring:message code="salesOrder.confirm.toDeliver" />')">
							<spring:message code="salesOrder.toDeliver" /></a>
					</display:column>
				</jstl:if>
				
			</security:authorize>
		
		</security:authorize>
		
	</display:table>

