<%--
info.jsp
 *
 * Copyright (C) 2014 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


 
<table id="cookie_table" class="well">
	<tr>
		<td>
			<p class="mini_title"><spring:message code="cookies.title" /></p>
		</td>
	</tr>
	<tr>
		<td>
			<p><spring:message code="cookies.text" /></p>
		</td>
	</tr>
	<tr>
		<td>
			<p>
				<spring:message code="cookies.seeMore" />
				<a href='<spring:message code="cookies.link" />'>
					<spring:message code="cookies.link" />
				</a>
			</p>
		</td>
	</tr>
</table>

<input type="button" name="cancel" class="btn btn-primary" value="<spring:message code="cancel" />" 
		onclick="javascript: window.history.back();" />
