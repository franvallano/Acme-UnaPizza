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
				<acme:labelDetails code="offer.range" value="${offer.rangee}"/>
				<acme:labelDetails code="offer.loop" value="${offer.loopp}"/>
			</fieldset>
			
			<br />
			<input type="button" name="cancel" class="btn btn-primary" value="<spring:message code="cancel" />" 
				onclick="javascript: window.location.replace('offer/administrator/list.do');" />
		</jstl:if>
		
		<jstl:if test="${register == true && edit == true}">
			<form:form action="${requestURI}" modelAttribute="offerForm">
				<form:hidden path="id"/>
				
				<acme:textbox code="offer.name" path="name"/>
				<br />
				<acme:textbox code="offer.discount" path="discount"/>
				<br />
				<acme:textbox code="offer.startDate" path="startDate"/>
				<br />
				<acme:textbox code="offer.endDate" path="endDate"/>
				<br />
				
				<spring:message code="offer.loop" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				
				<spring:message code="check.monday" />
				<form:checkbox id="checkMonday" path="monday"/> 
				<spring:message code="check.tuesday" />
				<form:checkbox path="tuesday"/> 
				<spring:message code="check.wednesday" />
				<form:checkbox path="wednesday"/> 
				<spring:message code="check.thursday" />
				<form:checkbox path="thursday"/> 
				<spring:message code="check.friday" />
				<form:checkbox path="friday"/> 
				<spring:message code="check.saturday" />
				<form:checkbox path="saturday"/> 
				<spring:message code="check.sunday" />
				<form:checkbox path="sunday"/> 
				<br /><br />
				
				<spring:message code="offer.range" />
				<form:select path="rangee">
					<form:options items="${ranges}" />
				</form:select>
				<br /><br />
					
				<acme:submit name="save" code="offer.save"/>
				
				<input type="button" name="cancel" class="btn btn-primary" value="<spring:message code="cancel" />" 
				onclick="javascript: window.location.replace('offer/administrator/list.do');" />
			</form:form>
		</jstl:if>
		
		<jstl:if test="${edit == true && register == null}">
			<form:form action="${requestURI}" modelAttribute="offerForm">
				<form:hidden path="id"/>
			
				<acme:textbox code="offer.name" path="name"/>
				<br />
				<acme:textbox code="offer.discount" path="discount"/>
				<br />
				<acme:textbox code="offer.startDate" path="startDate"/>
				<br />
				<acme:textbox code="offer.endDate" path="endDate"/>
				<br />
				
				<spring:message code="offer.loop" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				
				<spring:message code="check.monday" />
				<form:checkbox id="checkMonday" path="monday"/> 
				<spring:message code="check.tuesday" />
				<form:checkbox path="tuesday"/> 
				<spring:message code="check.wednesday" />
				<form:checkbox path="wednesday"/> 
				<spring:message code="check.thursday" />
				<form:checkbox path="thursday"/> 
				<spring:message code="check.friday" />
				<form:checkbox path="friday"/> 
				<spring:message code="check.saturday" />
				<form:checkbox path="saturday"/> 
				<spring:message code="check.sunday" />
				<form:checkbox path="sunday"/> 
				<br /><br />
				
				<spring:message code="offer.range" />
				<form:select path="rangee">
					<form:options items="${ranges}" />
				</form:select>
				<br /><br />
					
				<acme:submit name="update" code="offer.save" />
				
				<input type="button" name="cancel" class="btn btn-primary" value="<spring:message code="cancel" />" 
				onclick="javascript: window.location.replace('offer/administrator/list.do');" />
			</form:form>
		</jstl:if>
		
	</security:authorize>