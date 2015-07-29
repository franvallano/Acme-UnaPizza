<%--
 * select.tag
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
<%@ attribute name="items" required="true" type="java.util.Collection" %>
<%@ attribute name="itemLabel" required="true" %>
<%@ attribute name="itemValue" required="false" %>

<%@ attribute name="id" required="false" %>
<%@ attribute name="onchange" required="false" %>
<%@ attribute name="onclick" required="false" %>
<%@ attribute name="onsubmit" required="false" %>

<jstl:if test="${id == null}">
	<jstl:set var="id" value="${UUID.randomUUID().toString()}" />
</jstl:if>

<jstl:if test="${onchange == null}">
	<jstl:set var="onchange" value="javascript: return true;" />
</jstl:if>

<jstl:if test="${onclick == null}">
	<jstl:set var="onclick" value="javascript: return true;" />
</jstl:if>

<jstl:if test="${onsubmit == null}">
	<jstl:set var="onsubmit" value="javascript: return true;" />
</jstl:if>

<%-- Definition --%>

<div class="form-group">
	<form:label path="${path}" class =" col-lg-2 control-label">
		<spring:message code="${code}" />
	</form:label>	
	<jstl:if test="${itemValue == null}">
		<form:select class="col-lg-4" id="${id}" path="${path}" onchange="${onchange}" onclick="${onclick}" onsubmit="${onsubmit}">
			<form:option value="0" label="----" />		
			<form:options items="${items}" itemValue="id" itemLabel="${itemLabel}" />
		</form:select>
	</jstl:if>
	
	<jstl:if test="${itemValue != null}">
		<form:select id="${id}" path="${path}" class="col-lg-4" onchange="${onchange}" onclick="${onclick}" onsubmit="${onsubmit}">
			<form:option value="0" label="----" />		
			<form:options items="${items}" itemValue="${itemValue}" itemLabel="${itemLabel}" />
		</form:select>
	</jstl:if>
	<form:errors path="${path}" class="alert alert-danger" />
</div>


