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
				<acme:labelDetails code="product.type" value="${product.type}"/>
				<acme:labelDetails code="product.code" value="${product.code}"/>
				<acme:labelDetails code="product.name" value="${product.name}"/>
				<acme:labelDetails code="product.description" value="${product.description}"/>
				<acme:labelDetails code="product.stockPrice" value="${product.stockPrice}" eurCurrency="true"/>
				<acme:labelDetails code="product.salePrice" value="${product.salePrice}" eurCurrency="true"/>
				<acme:labelDetails code="product.actualStock" value="${product.actualStock}"/>
				<acme:labelDetails code="product.minStock" value="${product.minStock}"/>
				<acme:labelDetails code="product.provider" value="${product.provider.name}"/>
			</fieldset>
			
			<br />
			<input type="button" name="cancel" class="btn btn-primary" value="<spring:message code="cancel" />" 
				onclick="javascript: window.history.back();" />
		</jstl:if>
		
		<jstl:if test="${register == true && edit == true}">
			<form:form action="${requestURI}" modelAttribute="product">

				<form:hidden path="id" />
				<form:hidden path="version" />
				
				<spring:message code="product.type" />
				<form:select path="type">
					<form:options items="${productsType}"/>
				</form:select>
				<br /><br />
				<acme:textbox code="product.code" path="code"/>
				<br />
				<acme:textbox code="product.name" path="name"/>
				<br />
				<acme:textarea code="product.description" path="description"/>
				<br />
				<acme:textbox code="product.stockPrice" path="stockPrice"/>
				<br />
				<acme:textbox code="product.salePrice" path="salePrice"/>
				<br />
				<acme:textbox code="product.actualStock" path="actualStock"/>
				<br />
				<acme:textbox code="product.minStock" path="minStock"/>
				<br />
					
				<spring:message code="availableProviders" />
				<form:select path="provider">
					<form:options items="${availableProviders}" itemLabel="name" itemValue="id"/>
				</form:select>
					
				<br /><br />
					
				<acme:submit name="save" code="product.save"/>

			<input type="button" name="cancel" class="btn btn-primary" value="<spring:message code="cancel" />" 
					onclick="javascript: window.history.back();" />
			</form:form>
		</jstl:if>
		
		<jstl:if test="${edit == true && register == null}">
			<form:form action="${requestURI}" modelAttribute="product">

				<form:hidden path="id" />
				<form:hidden path="version" />
			
				<spring:message code="product.type" />
				<form:select path="type">
					<form:options items="${productsType}"/>
				</form:select>
				<br /><br />

				<acme:textbox code="product.code" path="code"/>
				<br />
				<acme:textbox code="product.name" path="name"/>
				<br />
				<acme:textarea code="product.description" path="description"/>
				<br />
				<acme:textbox code="product.stockPrice" path="stockPrice"/>
				<br />
				<acme:textbox code="product.salePrice" path="salePrice"/>
				<br />
				<acme:textbox code="product.actualStock" path="actualStock"/>
				<br />
				<acme:textbox code="product.minStock" path="minStock"/>
				<br />
					
				<spring:message code="availableProviders" />
				<form:select path="provider">
					<form:options items="${availableProviders}" itemLabel="name" itemValue="id"/>
				</form:select>
					
				<br /><br />
				
				<acme:submit name="update" code="product.save"/>&nbsp;
				<input type="button" name="cancel" class="btn btn-primary" value="<spring:message code="cancel" />" 
					onclick="javascript: window.history.back();" />
			</form:form>
		</jstl:if>

		
	</security:authorize>