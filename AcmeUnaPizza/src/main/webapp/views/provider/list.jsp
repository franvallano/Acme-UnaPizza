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

<display:table name="providers" pagesize="5" class="table table-striped" requestURI="${requestURI}" id="providersRow">

	<security:authorize access="hasRole('ADMINISTRATOR')">
	
			<spring:message code="provider.name" var="nameHeader" />
			<display:column property="name" title="${nameHeader}" sortable="true"/>
			
			<spring:message code="provider.phone" var="phoneHeader" />
			<display:column property="phone" title="${phoneHeader}" sortable="true"/>
			
			<spring:message code="provider.cif" var="cifHeader" />
			<display:column property="cif" title="${cifHeader}" sortable="true"/>
			
			<display:column>
					<a href="provider/administrator/edit.do?providerId=${providersRow.id}">
						<spring:message code="provider.edit" />
					</a>
			</display:column>
			
			<display:column>
					<a href="provider/administrator/details.do?providerId=${providersRow.id}">
						<spring:message code="provider.details" />
					</a>
			</display:column>
		
		</security:authorize>
		
	</display:table>

<input type="button" class="btn btn-primary" name="new" value="<spring:message code="provider.new" />" 
		onclick="javascript: window.location.replace('provider/administrator/create.do');" />

