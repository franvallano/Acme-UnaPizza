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

<form:form action="${requestURI}" modelAttribute="entity"
	enctype="multipart/form-data">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<!-- TODO: Añadir el resto de atributos ocultos -->

	<!--
	<acme:textbox code="codigoDeInternacionalizacion" path="nombreAtributo" readonly="true"/>
	<br />
	-->
	
	<!--
	<acme:textarea code="codigoDeInternacionalizacion" path="nombreAtributo"/>
	<br />
	-->
	
	<!--
	<form:label path="nombreAtributo">
		<spring:message code="codigoDeInternacionalizacion"/>
	</form:label>
	<form:select path="nombrePropiedad">
		<form:option value="0">----</form:option>
		<form:option value="CREATED">CREATED</form:option>
		<form:option value="REGISTERED">REGISTERED</form:option>
		<form:option value="SOLVED">SOLVED</form:option>
	</form:select>
	<br />
	-->	
		
	<!-- <jstl:if test="${editable}"> </jstl:if> -->
	<acme:submit name="save" code="details.save" />

	<acme:submit name="delete" code="details.delete" />
	
	
	<acme:cancel url="urlDeListado" code="details.cancel" />

</form:form>
