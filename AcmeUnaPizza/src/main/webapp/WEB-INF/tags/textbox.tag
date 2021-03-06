<%--
 * textbox.tag
 *
 * Copyright (C) 2014 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@ tag language="java" body-content="empty" %>

<%-- Taglibs --%>

<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<%-- Attributes --%> 
 
<%@ attribute name="path" required="true" %>
<%@ attribute name="code" required="true" %>

<%@ attribute name="readonly" required="false" %>
<%@ attribute name="id" required="false" %>

<jstl:if test="${readonly == null}">
	<jstl:set var="readonly" value="false" />
</jstl:if>

<%-- Definition --%>

<div>
	<div class="form-group">
	<form:label path="${path}" class =" col-lg-2 control-label">
		<spring:message code="${code}" />:
	</form:label>	
	<jstl:choose>
		<jstl:when test="${id == null}">
			<div class="col-lg-4">
			<form:input path="${path}" readonly="${readonly}" class="form-control"/>	
			</div>
		</jstl:when>
		<jstl:when test="${id != null}">
			<div class="col-lg-4">
			<form:input path="${path}" readonly="${readonly}" id="${id}" class="form-control"/>		
			</div>
		</jstl:when>
	</jstl:choose>
	
	<form:errors path="${path}" class="form-group has-error alert-danger" />
	<br/>
</div>	
</div>
