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
				<acme:labelDetails code="provider.name" value="${provider.name}"/>
				<acme:labelDetails code="provider.phone" value="${provider.phone}"/>
				<acme:labelDetails code="provider.cif" value="${provider.cif}"/>
				<br/>
				<fieldset>
					<legend><h3><spring:message code="provider.products" /></h3></legend>
					<jstl:forEach var="product" items="${provider.products}" varStatus="rowIndex">
						<acme:labelDetails code="provider.product.type" value="${product.type}"/>
						<acme:labelDetails code="provider.product.code" value="${product.code}"/>
						<acme:labelDetails code="provider.product.name" value="${product.name}"/>
						<acme:labelDetails code="provider.product.description" value="${product.description}"/>
						<br/>
					</jstl:forEach>
				</fieldset>
			</fieldset>
			
			<br />
			<input type="button" name="cancel" class="btn btn-primary" value="<spring:message code="cancel" />" 
					onclick="javascript: window.location.replace('provider/administrator/list.do');" />
		</jstl:if>
		
		<jstl:if test="${edit == true}">
			<form:form action="${requestURI}" modelAttribute="provider">

					<form:hidden path="id" />
					<form:hidden path="version" />
					<form:hidden path="products" />
				
					<acme:textbox code="provider.name" path="name"/>
					<br />
					<acme:textbox code="provider.phone" path="phone"/>
					<br />
					<acme:textbox code="provider.cif" path="cif"/>
					<br />					

				<acme:submit name="save" code="provider.save"/>&nbsp;
				<input type="button" name="cancel" class="btn btn-primary" value="<spring:message code="cancel" />" 
					onclick="javascript: window.location.replace('provider/administrator/list.do');" />
			</form:form>
		</jstl:if>
	
		
	</security:authorize>