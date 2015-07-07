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



	<security:authorize access="hasRole('ADMINISTRATOR')">

		<jstl:if test="${details == true}">
			<fieldset>
				<acme:labelDetails code="offer.name" value="${offer.name}"/>
				<acme:labelDetails code="offer.discount" value="${offer.discount}" percentage="true"/>
				<acme:dateLabelDetails code="offer.startDate" value="${offer.startDate}"/>
				<acme:dateLabelDetails code="offer.endDate" value="${offer.endDate}"/>
			</fieldset>
			
			<br />
			<input type="button" name="cancel" value="<spring:message code="cancel" />" 
				onclick="javascript: window.history.back();" />
		</jstl:if>
		
		<jstl:if test="${register == true && edit == true}">
			<form:form action="${requestURI}" modelAttribute="offer">
			
				<form:hidden path="id" />
				<form:hidden path="version" />
				
				<acme:textbox code="offer.name" path="name"/>
				<br />
				<acme:textbox code="offer.discount" path="discount"/>
				<br />
				<acme:textbox code="offer.startDate" path="startDate"/>
				<br />
				<acme:textbox code="offer.endDate" path="endDate"/>
				<br />
				
				<spring:message code="offer.range" />
				<form:select path="rangee">
					<form:options items="${ranges}" />
				</form:select>
				<br /><br />
				
				<spring:message code="offer.loop" />
				<form:checkboxes items="${days}" path="loopp" checked="true" />

				<br /><br />
					
				<acme:submit name="save" code="offer.save"/>
				
				<input type="button" name="cancel" value="<spring:message code="cancel" />" 
				onclick="javascript: window.history.back();" />
			</form:form>
		</jstl:if>
		
	</security:authorize>