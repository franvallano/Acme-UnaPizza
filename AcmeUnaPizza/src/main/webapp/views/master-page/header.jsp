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

<nav class="navbar navbar-inverse navbar-fixed">
	<div class="container" >
		<div class="navbar-header">
		<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
			<span class="sr-only">Toggle navigation</span>
		    <span class="icon-bar"></span>
		    <span class="icon-bar"></span>
		    <span class="icon-bar"></span>
		</button>
		</div>
		<div id="navbar" class="collapse navbar-collapse">
			<ul id="jMenu" class="nav navbar-nav">
				<!-- Do not forget the "fNiv" class for the first level links !! -->
				<li><a class="dropdown-toggle" href="welcome/index.do"><spring:message code="master.page.index" /></a></li>
				<security:authorize access="hasRole('ADMINISTRATOR')">
					<li><a class="dropdown-toggle" data-toggle="dropdown" href=""><spring:message	code="master.page.administrator" /><span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li class="arrow"></li>
							<li><a href="garage/administrator/list.do"><spring:message code="master.page.administrator.garage" /></a></li>
							<li><a href="motorbike/administrator/list.do"><spring:message code="master.page.administrator.motorbike" /></a></li>		
							<li><a href="provider/administrator/list.do"><spring:message code="master.page.administrator.provider" /></a></li>		
						</ul> 
					</li>
					
					<li><a class="dropdown-toggle" data-toggle="dropdown" href=""><spring:message	code="master.page.dashboard" /><span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li class="arrow"></li>
							<li><a href="dashboard/administrator/list.do"><spring:message code="master.page.dashboard" /></a></li>
							<li><a href="dashboard/administrator/ordersSuggestion.do"><spring:message code="master.page.dashboard.ordersSuggestion" /></a></li>
						</ul>
					</li>
					
					<li><a class="dropdown-toggle" data-toggle="dropdown" href=""><spring:message	code="master.page.complaints" /><span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li class="arrow"></li>
							<li><a href="complaint/administrator/listAvailables.do"><spring:message code="master.page.administrator.complaint.listAvailables" /></a></li>
							<li><a href="complaint/actor/list.do"><spring:message code="master.page.administrator.complaint.listAll" /></a></li>
						</ul> 
					</li>
					
					<li><a class="dropdown-toggle" data-toggle="dropdown" href=""><spring:message	code="master.page.administrator.usersManagement" /> <span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li class="arrow"></li>
							<li><a href="user/administrator/listAdministrators.do"><spring:message code="master.page.administrator.administratorManagement" /></a></li>
							<li><a href="user/administrator/listBosses.do"><spring:message code="master.page.administrator.bossManagement" /></a></li>
							<li><a href="user/administrator/listDeliveryMen.do"><spring:message code="master.page.administrator.deliveryManManagement" /></a></li>
							<li><a href="user/administrator/listCooks.do"><spring:message code="master.page.administrator.cookManagement" /></a></li>
							<li><a href="user/administrator/listCustomers.do"><spring:message code="master.page.administrator.customerManagement" /></a></li>
							</ul>
					</li>
					
					<li><a class="dropdown-toggle" data-toggle="dropdown" href=""><spring:message	code="master.page.administrator.products" /><span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li class="arrow"></li>
							<li><a href="product/administrator/listPizzas.do"><spring:message code="master.page.administrator.productsPizzas" /></a></li>
							<li><a href="product/administrator/listComplements.do"><spring:message code="master.page.administrator.productsComplements" /></a></li>
							<li><a href="product/administrator/listDesserts.do"><spring:message code="master.page.administrator.productsDesserts" /></a></li>
							<li><a href="product/administrator/listDrinks.do"><spring:message code="master.page.administrator.productsDrinks" /></a></li>
							<li><a href="product/administrator/listAll.do"><spring:message code="master.page.administrator.productsAll" /></a></li>
							<li><a href="product/administrator/create.do?productType=PIZZA"><spring:message code="master.page.administrator.newProduct" /></a></li>
						</ul>
					</li>
					
					
					<li><a class="dropdown-toggle" data-toggle="dropdown" href=""><spring:message	code="master.page.administrator.offers" /> <span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li class="arrow"></li>
							<li><a href="offer/administrator/list.do"><spring:message code="master.page.administrator.offers.list" /></a></li>
							<li><a href="offer/administrator/listCurrentOffers.do"><spring:message code="master.page.administrator.offers.listCurrentOffers" /></a></li>
						</ul>
					</li>
					
					<li><a class="dropdown-toggle" data-toggle="dropdown" href=""><spring:message	code="master.page.administrator.purchaseOrders" /><span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li class="arrow"></li>
							<li><a href="purchaseOrder/administrator/list.do"><spring:message code="master.page.administrator.purchaseOrders.list" /></a></li>
							<li><a href="purchaseOrder/administrator/create.do"><spring:message code="master.page.administrator.purchaseOrders.new" /></a></li>
						</ul>
					</li>
					
				</security:authorize>
				
				<security:authorize access="hasRole('CUSTOMER')">
					<!-- <li><a class="dropdown-toggle"><spring:message	code="master.page.customer" /></a></li> -->
					<li><a class="dropdown-toggle" href="dashboard/customer/list.do"><spring:message code="master.page.dashboard" /></a></li>
					<li><a class="dropdown-toggle" data-toggle="dropdown" href=""><spring:message	code="master.page.complaints" /><span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="complaint/actor/list.do"><spring:message code="master.page.customer.viewComplaint"/></a></li>
						</ul>
					</li>
					<li><a class="dropdown-toggle" data-toggle="dropdown" href=""><spring:message	code="master.page.salesOrders" /><span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="salesOrder/customer/list.do"><spring:message code="master.page.salesOrders.list" /></a></li>
							<li><a href="salesOrder/customer/create.do"><spring:message code="master.page.salesOrders.create" /></a></li>
						</ul>
					</li>
					
				</security:authorize>
				
				<security:authorize access="hasRole('BOSS')">
					<li><a class="dropdown-toggle" data-toggle="dropdown" href=""><spring:message	code="master.page.boss" /><span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="repair/boss/list.do"><spring:message code="master.page.boss.repair" /></a></li>
							<li><a href="workshop/boss/list.do"><spring:message code="master.page.boss.workshop" /></a></li>
							<li><a href="stuff/boss/list.do"><spring:message code="master.page.boss.stuff" /></a></li>
							<li><a href="stuff/boss/list.do?status=OK"><spring:message code="master.page.boss.stuff.ok" /></a></li>
							<li><a href="stuff/boss/list.do?status=REPAIRING"><spring:message code="master.page.boss.stuff.repairing" /></a></li>
							<li><a href="stuff/boss/list.do?status=MALFUNCTION"><spring:message code="master.page.boss.stuff.malfunction" /></a></li>
						</ul>
					</li>
					<li><a class="dropdown-toggle" href="dashboard/boss/list.do"><spring:message code="master.page.dashboard" /></a></li>
					<li><a class="dropdown-toggle" data-toggle="dropdown" href=""><spring:message	code="master.page.salesOrders" /><span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="salesOrder/boss/listOpened.do"><spring:message code="master.page.salesOrders.listOpened" /></a></li>
							<li><a href="salesOrder/boss/listInProcess.do"><spring:message code="master.page.salesOrders.listInProcess" /></a></li>
							<li><a href="salesOrder/boss/listCompleted.do"><spring:message code="master.page.salesOrders.listCompleted" /></a></li>
							<li><a href="salesOrder/boss/listUndelivered.do"><spring:message code="master.page.salesOrders.listUndelivered" /></a></li>
							<li><a href="salesOrder/boss/listAll.do"><spring:message code="master.page.salesOrders.listAll" /></a></li>
						</ul>
					</li>
				</security:authorize>
				
				<security:authorize access="hasRole('COOK')">
					<li><a class="dropdown-toggle" href="dashboard/cook/list.do"><spring:message code="master.page.dashboard" /></a></li>
					<li><a class="dropdown-toggle" data-toggle="dropdown" href=""><spring:message	code="master.page.salesOrders" /><span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="salesOrder/cook/forCooking.do"><spring:message code="master.page.salesOrders.forCooking" /></a></li>
							<li><a href="salesOrder/cook/forPrepared.do"><spring:message code="master.page.salesOrders.forPrepared" /></a></li>
						</ul>
					</li>
				</security:authorize>
				
				<security:authorize access="hasRole('DELIVERY_MAN')">
					<li><a class="dropdown-toggle" href="dashboard/deliveryMan/list.do"><spring:message code="master.page.dashboard" /></a></li>
					<li><a class="dropdown-toggle" data-toggle="dropdown" href=""><spring:message	code="master.page.salesOrders" /><span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="salesOrder/deliveryMan/listOnItsWay.do"><spring:message code="master.page.salesOrders.onItsWay" /></a></li>
							<li><a href="salesOrder/deliveryMan/finish.do"><spring:message code="master.page.salesOrders.finish" /></a></li>
						</ul>
					</li>
				</security:authorize>
				
				<security:authorize access="isAnonymous()">
					<li><a class="dropdown-toggle" href="security/login.do"><spring:message code="master.page.login" /></a></li>
					<li><a class="dropdown-toggle" data-toggle="dropdown" href=""><spring:message code="master.page.register" /><span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="register/customer/register.do"><spring:message code="master.page.registerCustomer" /></a></li>
					</ul>
					</li>
				</security:authorize>
				
				<security:authorize access="isAuthenticated()">
					<li>
						<a class="dropdown-toggle" data-toggle="dropdown" href=""> 
							<spring:message code="master.page.profile" /> 
					        (<security:authentication property="principal.username" />)<span class="caret"></span>
						</a>
						<ul class="dropdown-menu">
							<li class="arrow"></li>			
								<security:authorize access="hasRole('CUSTOMER')">
									<li><a href="profile/customer/edit.do"><spring:message code="master.page.viewProfile" /></a></li>
									<li><a href="profile/customer/changePassword.do"><spring:message code="master.page.changePassword" /></a></li>
								</security:authorize>
								<security:authorize access="hasRole('ADMINISTRATOR')">
									<li><a href="profile/administrator/edit.do"><spring:message code="master.page.viewProfile" /></a></li>
									<li><a href="profile/administrator/changePassword.do"><spring:message code="master.page.changePassword" /></a></li>
								</security:authorize>
								<security:authorize access="hasRole('BOSS')">
									<li><a href="profile/staff/editBoss.do"><spring:message code="master.page.viewProfile" /></a></li>
									<li><a href="profile/staff/changePassword.do"><spring:message code="master.page.changePassword" /></a></li>
								</security:authorize>
								<security:authorize access="hasRole('COOK')">
									<li><a href="profile/staff/editCook.do"><spring:message code="master.page.viewProfile" /></a></li>
									<li><a href="profile/staff/changePassword.do"><spring:message code="master.page.changePassword" /></a></li>
								</security:authorize>
								<security:authorize access="hasRole('DELIVERY_MAN')">
									<li><a href="profile/staff/editDeliveryMan.do"><spring:message code="master.page.viewProfile" /></a></li>
									<li><a href="profile/staff/changePassword.do"><spring:message code="master.page.changePassword" /></a></li>
								</security:authorize>
							<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
						</ul>
					</li>
				</security:authorize>
			</ul>
		</div>	
	</div>
</nav>


<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>



