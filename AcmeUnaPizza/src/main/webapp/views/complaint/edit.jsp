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

<security:authorize access="hasAnyRole('ADMINISTRATOR','CUSTOMER')">

	<jstl:if test="${details == true}">
			<fieldset>
				<acme:labelDetails code="complaint.title" value="${complaint.title}"/>
				<acme:labelDetails code="complaint.creationMoment" value="${complaint.creationMoment}"/>
				<acme:labelDetails code="complaint.description" value="${complaint.description}"/>
				<acme:labelDetails code="complaint.result" value="${complaint.result}"/>
				<acme:labelDetails code="complaint.state" value="${complaint.state}"/>
				<br/>
				<fieldset>
					<legend><h3><spring:message code="complaint.discussionMessages" /></h3></legend>
					<jstl:forEach var="discussionMessage" items="${complaint.discussionMessages}" varStatus="rowIndex">
						<acme:dateLabelDetails code="complaint.discussionMessages.moment" value="${discussionMessage.moment}"/>
						<acme:labelDetails code="complaint.discussionMessages.message" value="${discussionMessage.message}" />
						<br/>
					</jstl:forEach>
				</fieldset>
			</fieldset>
			
			<br />
			<input type="button" name="new" value="<spring:message code="newDiscussionMessage" />" 
					onclick="javascript: window.location.replace('discussionMessage/actor/create.do');" />
			<br />
			<input type="button" name="cancel" value="<spring:message code="cancel" />" 
					onclick="javascript: window.location.replace('complaint/actor/list.do');" />
		</jstl:if>
		
	<jstl:if test="${edit == true}">
		<form:form action="${requestURI}" modelAttribute="complaint">

					<form:hidden path="id" />
					<form:hidden path="version" />
					<form:hidden path="discussionMessages" />
				
					<acme:textbox code="complaint.title" path="title"/>
					<br />
					<acme:textbox code="complaint.creationMoment" path="creationMoment"/>
					<br />
					<acme:textbox code="complaint.description" path="description"/>
					<br />
					<acme:textbox code="complaint.result" path="result"/>
					<br />
					<acme:textbox code="complaint.state" path="state"/>
					<br />
			
				<br />
				
				<acme:submit name="save" code="complaint.save"/>&nbsp;
				<input type="button" name="cancel" value="<spring:message code="cancel" />" 
					onclick="javascript: window.location.replace('complaint/actor/list.do');" />
			</form:form>
		</jstl:if>
	

</security:authorize>