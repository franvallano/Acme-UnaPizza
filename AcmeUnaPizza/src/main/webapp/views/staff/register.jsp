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


<form:form action="${action}" modelAttribute="registrationAdministratorForm">
	
	<acme:textbox code="administrator.username" path="username"/>

	<acme:password code="administrator.password" path="password"/>

	<acme:textbox code="administrator.name" path="name"/>

	<acme:textbox code="administrator.surname" path="surname"/>

	<acme:textbox code="administrator.email" path="email"/>
	
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
	
	<acme:submit name="save" code="administrator.save"/>&nbsp; &nbsp; &nbsp; &nbsp;

	<input type="button" name="cancel" class="btn btn-primary" onclick="javascript: window.location.replace('/AcmeUnaPizza/')" value="<spring:message code='cancel' />" />
	<br />
	
</form:form>