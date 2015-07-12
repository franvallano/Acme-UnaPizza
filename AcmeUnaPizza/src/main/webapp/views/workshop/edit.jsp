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

		<jstl:if test="${details == true}">
			<fieldset>
				<acme:labelDetails code="workshop.company" value="${workshop.company}"/>
				<acme:labelDetails code="workshop.phoneNumber" value="${workshop.phoneNumber}"/>
				<acme:labelDetails code="workshop.taxes" value="${workshop.taxes}" eurCurrency="true"/>
				<acme:labelDetails code="workshop.city" value="${workshop.city}"/>
				<acme:labelDetails code="workshop.contact" value="${workshop.contact}"/>
				<br/>
				<fieldset>
					<legend><h3><spring:message code="workshop.repairs" /></h3></legend>
					<jstl:forEach var="repair" items="${workshop.repairs}" varStatus="rowIndex">
						<acme:labelDetails code="workshop.repair.cost" value="${repair.cost}" eurCurrency="true" />
						<acme:dateLabelDetails code="workshop.repair.moment" value="${repair.moment}" />
						<br/>
					</jstl:forEach>
				</fieldset>
			</fieldset>
			
			<br />
			<input type="button" name="cancel" value="<spring:message code="cancel" />" 
					onclick="javascript: window.location.replace('workshop/boss/list.do');" />
		</jstl:if>
		
		<jstl:if test="${edit == true}">
			<form:form action="${requestURI}" modelAttribute="workShop">

					<form:hidden path="id" />
					<form:hidden path="version" />
					<form:hidden path="repairs" />
				
					<acme:textbox code="workshop.company" path="company"/>
					<br />
					<acme:textbox code="workshop.city" path="city"/>
					<br />
					<acme:textbox code="workshop.taxes" path="taxes"/>
					<br />
					<acme:textbox code="workshop.phoneNumber" path="phoneNumber"/>
					<br />
					<acme:textbox code="workshop.contact" path="contact"/>
					<br />
			
				<br />
				
				<acme:submit name="save" code="workshop.save"/>&nbsp;
				<input type="button" name="cancel" value="<spring:message code="cancel" />" 
					onclick="javascript: window.location.replace('workshop/boss/list.do');" />
			</form:form>
		</jstl:if>
	
		
	</security:authorize>