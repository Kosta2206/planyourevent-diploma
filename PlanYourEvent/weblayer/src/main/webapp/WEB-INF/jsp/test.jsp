<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>PlanYourEvent Test</title>
</head>
<body>
<h2>${message}</h2>
<form:form method="post" action="createTest" modelAttribute="testDTO">
 
    <table>
    <tr>
        <td><form:label path="name">Name</form:label></td>
        <td><form:input path="name" /></td> 
    </tr>
    <tr>
        <td><form:label path="number">Number</form:label></td>
        <td><form:input path="number" /></td>
    </tr>
    <tr>
        <td colspan="2">
            <input type="submit" value="Add Test record"/>
        </td>
    </tr>
</table>  
<a href='<spring:url value="/welcome" htmlEscape="true"/>'><c:out value="Go back to welcome page"/></a>
     
</form:form>
</body>
</html>