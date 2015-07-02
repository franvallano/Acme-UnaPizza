<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<fieldset>
	<legend><spring:message code="terms.title" /></legend>
		<p>	
			<spring:message code="terms.a" />
			<spring:message code="terms.b" />
			<spring:message code="terms.c" />
			<spring:message code="terms.d" />
			<spring:message code="terms.e" />
			<spring:message code="terms.f" />
	
		</p>
</fieldset>