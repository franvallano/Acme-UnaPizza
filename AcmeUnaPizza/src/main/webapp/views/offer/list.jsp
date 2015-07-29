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

<display:table name="offers" pagesize="10" class="table table-striped" requestURI="${requestURI}" id="offersRow">
	
	<security:authorize access="hasRole('ADMINISTRATOR')">

		<spring:message code="offer.name" var="nameHeader" />
		<display:column property="name" title="${nameHeader}" sortable="true"/>
		
		<spring:message code="offer.discountPerc" var="discountPercHeader" />
		<display:column property="discount" title="${discountPercHeader}" sortable="true"/>

		<display:column>
				<a href="offer/administrator/details.do?offerId=${offersRow.id}">
					<spring:message code="offer.details" />
				</a>
		</display:column>
		
		<display:column>
				<a href="offer/administrator/edit.do?offerId=${offersRow.id}">
					<spring:message code="offer.edit" />
				</a>
		</display:column>
		
	</security:authorize>
		
</display:table>

	<input type="button" class="btn btn-primary" name="new" value="<spring:message code="new" />" 
		onclick="javascript: window.location.replace('offer/administrator/create.do');" />
	



