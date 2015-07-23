<%--
 * label.tag
 *
 * Copyright (C) 2014 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@ tag language="java" body-content="empty"%>

<%-- Taglibs --%>

<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<%-- Attributes --%>

<%@ attribute name="code" required="true"%>
<%@ attribute name="value" required="true"%>
<%@ attribute name="eurCurrency" required="false"%>
<%@ attribute name="timeMinutes" required="false"%>
<%@ attribute name="percentage" required="false"%>
<%@ attribute name="noLineBreaks" required="false"%>

<%-- Definition --%>

<b><spring:message code="${code}"/>:</b>
<jstl:out value="${value}" /> 

<jstl:if test="${percentage == true}">
	<jstl:out value="%" /> 
</jstl:if>

<jstl:if test="${eurCurrency == true}">
	<jstl:out value="E" /> 
</jstl:if>

<jstl:if test="${timeMinutes == true}">
	<jstl:out value=" min" /> 
</jstl:if>

<jstl:if test="${noLineBreaks == null}">
	<br/>
</jstl:if>



