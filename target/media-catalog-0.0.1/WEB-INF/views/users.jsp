<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>Contributors</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
</head>
<body>
	<h2>User List</h2>

	<table class="table">
		<thead>
			<tr>
				<th>Name</th>
				<th>Email</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="user" items="${contributors}">
				<tr>
					<td>${user.firstName} ${user.lastName}</td>
					<td>${user.email}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>