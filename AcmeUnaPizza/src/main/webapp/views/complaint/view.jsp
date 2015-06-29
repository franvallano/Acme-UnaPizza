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

<b><spring:message code="complaint.title" />:</b>
<jstl:out value="${complaint.title}" />
<br />

<b><spring:message code="complaint.creationMoment" />:</b>
<fmt:formatDate value="${complaint.creationMoment}" pattern="dd-MM-yyyy HH:mm"/>
<br />

<b><spring:message code="complaint.description" />:</b>
<jstl:out value="${complaint.description}" />
<br />

<jstl:if test="${complaint.result != null}">
	<b><spring:message code="complaint.result" />:</b>
	<jstl:choose>
		<jstl:when test="${complaint.result eq null || complaint.result == ''}">
			-
		</jstl:when>
		<jstl:otherwise>
			<jstl:out value="${complaint.result}" />
		</jstl:otherwise>
	</jstl:choose>
	<br />
</jstl:if>

<b><spring:message code="complaint.state" />:</b>
<jstl:out value="${complaint.state}" />
<br />

<jsp:useBean id="principalId" class="security.LoginService" />

<fieldset>
	<legend><b><spring:message code="complaint.discussionMessages" />:</b></legend>
	<jstl:forEach var="discussionMessage" items="${complaint.discussionMessages}">
		<b><fmt:formatDate value="${discussionMessage.moment}" pattern="dd-MM-yyyy HH:mm"/> - <jstl:out value="${discussionMessage.actor.userAccount.username}"></jstl:out>:</b> <jstl:out value="${discussionMessage.message}"></jstl:out>
		<br />
	</jstl:forEach>
	<jstl:if test="${complaint.administrator.userAccount.id == principalId.getPrincipal().getId() || complaint.customer.userAccount.id == principalId.getPrincipal().getId() }">
		<jstl:if test="${complaint.state != 'CLOSED' && complaint.state != 'CANCELLED'}">
			<a href="discussionMessage/actor/create.do?complaintId=${complaint.id}">
				[<spring:message	code="complaint.sendNewMessage" />]
			</a>
		</jstl:if>
	</jstl:if>
</fieldset>

<input type="button" onclick="javascript:history.back();" value="<spring:message code='complaint.cancel' />" />