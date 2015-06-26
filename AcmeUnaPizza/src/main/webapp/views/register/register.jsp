<%--
 * action-2.jsp
 *
 * Copyright (C) 2013 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<script>	

	<!-- Esto es para llamar a la funcion cada vez que se cargue la web -->
	$(document).ready(function () {
		checkCreditCard();
	});
	
	function checkCreditCard(){
		var checked = $("#checkBoxCreditCard");
		if(checked.is(':checked')) {
			$("#enableCreditCard").attr("disabled", false);
			$("#enableCreditCard").attr("enabled", true);
		} else {
			$("#enableCreditCard").attr("disabled", true);
		}
	}
		
</script>

<form:form action="${url}" modelAttribute="${userForm}" >
	
	<acme:textbox code="register.username" path="username"/>
	<br/>
	
	<acme:password code="register.password" path="password"/>
	<br/>
	
	<acme:password code="register.rpassword" path="repeatedPass"/>
	<jstl:if test="${duplicate == false}">
		<font color="red">
			<b><spring:message code="register.duplicate" /></b>
		</font>
	</jstl:if>
	<br/>
	
	<acme:textbox code="register.name" path="name"/>
	<br/>
	
	<acme:textbox code="register.surname" path="surname"/>
	<br/>
	
	<acme:textbox code="register.email" path="email"/>
	<br/>
	
	<acme:textbox code="register.phone" path="phone"/>
	<br/>
	
	<acme:textbox code="register.address" path="address"/>
	<br/>
	
	<acme:textbox code="register.birthDate" path="birthDate"/>
	<br/>
	
	<spring:message code="useCreditCard" />

	<jstl:choose>
		<jstl:when test="${checkBoxCreditCard == null || checkBoxCreditCard == true}">
			<form:checkbox id="checkBoxCreditCard" path="checkBoxCreditCard" onclick="checkCreditCard()"/> 
			
		</jstl:when>
		<jstl:when test="${checkBoxCreditCard == false}">
			<form:checkbox id="checkBoxCreditCard" path="checkBoxCreditCard" onclick="checkCreditCard()"/> 
			
		</jstl:when>
	</jstl:choose>
	
	

	<br/>
	
	<div class="showCreditCard">
		<fieldset id="enableCreditCard">
		<legend><h3><spring:message code="creditCard" /></h3></legend>
			<acme:textbox code="register.holdername" path="creditCard.holderName"/>
			<br/>
			
			<acme:textbox code="register.brandname" path="creditCard.brandName"/>
			<br/>
			
			<acme:textbox code="register.number" path="creditCard.number"/>
			<br/>
			
			<acme:textbox code="register.expirationmonth" path="creditCard.expirationMonth"/>
			<br/>
			
			<acme:textbox code="register.expirationyear" path="creditCard.expirationYear"/>
			<br/>
			
			<acme:textbox code="register.cvvcode" path="creditCard.CVV"/>
			<br/>
			
		</fieldset>
	</div>
	<br/>
	<fieldset>
			<div>
			<h3><b><spring:message code="conditions" /></b></h3>
			<spring:message code="conditionsText" />
			<br/>
			
			<h3><b><spring:message code="delete" /></b></h3>
			<spring:message code="deleteText" />
			<br/>
			
		</div>
	</fieldset>
	
			
	<spring:message code="check" />
	<form:checkbox path="agree"/> 
	<jstl:if test="${agree == false}">
		<font color="red">
			<b><spring:message code="register.disagree" /></b>
		</font>
	</jstl:if><br /><br />
	
	<acme:submit name="save" code="register.save" />&nbsp; &nbsp; &nbsp; &nbsp; 
	
	<input type="button" name="cancel" value="<spring:message code="cancel" />" 
		onclick="javascript: window.history.back();" />
	
</form:form>

<br /><br />