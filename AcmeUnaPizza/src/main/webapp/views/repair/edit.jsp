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
				<acme:dateLabelDetails code="repair.moment" value="${repair.moment}" time="false"/>
				<br/>
				<acme:labelDetails code="repair.cost" value="${repair.cost}" eurCurrency="true"/>
				<br/>
				<acme:labelDetails code="repair.stuff" value="${repair.stuff.name}"/>
				<br/>
				<acme:labelDetails code="workshop.company" value="${repair.workShop.company}"/>
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
					<form:hidden path="boss" />
					<form:hidden path="stuff" />
				
					<acme:textbox code="repair.moment" path="moment"/>
					<br />
					<acme:textbox code="repair.cost" path="cost"/>
					<br />
					 
				<br />
				
				<acme:submit name="save" code="repair.save"/>&nbsp;
				<input type="button" name="cancel" class="btn btn-primary" value="<spring:message code="cancel" />" 
					onclick="javascript: window.location.replace('repair/boss/list.do');" />
			</form:form>
		</jstl:if>
	
		
	</security:authorize>