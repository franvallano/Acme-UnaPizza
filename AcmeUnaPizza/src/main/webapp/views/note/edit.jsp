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



	<security:authorize access="hasRole('BOSS')">
		<fieldset>
			<acme:labelDetails code="note.cause" value="${note.cause}"/>
			<acme:labelDetails code="note.description" value="${note.description}"/>
			
		</fieldset>
		<br/>
		<input type="button" name="cancel" class="btn btn-primary" value="<spring:message code="cancel" />" 
			onclick="javascript: window.location.replace('salesOrder/boss/listUndelivered.do');" />
		
	</security:authorize>
	