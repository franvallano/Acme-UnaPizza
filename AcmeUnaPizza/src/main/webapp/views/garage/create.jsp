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

<form:form action="garage/administrator/create.do" modelAttribute="garage">

	<form:hidden path="id" />
	<form:hidden path="version" />

	<acme:textbox code="garage.location" path="location"/>
	
	<acme:textbox code="garage.size" path="size"/>
	
	<acme:submit name="save" code="garage.save"/>
	
	<acme:cancel url="garage/administrator/list.do" code="garage.cancel"/>
	<br />
	
</form:form>