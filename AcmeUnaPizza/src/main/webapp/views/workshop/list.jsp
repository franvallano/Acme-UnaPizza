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

<display:table name="workshops" pagesize="5" class="displaytag" requestURI="${requestURI}" id="workshopsRow">

	<security:authorize access="hasRole('BOSS')">
	
			<spring:message code="workshop.company" var="companyHeader" />
			<display:column property="company" title="${companyHeader}" sortable="true"/>
			
			<spring:message code="workshop.city" var="cityHeader" />
			<display:column property="city" title="${cityHeader}" sortable="true"/>
			
			<spring:message code="workshop.taxes" var="taxesHeader" />
			<display:column property="taxes" title="${taxesHeader}" sortable="true"/>
			
			<spring:message code="workshop.phoneNumber" var="phoneNumberHeader" />
			<display:column property="phoneNumber" title="${phoneNumberHeader}" sortable="true"/>
			
			<spring:message code="workshop.contact" var="contactHeader" />
			<display:column property="contact" title="${contactHeader}" sortable="true"/>
			
			<display:column>
					<a href="workshop/boss/edit.do?workshopId=${workshopsRow.id}">
						<spring:message code="workshop.edit" />
					</a>
			</display:column>
			
			<display:column>
					<a href="workshop/boss/details.do?workshopId=${workshopsRow.id}">
						<spring:message code="workshop.details" />
					</a>
			</display:column>
		
		</security:authorize>
		
	</display:table>

<input type="button" name="new" value="<spring:message code="workshop.new" />" 
		onclick="javascript: window.location.replace('workshop/boss/create.do');" />

