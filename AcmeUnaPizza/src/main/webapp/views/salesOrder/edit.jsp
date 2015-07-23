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

<script>
	
	<!-- Esto es para llamar a la funcion cada vez que se cargue la web -->
	$(document).ready(function () {
		resetSalesOrder();
	});
	
	function resetSalesOrder() {
		var totalCost = $("#totalCost");
		sum = 0.0;
		totalCost.val(sum);
	}
	
	function updatePrice(updatePriceNoOffer) {
		var totalCost = $("#totalCost");
		// Con esta variable evitamos que el primer select cuente como un valor
		var jumpSelectOffer = true; 

		sum = 0.0;

		$("select option").each(function(){
			if($(this).is(':checked')) {
				if(!jumpSelectOffer)
					sum += parseFloat($(this).val());
				else
					jumpSelectOffer = false;
			}
		});
		
		totalCost.val(sum);
	}

</script>

	<security:authorize access="hasAnyRole('CUSTOMER, BOSS')">

		<jstl:if test="${details == true}">
			<fieldset>
				<acme:labelDetails code="salesOrder.referenceNumber" value="${salesOrder.referenceNumber}"/>
				<acme:labelDetails code="salesOrder.totalCost" value="${salesOrder.totalCost}" eurCurrency="true"/>
				<acme:dateLabelDetails code="salesOrder.creationMoment" value="${salesOrder.creationMoment}"/>
				
				<security:authorize access="hasRole('BOSS')">
					<acme:labelDetails code="salesOrder.boss" value="${salesOrder.boss.name}"/>
					<acme:labelDetails code="salesOrder.cook" value="${salesOrder.cook.name}"/>
					<acme:labelDetails code="salesOrder.deliveryMan" value="${salesOrder.deliveryMan.name}"/>
				</security:authorize>
				
				<br/>
				<fieldset>
					<legend><h3><spring:message code="salesOrder.offer" /></h3></legend>
						<jstl:choose>
							<jstl:when test="${salesOrder.offer != null}">
								<acme:labelDetails code="salesOrder.offer.name" value="${salesOrder.offer.name}"/>
								<acme:labelDetails code="salesOrder.offer.discount" value="${salesOrder.offer.discount}" percentage="true"/>
								<acme:labelDetails code="salesOrder.offer.range" value="${salesOrder.offer.rangee}"/>
								
							</jstl:when>
							<jstl:when test="${salesOrder.offer == null}">
								<spring:message code="salesOrder.offer.none" />
							</jstl:when>
						</jstl:choose>
				</fieldset>
				<br/>
				<fieldset>
					<legend><h3><spring:message code="salesOrder.products" /></h3></legend>
					<jstl:forEach var="product" items="${salesOrder.products}" varStatus="rowIndex">
						<acme:labelDetails code="product.type" value="${product.type}"/>
						<acme:labelDetails code="product.name" value="${product.name}"/>
						<acme:labelDetails code="product.description" value="${product.description}"/>
						<acme:labelDetails code="product.salePrice" value="${product.salePrice}" eurCurrency="true"/>
						<br/>
					</jstl:forEach>
				</fieldset>
			</fieldset>
			
			<br />
			<security:authorize access="hasRole('BOSS')">
				<input type="button" name="cancel" class="btn btn-primary" value="<spring:message code="cancel" />" 
					onclick="javascript: window.history.back();" />
			</security:authorize>
			<security:authorize access="hasRole('CUSTOMER')">
				<input type="button" name="cancel" class="btn btn-primary" value="<spring:message code="cancel" />" 
						onclick="javascript: window.location.replace('salesOrder/customer/list.do');" />
			</security:authorize>
		</jstl:if>
	</security:authorize>
		
	<security:authorize access="hasRole('CUSTOMER')">
		<jstl:if test="${edit == true}">
			<form:form action="${requestURI}" modelAttribute="salesOrderForm">
				<form:hidden path="idPizzas" />
				<form:hidden path="idComplements" />
				<form:hidden path="idDesserts" />
				<form:hidden path="idDrinks" />
				
				<acme:textbox code="salesOrder.referenceNumber" path="referenceNumber" readonly="true"/>
				<br />
				<acme:textbox code="salesOrder.creationMoment" path="creationMoment" readonly="true" />
				<br />
				<acme:textbox code="salesOrder.totalCost" path="totalCost" readonly="true" id="totalCost"/>
				<br />
				
				<jstl:if test="${!availableOffers.isEmpty()}">
					<fieldset>
						<legend><h3><spring:message code="salesOrder.availableOffers" /></h3></legend>
						<acme:select id="offerApplied" items="${availableOffers}" itemLabel="name" code="salesOrder.offerApplied" path="offer"/>
					</fieldset>
				</jstl:if>

				<br />
				
				<fieldset>
					<legend><h3><spring:message code="salesOrder.pizzas" /></h3></legend>
					<jstl:forEach var="product" items="${pizzas}" varStatus="rowIndex">
						<acme:labelDetails code="product.code" value="${product.code}" noLineBreaks="true"/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<acme:labelDetails code="product.name" value="${product.name}" noLineBreaks="true"/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<acme:labelDetails code="product.stockPrice" value="${product.stockPrice}" eurCurrency="true" noLineBreaks="true"/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<spring:message code="product.amount" />
							<form:select path="amountPizzas" size="2" onclick="updatePrice()" onchange="updatePrice()">
								<form:option label="0" value="0.0" selected="selected"/>
									<jstl:forEach items="${totalAmount}" varStatus="amountIndex">
										<jstl:if test="${amountIndex.index > 0}">
											<form:option label="${amountIndex.index}" value="${product.stockPrice *  amountIndex.index}"/>
										</jstl:if>
									</jstl:forEach>
							</form:select>
						<br /><br />
					</jstl:forEach>
				</fieldset>
				
				<br />
				
				<fieldset>
					<legend><h3><spring:message code="salesOrder.complements" /></h3></legend>
					<jstl:forEach var="product" items="${complements}" varStatus="rowIndex">
						<acme:labelDetails code="product.code" value="${product.code}" noLineBreaks="true"/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<acme:labelDetails code="product.name" value="${product.name}" noLineBreaks="true"/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<acme:labelDetails code="product.stockPrice" value="${product.stockPrice}" eurCurrency="true" noLineBreaks="true"/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<spring:message code="product.amount" />
							<form:select path="amountComplements" size="2" onclick="updatePrice()" onchange="updatePrice()">
								<form:option label="0" value="0.0" selected="selected"/>
									<jstl:forEach items="${totalAmount}" varStatus="amountIndex">
										<jstl:if test="${amountIndex.index > 0}">
											<form:option label="${amountIndex.index}" value="${product.stockPrice *  amountIndex.index}"/>
										</jstl:if>
									</jstl:forEach>
							</form:select>
						<br /><br />
					</jstl:forEach>
				</fieldset>
				
				<br />
				
				<fieldset>
					<legend><h3><spring:message code="salesOrder.desserts" /></h3></legend>
					<jstl:forEach var="product" items="${desserts}" varStatus="rowIndex">
						<acme:labelDetails code="product.code" value="${product.code}" noLineBreaks="true"/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<acme:labelDetails code="product.name" value="${product.name}" noLineBreaks="true"/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<acme:labelDetails code="product.stockPrice" value="${product.stockPrice}" eurCurrency="true" noLineBreaks="true"/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<spring:message code="product.amount" />
							<form:select path="amountDesserts" size="2" onclick="updatePrice()" onchange="updatePrice()">
								<form:option label="0" value="0.0" selected="selected"/>
									<jstl:forEach items="${totalAmount}" varStatus="amountIndex">
										<jstl:if test="${amountIndex.index > 0}">
											<form:option label="${amountIndex.index}" value="${product.stockPrice *  amountIndex.index}"/>
										</jstl:if>
									</jstl:forEach>
							</form:select>
						<br /><br />
					</jstl:forEach>
				</fieldset>
				
				<br />
				
				<fieldset>
					<legend><h3><spring:message code="salesOrder.drinks" /></h3></legend>
					<jstl:forEach var="product" items="${drinks}" varStatus="rowIndex">
						<acme:labelDetails code="product.code" value="${product.code}" noLineBreaks="true"/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<acme:labelDetails code="product.name" value="${product.name}" noLineBreaks="true"/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<acme:labelDetails code="product.stockPrice" value="${product.stockPrice}" eurCurrency="true" noLineBreaks="true"/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<spring:message code="product.amount" />
							<form:select path="amountDrinks" size="2" onclick="updatePrice()" onchange="updatePrice()">
								<form:option label="0" value="0.0" selected="selected"/>
									<jstl:forEach items="${totalAmount}" varStatus="amountIndex">
										<jstl:if test="${amountIndex.index > 0}">
											<form:option label="${amountIndex.index}" value="${product.stockPrice *  amountIndex.index}"/>
										</jstl:if>
									</jstl:forEach>
							</form:select>
						<br /><br />
					</jstl:forEach>
				</fieldset>
				<br />
				
				<acme:submit name="save" code="salesOrder.save"/>
				
				<input type="button" name="cancel" class="btn btn-primary" value="<spring:message code="cancel" />" 
					onclick="javascript: window.location.replace('salesOrder/customer/list.do');" />
			</form:form>
		</jstl:if>
		
	</security:authorize>