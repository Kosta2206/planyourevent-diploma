<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<html>
<head>
    <title>Welcome page</title>
</head>
<body>
<h2>Welcome page</h2>

<a href='<spring:url value="/createTest" htmlEscape="true"/>'>
   		<input type="button" value="create new test" />
</a>
<a href='<spring:url value="/testList" htmlEscape="true"/>'>
   		<input type="button" value="show test list" />
</a>


</body>
</html>