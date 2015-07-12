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

<display:table name="products" pagesize="10" class="displaytag" requestURI="${requestURI}" id="productsRow">
	
	<security:authorize access="hasRole('ADMINISTRATOR')">

		<spring:message code="product.code" var="codeHeader" />
		<display:column property="code" title="${codeHeader}" sortable="true"/>
		
		<spring:message code="product.name" var="nameHeader" />
		<display:column property="name" title="${nameHeader}" sortable="true"/>
		
		<spring:message code="product.description" var="descriptionHeader" />
		<display:column property="description" title="${descriptionHeader}" sortable="true"/>
		
		<spring:message code="product.actualStock" var="actualStockHeader" />
		<display:column property="actualStock" title="${actualStockHeader}" sortable="true"/>
		
		<spring:message code="product.minStock" var="minStockHeader" />
		<display:column property="minStock" title="${minStockHeader}" sortable="true"/>

		<display:column>
				<a href="product/administrator/details.do?productId=${productsRow.id}">
					<spring:message code="product.details" />
				</a>
		</display:column>
		
		<display:column>
				<a href="product/administrator/edit.do?productId=${productsRow.id}">
					<spring:message code="product.edit" />
				</a>
		</display:column>
		
	</security:authorize>
		
</display:table>
	
	<jstl:if test="${findAll != null && findAll == true}">
		<input type="button" name="new" value="<spring:message code="new" />" 
			onclick="javascript: window.location.replace('product/administrator/create.do?productType=${products[0].type}');" />
	</jstl:if>



