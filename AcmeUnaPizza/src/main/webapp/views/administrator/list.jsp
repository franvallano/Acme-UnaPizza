<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

	<display:table name="administrators" id="administratorsRow" requestURI="${requestURI}"
	pagesize="10" class="table table-striped">
	
		<security:authorize access="hasRole('ADMINISTRATOR')">
		
			<spring:message code="administrator.username" var="usernameHeader" />
			<display:column property="userAccount.username" title="${usernameHeader}" sortable="true" />
			
			<spring:message code="administrator.name" var="nameHeader" />
			<display:column property="name" title="${nameHeader}" sortable="true" />
			
			<spring:message code="administrator.surname" var="surnameHeader" />
			<display:column property="surname" title="${surnameHeader}" sortable="true" />
			
			<spring:message code="administrator.email" var="emailHeader" />
			<display:column property="email" title="${emailHeader}" sortable="true" />
			
			<display:column>
				<a href="user/administrator/detailsAdministrator.do?administratorId=${administratorsRow.id}" ><spring:message code="details"/></a>
			</display:column>
			
		</security:authorize>
		
		
	
	</display:table>
	
	<input type="button" class="btn btn-primary" name="new" value="<spring:message code="new" />" 
		onclick="javascript: window.location.replace('register/administrator/register.do');" />