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
		resetPurchaseOrder();
	});
	
	function resetPurchaseOrder() {
		var totalCost = $("#totalCost");
		sum = 0.0;
		totalCost.val(sum);
	}

	function updatePrice() {
		var totalCost = $("#totalCost");
		
		sum = 0.0;

		$("select option").each(function(){
			if($(this).is(':checked')) {
				sum += parseFloat($(this).val());
			}
		});
		
		totalCost.val(sum);
	}

</script>

	<security:authorize access="hasRole('ADMINISTRATOR')">

		<jstl:if test="${details == true}">
			<fieldset>
				<acme:labelDetails code="purchaseOrder.referenceNumber" value="${purchaseOrder.referenceNumber}"/>
				<acme:labelDetails code="purchaseOrder.totalCost" value="${purchaseOrder.totalCost}" eurCurrency="true"/>
				<acme:dateLabelDetails code="purchaseOrder.creationMoment" value="${purchaseOrder.creationMoment}"/>
				<br/>
				<fieldset>
					<legend><h3><spring:message code="purchaseOrder.products" /></h3></legend>
					<jstl:forEach var="product" items="${purchaseOrder.products}" varStatus="rowIndex">
						<acme:labelDetails code="product.type" value="${product.type}"/>
						<acme:labelDetails code="product.code" value="${product.code}"/>
						<acme:labelDetails code="product.name" value="${product.name}"/>
						<acme:labelDetails code="product.description" value="${product.description}"/>
						<acme:labelDetails code="product.stockPrice" value="${product.stockPrice}" eurCurrency="true"/>
						<br/>
					</jstl:forEach>
				</fieldset>
			</fieldset>
			
			<br />
			<input type="button" name="cancel" class="btn btn-primary" value="<spring:message code="cancel" />" 
					onclick="javascript: window.location.replace('purchaseOrder/administrator/list.do');" />
		</jstl:if>
		
		<jstl:if test="${edit == true}">
			<form:form action="${requestURI}" modelAttribute="purchaseOrderForm">
				<form:hidden path="idPizzas" />
				<form:hidden path="idComplements" />
				<form:hidden path="idDesserts" />
				<form:hidden path="idDrinks" />
				
				<acme:textbox code="purchaseOrder.referenceNumber" path="referenceNumber" readonly="true"/>
				<br />
				<acme:textbox code="purchaseOrder.creationMoment" path="creationMoment" readonly="true" />
				<br />
				<acme:textbox code="purchaseOrder.totalCost" path="totalCost" readonly="true" id="totalCost"/>
				<br />
				
				<fieldset>
					<legend><h3><spring:message code="purchaseOrder.pizzas" /></h3></legend>
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
					<legend><h3><spring:message code="purchaseOrder.complements" /></h3></legend>
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
					<legend><h3><spring:message code="purchaseOrder.desserts" /></h3></legend>
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
					<legend><h3><spring:message code="purchaseOrder.drinks" /></h3></legend>
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
				<acme:submit name="save" code="purchaseOrder.save"/>
				
				<input type="button" name="cancel" class="btn btn-primary" value="<spring:message code="cancel" />" 
					onclick="javascript: window.location.replace('purchaseOrder/administrator/list.do');" />
			</form:form>
		</jstl:if>
		
		
		
	</security:authorize>