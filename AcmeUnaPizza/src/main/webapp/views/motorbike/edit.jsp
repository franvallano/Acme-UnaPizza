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
		
		
		<jstl:if test="${edit == true}">
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
				
				<acme:submit name="save" code="motorbike.save"/>
				<input type="button" name="cancel" value="<spring:message code="cancel" />" 
					onclick="javascript: window.location.replace('motorbike/administrator/list.do');" />
			</form:form>
		</jstl:if>
		
		<jstl:if test="${changeGarage == true}">
			
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
		
		
	</security:authorize>