<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

	
	<security:authorize access="hasRole('ADMINISTRATOR')">
		<fieldset>
			<acme:labelDetails code="administrator.surname" value="${administrator.surname}"/>
			<acme:labelDetails code="administrator.name" value="${administrator.name}"/>
			<acme:labelDetails code="administrator.surname" value="${administrator.surname}"/>
			<acme:labelDetails code="administrator.email" value="${administrator.email}"/>
		</fieldset>
		
		<br />
		<input type="button" name="back" value="<spring:message code="back" />" 
		onclick="javascript: window.history.back();" />
		
	</security:authorize>
	
