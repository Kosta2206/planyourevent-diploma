<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<html>
<body>
	<h2>List Example</h2>
 
 	<a href='<spring:url value="/createTest" htmlEscape="true"/>'>
   		<input type="button" value="Go back to create test" />
	</a>
	
	<a href='<spring:url value="/welcome" htmlEscape="true"/>'><c:out value="Go back to welcome page"/></a>
 
	<c:if test="${not empty testList}">
 
		<ul>
			<c:forEach var="listValue" items="${testList}">
				<li>Name : ${listValue.name}</li>
				<li>Number : ${listValue.number}</li>
			</c:forEach>
		</ul>
 
	</c:if>
	
</body>
</html>