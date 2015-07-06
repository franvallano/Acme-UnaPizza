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



	<security:authorize access="hasAnyRole('ADMINISTRATOR','STAFF')">

		<jstl:if test="${details == true}">
			<fieldset>
				<acme:labelDetails code="workshop.company" value="${workshop.company}"/>
				<acme:labelDetails code="workshop.phoneNumber" value="${workshop.phoneNumber}"/>
				<acme:labelDetails code="workshop.taxes" value="${workshop.taxes}"/>
				<acme:labelDetails code="workshop.city" value="${workshop.city}"/>
				<acme:labelDetails code="workshop.contact" value="${workshop.contact}"/>
				<!-- <acme:labelDetails code="workshop.repairs" value="${workshop.repairs}"/> -->
			</fieldset>
			
			<br />
			<input type="button" name="cancel" value="<spring:message code="cancel" />" 
					onclick="javascript: window.location.replace('workshop/staff/list.do');" />
		</jstl:if>
		
		<jstl:if test="${register == true && edit == true}">
			<form:form action="${requestURI}" modelAttribute="workshop">
			
				<jstl:if test="${!availableGarages.isEmpty()}">
					<form:hidden path="id" />
					<form:hidden path="version" />
				
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
					
							
					<br /><br />
					
					<acme:submit name="save" code="workshop.save"/>
				</jstl:if>
				
				<jstl:if test="${availableGarages.isEmpty()}">
					<spring:message code="NotAvailableGaragesRegister" />
					<br /><br />
				</jstl:if>

				
				<input type="button" name="cancel" value="<spring:message code="cancel" />" 
					onclick="javascript: window.location.replace('workshop/administrator/list.do');" />
			</form:form>
		</jstl:if>
		
		
		<jstl:if test="${edit == true && register == null}">
			<form:form action="${requestURI}" modelAttribute="workshop">

					<form:hidden path="id" />
					<form:hidden path="version" />
				
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
				
				<acme:submit name="update" code="workshop.save"/>&nbsp;
				<input type="submit" name="delete"
						value="<spring:message code="workshop.delete" />"
						onclick="return confirm('<spring:message code="workshop.confirm.delete" />')" />&nbsp;
				<input type="button" name="cancel" value="<spring:message code="cancel" />" 
					onclick="javascript: window.location.replace('workshop/staff/list.do');" />
			</form:form>
		</jstl:if>
	
		
	</security:authorize>