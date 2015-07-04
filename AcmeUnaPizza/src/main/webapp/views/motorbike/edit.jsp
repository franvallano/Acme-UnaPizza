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
				<acme:labelDetails code="motorbike.number" value="${motorbike.number}"/>
				<acme:labelDetails code="motorbike.drivingTime" value="${motorbike.drivingTime}"/>
				<acme:labelDetails code="motorbike.licensePlate" value="${motorbike.licensePlate}"/>
				<acme:labelDetails code="motorbike.garage" value="${motorbike.garage.location}"/>
				<acme:labelDetails code="motorbike.deliveryMan" value="${deliveryMan}"/>
			</fieldset>
			
			<br />
			<input type="button" name="cancel" value="<spring:message code="cancel" />" 
					onclick="javascript: window.location.replace('motorbike/administrator/list.do');" />
		</jstl:if>
		
		<jstl:if test="${register == true && edit == true}">
			<form:form action="${requestURI}" modelAttribute="motorbike">
			
				<jstl:if test="${!availableGarages.isEmpty()}">
					<form:hidden path="id" />
					<form:hidden path="version" />
					<form:hidden path="drivingTime" />
				
					<acme:textbox code="motorbike.number" path="number"/>
					<br />
					<acme:textbox code="motorbike.licensePlate" path="licensePlate"/>
					<br />
					
					<spring:message code="availableGarages" />
					<form:select path="garage">
						<form:options items="${availableGarages}" itemLabel="location" itemValue="id"/>
					</form:select>
					
					<br /><br />
					
					<acme:submit name="save" code="motorbike.save"/>
				</jstl:if>
				
				<jstl:if test="${availableGarages.isEmpty()}">
					<spring:message code="NotAvailableGaragesRegister" />
					<br /><br />
				</jstl:if>

				
				<input type="button" name="cancel" value="<spring:message code="cancel" />" 
					onclick="javascript: window.location.replace('motorbike/administrator/list.do');" />
			</form:form>
		</jstl:if>
		
		
		<jstl:if test="${edit == true && register == null}">
			<form:form action="${requestURI}" modelAttribute="motorbike">

				<form:hidden path="id" />
				<form:hidden path="version" />
				<form:hidden path="garage" />
			
				<acme:textbox code="motorbike.number" path="number"/>
				<br />
				<acme:textbox code="motorbike.drivingTime" path="drivingTime" readonly="true"/>
				<br />
				<acme:textbox code="motorbike.licensePlate" path="licensePlate"/>
				<br />
				
				<br />
				
				<acme:submit name="update" code="motorbike.save"/>&nbsp;
				<input type="submit" name="delete"
						value="<spring:message code="motorbike.delete" />"
						onclick="return confirm('<spring:message code="motorbike.confirm.delete" />')" />&nbsp;
				<input type="button" name="cancel" value="<spring:message code="cancel" />" 
					onclick="javascript: window.location.replace('motorbike/administrator/list.do');" />
			</form:form>
		</jstl:if>
		
		<jstl:if test="${changeGarage == true}">
			
			<jstl:if test="${!availableGarages.isEmpty()}">
				<form:form action="${requestChangeGarageURI}" modelAttribute="motorbike">
					<form:hidden path="licensePlate" />
					<form:hidden path="drivingTime" />
					<form:hidden path="number" />
					<form:hidden path="id" />
					<form:hidden path="version" />
				
					<fieldset>
						<acme:labelDetails code="motorbike.number" value="${motorbike.number}"/>
						<acme:labelDetails code="motorbike.garage" value="${motorbike.garage.location}"/>
					</fieldset>
					<br />
						
						<fieldset>
							<legend><h3><spring:message code="availableGarages" /></h3></legend>
								
								<form:select path="garage">
									<form:options items="${availableGarages}" itemLabel="location" itemValue="id"/>
								</form:select>
										
								<br />
									
							</fieldset>
							<br />
					<acme:submit name="save" code="motorbike.save" />
					<input type="button" name="cancel" value="<spring:message code="cancel" />" 
						onclick="javascript: window.location.replace('motorbike/administrator/list.do');" />
					
				</form:form>
			</jstl:if>
			
			<jstl:if test="${availableGarages.isEmpty()}">
				<spring:message code="NotAvailableGaragesEdit" />
				<br /><br />
				<input type="button" name="cancel" value="<spring:message code="cancel" />" 
						onclick="javascript: window.location.replace('motorbike/administrator/list.do');" />
			</jstl:if>
			
			
						
					
		</jstl:if>
		
		
	</security:authorize>