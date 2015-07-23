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
				<acme:labelDetails code="repair.moment" value="${repair.moment}"/>
				<br/>
				<acme:labelDetails code="repair.cost" value="${repair.cost}" eurCurrency="true"/>
				<br/>
				<acme:labelDetails code="repair.stuff" value="${repair.stuff.name}" eurCurrency="true"/>
			</fieldset>
			
			<br />
			<input type="button" name="cancel" class="btn btn-primary" value="<spring:message code="cancel" />" 
					onclick="javascript: window.location.replace('repair/boss/list.do');" />
		</jstl:if>
		
		<jstl:if test="${edit == true}">
			<form:form action="${requestURI}" modelAttribute="repair">

					<form:hidden path="id" />
					<form:hidden path="version" />
					<form:hidden path="workShop" />
					<form:hidden path="staff" />
				
					<acme:textbox code="repair.moment" path="moment"/>
					<br />
					<acme:textbox code="repair.cost" path="cost"/>
					<br />
			
					<form:label path="stuff">
						<spring:message code="repair.stuff"/>
					</form:label>
					 <form:select path="stuff">
					 	<jstl:forEach var="var" items="${reparableStuff}">
					 		<form:option label="${var.name}" value="${var.id}"/>
					 	</jstl:forEach>
					 </form:select>
					 <br />
					 
				<br />
				
				<acme:submit name="save" code="repair.save"/>&nbsp;
				<input type="button" name="cancel" class="btn btn-primary" value="<spring:message code="cancel" />" 
					onclick="javascript: window.location.replace('repair/boss/list.do');" />
			</form:form>
		</jstl:if>
	
		
	</security:authorize>