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

<script>

	function submitDelete(){
		var form = document.getElementById("idFormDelete");
		
		var res = confirm('<spring:message code="motorbike.confirm.delete" />');
		
		if(res) {
			form.submit();
		}
	}

</script>

<display:table name="motorbikes" pagesize="10" class="table table-striped" requestURI="${requestURI}" id="motorbikesRow">

	<security:authorize access="hasRole('ADMINISTRATOR')">
	
			<spring:message code="motorbike.number" var="numberHeader" />
			<display:column property="number" title="${numberHeader}" sortable="true"/>
			
			<spring:message code="motorbike.drivingTime" var="drivingTimeHeader" />
			<display:column property="drivingTime" title="${drivingTimeHeader}" sortable="true"/>
			
			<spring:message code="motorbike.licensePlate" var="licensePlateHeader" />
			<display:column property="licensePlate" title="${licensePlateHeader}" sortable="true"/>
			
			<spring:message code="motorbike.garage" var="garageHeader" />
			<display:column property="garage.location" title="${garageHeader}" sortable="true"/>
			
			<display:column>
					<a href="motorbike/administrator/edit.do?motorbikeId=${motorbikesRow.id}">
						<spring:message code="motorbike.edit" />
					</a>
			</display:column>
			
			<display:column>
					<a href="motorbike/administrator/details.do?motorbikeId=${motorbikesRow.id}">
						<spring:message code="motorbike.details" />
					</a>
			</display:column>
			
			<display:column>
					<a href="motorbike/administrator/changeGarage.do?motorbikeId=${motorbikesRow.id}">
							<spring:message code="motorbike.changeGarage" />
					</a>
			</display:column>
		
		</security:authorize>
		
	</display:table>

<input type="button" class="btn btn-primary" name="new" value="<spring:message code="motorbike.new" />" 
		onclick="javascript: window.location.replace('motorbike/administrator/create.do');" />

