<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

	<!-- 
	Atributos obligatorios del modelo:
	- String requestURI -- URI a la que se hacen los POST del formulario para guardar/borrar entidades
	- Object Entity		-- Entidad con la que se va a trabajar en el formulario
	-->

<form:form action="${requestURI}" modelAttribute="entity">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="repairs" />

	<acme:textbox code="stuff.name" path="name"/>
	<br />
	
	<acme:textbox code="stuff.refCode" path="referenceCode"/>
	<br />
	
	<acme:textbox code="stuff.powConsumption" path="powerConsumption"/>
	<br />
	
	<form:label path="status" class =" col-lg-2 control-label">
		<spring:message code="stuff.status"/>
	</form:label>
	<form:select path="status" class="col-lg-4">
		<form:option value="OK"><spring:message code="stuff.status.OK"/></form:option>
		<form:option value="MALFUNCTION"><spring:message code="stuff.status.MALFUNCTION"/></form:option>
		<form:option value="REPAIRING"><spring:message code="stuff.status.REPAIRING"/></form:option>
	</form:select>
	<br />
	<br />
	<form:label path="workShop" class =" col-lg-2 control-label">
		<spring:message code="stuff.workshop"/>
	</form:label>
	 <form:select path="workShop" class="col-lg-4">
	 	<jstl:forEach var="var" items="${workshops}">
	 		<form:option label="${var.company}" value="${var.id}"/>
	 	</jstl:forEach>
	</form:select>
	<br />
	<br />
		
	<acme:submit name="save" code="details.save" />

	<acme:submit name="delete" code="details.delete" />
	
	<input type="button" name="cancel" class="btn btn-primary" value="<spring:message code="details.cancel" />" 
					onclick="javascript: window.location.replace('stuff/boss/list.do');" />

</form:form>
