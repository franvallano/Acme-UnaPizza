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

<display:table name="repairs" pagesize="5" class="displaytag" requestURI="${requestURI}" id="repairsRow">

	<security:authorize access="hasRole('BOSS')">
	
			<spring:message code="repair.cost" var="costHeader" />
			<display:column property="cost" title="${costHeader}" sortable="true"/>
			
			<spring:message code="repair.moment" var="momentHeader" />
			<display:column property="moment" title="${momentHeader}" sortable="true"/>
			
			<display:column>
					<a href="repair/boss/edit.do?repairId=${repairsRow.id}">
						<spring:message code="repair.edit" />
					</a>
			</display:column>
			
			<display:column>
					<a href="repair/boss/details.do?repairId=${repairsRow.id}">
						<spring:message code="repair.details" />
					</a>
			</display:column>
		
		</security:authorize>
		
	</display:table>


