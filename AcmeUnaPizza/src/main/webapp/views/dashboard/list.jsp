<%--
 * list.jsp
 *
 * Copyright (C) 2014 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<security:authorize access="hasRole('ADMINISTRATOR')">
	<form:form action="dashboard/administrator/list.do" >
		
		<fieldset>
			<b><spring:message code="dashboard.administrator.moneyInvestedProducts"/></b>
			<br/>

		</fieldset>
		<br/>
		
		<fieldset>
			<b><spring:message code="dashboard.administrator.customerMoreComplaints"/></b>
			<br/>

		</fieldset>
		<br/>
		
		<fieldset>
			<b><spring:message code="dashboard.administrator.moneyEarned"/></b>
			<br/>

		</fieldset>
		<br/>
		
		<fieldset>
			<b><spring:message code="dashboard.administrator.netMoneyEarned"/></b>
			<br/>

		</fieldset>
		<br/>
		
		<fieldset>
			<b><spring:message code="dashboard.administrator.customerMoreOrders"/></b>
			<br/>

		</fieldset>
		<br/>
		
		<fieldset>
			<b><spring:message code="dashboard.administrator.customerMoreMoneySpent"/></b>
			<br/>

		</fieldset>
		<br/>
		
		<fieldset>
			<b><spring:message code="dashboard.administrator.avgOrders"/></b>
			<br/>

		</fieldset>
		<br/>
		
		<fieldset>
			<b><spring:message code="dashboard.administrator.deliveryManMoreOrders"/></b>
			<br/>

		</fieldset>
		<br/>
		
		<fieldset>
			<b><spring:message code="dashboard.administrator.cookMoreOrders"/></b>
			<br/>

		</fieldset>
		<br/>
		
		<fieldset>
			<b><spring:message code="dashboard.administrator.pizzaMoreSold"/></b>
			<br/>

		</fieldset>
		<br/>
		
		<fieldset>
			<b><spring:message code="dashboard.administrator.pizzaLessSold"/></b>
			<br/>

		</fieldset>
		<br/>
		
		<fieldset>
			<b><spring:message code="dashboard.administrator.drinkMoreSold"/></b>
			<br/>

		</fieldset>
		<br/>
		
		<fieldset>
			<b><spring:message code="dashboard.administrator.drinkLessSold"/></b>
			<br/>

		</fieldset>
		<br/>
		
		<fieldset>
			<b><spring:message code="dashboard.administrator.complementMoreSold"/></b>
			<br/>

		</fieldset>
		<br/>
		
		<fieldset>
			<b><spring:message code="dashboard.administrator.complementLessSold"/></b>
			<br/>

		</fieldset>
		<br/>
		
		<fieldset>
			<b><spring:message code="dashboard.administrator.dessertMoreSold"/></b>
			<br/>

		</fieldset>
		<br/>
		
		<fieldset>
			<b><spring:message code="dashboard.administrator.dessertLessSold"/></b>
			<br/>

		</fieldset>
		<br/>
		
		<fieldset>
			<b><spring:message code="dashboard.administrator.purchasesOrder"/></b>
			<br/>

		</fieldset>
		<br/>
		
		<fieldset>
			<b><spring:message code="dashboard.administrator.moneyUndeliveredOrders"/></b>
			<br/>

		</fieldset>
		<br/>

	</form:form>
</security:authorize>

<security:authorize access="hasRole('CUSTOMER')">
	<form:form action="dashboard/customer/list.do" >
		
		<fieldset>
			<b><spring:message code="dashboard.customer.totalOrders"/></b>
			<br/>

		</fieldset>
		<br/>
		
		<fieldset>
			<b><spring:message code="dashboard.customer.dateLastOrder"/></b>
			<br/>

		</fieldset>
		<br/>

		
	</form:form>
</security:authorize>

<security:authorize access="hasRole('BOSS')">
	<form:form action="dashboard/boss/list.do" >
		
		<fieldset>
			<b><spring:message code="dashboard.boss.totalOrders"/></b>
			<br/>

		</fieldset>
		<br/>
		
		<fieldset>
			<b><spring:message code="dashboard.boss.orderMoreExpensive"/></b>
			<br/>

		</fieldset>
		<br/>
		
		<fieldset>
			<b><spring:message code="dashboard.boss.orderMoreCheap"/></b>
			<br/>

		</fieldset>
		<br/>
		
		<fieldset>
			<b><spring:message code="dashboard.boss.avgOrders"/></b>
			<br/>

		</fieldset>
		<br/>
		
		<fieldset>
			<b><spring:message code="dashboard.boss.orderMoreSlow"/></b>
			<br/>

		</fieldset>
		<br/>
		
		<fieldset>
			<b><spring:message code="dashboard.boss.orderMoreFast"/></b>
			<br/>

		</fieldset>
		<br/>
		
		<fieldset>
			<b><spring:message code="dashboard.boss.drivingTimeByMotorbike"/></b>
			<br/>

		</fieldset>
		<br/>
		
	</form:form>
</security:authorize>

<security:authorize access="hasRole('COOK')">
	<form:form action="dashboard/cook/list.do" >
		
		<fieldset>
			<b><spring:message code="dashboard.cook.totalOrders"/></b>
			<br/>

		</fieldset>
		<br/>
		
		<fieldset>
			<b><spring:message code="dashboard.cook.orderMoreExpensive"/></b>
			<br/>

		</fieldset>
		<br/>
		
		<fieldset>
			<b><spring:message code="dashboard.cook.orderMoreCheap"/></b>
			<br/>

		</fieldset>
		<br/>
		
		<fieldset>
			<b><spring:message code="dashboard.cook.avgOrders"/></b>
			<br/>

		</fieldset>
		<br/>
		
	</form:form>
</security:authorize>

<security:authorize access="hasRole('DELIVERY_MAN')">
	<form:form action="dashboard/deliveryMan/list.do" >
		
		<fieldset>
			<b><spring:message code="dashboard.deliveryMan.totalOrders"/></b>
			<br/>

		</fieldset>
		<br/>
		
		<fieldset>
			<b><spring:message code="dashboard.deliveryMan.orderMoreExpensive"/></b>
			<br/>

		</fieldset>
		<br/>
		
		<fieldset>
			<b><spring:message code="dashboard.deliveryMan.orderMoreCheap"/></b>
			<br/>

		</fieldset>
		<br/>
		
		<fieldset>
			<b><spring:message code="dashboard.deliveryMan.avgOrders"/></b>
			<br/>

		</fieldset>
		<br/>
		
		<fieldset>
			<b><spring:message code="dashboard.deliveryMan.orderMoreSlow"/></b>
			<br/>

		</fieldset>
		<br/>
		
		<fieldset>
			<b><spring:message code="dashboard.deliveryMan.orderMoreFast"/></b>
			<br/>

		</fieldset>
		<br/>
		
		<fieldset>
			<b><spring:message code="dashboard.deliveryMan.drivingTimeByMotorbike"/></b>
			<br/>

		</fieldset>
		<br/>

	</form:form>
</security:authorize>
