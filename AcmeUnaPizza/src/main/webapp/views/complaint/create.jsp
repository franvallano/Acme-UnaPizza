<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="complaint/customer/create.do" modelAttribute="complaint">
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="creationMoment" />
	<form:hidden path="result" />
	<form:hidden path="state" />
	<form:hidden path="customer" />
	<form:hidden path="discussionMessages" />
	
	<acme:textbox code="complaint.title" path="title"/>
	
	<acme:textbox code="complaint.description" path="description"/>
	
	<acme:submit name="save" code="complaint.save"/>
	
	<acme:cancel url="complaint/actor/list.do" code="complaint.cancel"/>
	
</form:form>