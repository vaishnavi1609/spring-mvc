<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<sec:authorize access="hasRole('ROLE_ADMIN')">
    <h2>logged in as admin</h2>
</sec:authorize> 

<sec:authorize access="hasRole('ROLE_USER')">
    <h2>logged in as user</h2>
</sec:authorize> 

<h3>Welcome to JournalDEV Tutorials</h3>
<ul>
	<li>Java 8 tutorial</li>
	<li>Spring tutorial</li>
	<li>Gradle tutorial</li>
	<li>BigData tutorial</li>
</ul>

<c:url value="/logout" var="logoutUrl" />
<form id="logout" action="${logoutUrl}" method="post" >
  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
</form>
<c:if test="${pageContext.request.userPrincipal.name != null}">
	<a href="/spring-mvc/admin">admin</a>
	<a href="javascript:document.getElementById('logout').submit()">Logout</a>
</c:if>

</body>
</html>