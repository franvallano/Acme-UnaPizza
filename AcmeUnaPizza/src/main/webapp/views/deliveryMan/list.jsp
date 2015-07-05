<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

	<display:table name="deliveryMen" id="deliveryMenRow" requestURI="${requestURI}"
	pagesize="5" class="displaytag">
	
		<security:authorize access="hasRole('ADMINISTRATOR')">
		
			<spring:message code="deliveryMan.username" var="usernameHeader" />
			<display:column property="userAccount.username" title="${usernameHeader}" sortable="true" />
			
			<spring:message code="deliveryMan.name" var="nameHeader" />
			<display:column property="name" title="${nameHeader}" sortable="true" />
			
			<spring:message code="deliveryMan.surname" var="surnameHeader" />
			<display:column property="surname" title="${surnameHeader}" sortable="true" />
			
			<spring:message code="deliveryMan.email" var="emailHeader" />
			<display:column property="email" title="${emailHeader}" sortable="true" />
			
			<spring:message code="deliveryMan.dni" var="dniHeader" />
			<display:column property="dni" title="${dniHeader}" sortable="true" />
			
			<display:column>
				<a href="user/administrator/staff/detailsStaff.do?staffId=${deliveryMenRow.id}" ><spring:message code="details"/></a>
			</display:column>

		</security:authorize>
	
	</display:table>
	
	<input type="button" name="new" value="<spring:message code="new" />" 
		onclick="javascript: window.location.replace('register/staff/register.do');" />