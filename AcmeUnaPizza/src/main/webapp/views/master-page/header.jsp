<%--
 * header.jsp
 *
 * Copyright (C) 2014 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<div>
	<img src="images/logo.png" alt="AcmeUnaPizza Co., Inc." />
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="hasRole('ADMINISTRATOR')">
			<li><a class="fNiv"><spring:message	code="master.page.administrator" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="complaint/administrator/listAvailables.do"><spring:message code="master.page.administrator.complaint" /></a></li>
					<li><a href="garage/administrator/list.do"><spring:message code="master.page.administrator.garage" /></a></li>
					<li><a href="motorbike/administrator/list.do"><spring:message code="master.page.administrator.motorbike" /></a></li>					
				</ul> 
			</li>
			<li><a class="fNiv" href="dashboard/administrator/list.do"><spring:message code="master.page.dashboard" /></a></li>
			<li><a class="fNiv"><spring:message	code="master.page.administrator.usersManagement" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="users/administrator/listAdministrators.do"><spring:message code="master.page.administrator.administratorManagement" /></a></li>
					<li><a href="users/administrator/listBosses.do"><spring:message code="master.page.administrator.bossManagement" /></a></li>
					<li><a href="users/administrator/listDeliveryMen.do"><spring:message code="master.page.administrator.deliveryManManagement" /></a></li>
					<li><a href="users/administrator/listCooks.do"><spring:message code="master.page.administrator.cookManagement" /></a></li>
					<li><a href="users/administrator/listCustomers.do"><spring:message code="master.page.administrator.customerManagement" /></a></li>
					</ul>
			</li>
			<li><a class="fNiv" href="dashboard/administrator/ordersSuggestion.do"><spring:message code="master.page.dashboard.ordersSuggestion" /></a></li>
		</security:authorize>
		
		<security:authorize access="hasRole('CUSTOMER')">
			<li><a class="fNiv"><spring:message	code="master.page.customer" /></a>
				<ul>
					<li><a href="complaint/actor/list.do"><spring:message code="master.page.complaint" /></a></li>
				</ul>
			</li>
			<li><a class="fNiv" href="dashboard/customer/list.do"><spring:message code="master.page.dashboard" /></a>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('BOSS')">
			<li><a class="fNiv"><spring:message	code="master.page.boss" /></a>
			</li>
			<li><a class="fNiv" href="dashboard/boss/list.do"><spring:message code="master.page.dashboard" /></a></li>
		</security:authorize>
		
		<security:authorize access="hasRole('COOK')">
			<li><a class="fNiv"><spring:message	code="master.page.cook" /></a>
				
			</li>
			<li><a class="fNiv" href="dashboard/cook/list.do"><spring:message code="master.page.dashboard" /></a></li>
		</security:authorize>
		
		<security:authorize access="hasRole('DELIVERY_MAN')">
			<li><a class="fNiv"><spring:message	code="master.page.deliveryMan" /></a>
			</li>
			<li><a class="fNiv" href="dashboard/deliveryMan/list.do"><spring:message code="master.page.dashboard" /></a></li>
		</security:authorize>
		
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
			<li><a class="fNiv"><spring:message code="master.page.register" /></a>
			<ul>
				<li><a href="register/customer/register.do"><spring:message code="master.page.registerCustomer" /></a></li>
			</ul>
		</security:authorize>
		
		<security:authorize access="isAuthenticated()">
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.profile" /> 
			        (<security:authentication property="principal.username" />)
				</a>
				<ul>
					<li class="arrow"></li>				
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
				</ul>
			</li>
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

