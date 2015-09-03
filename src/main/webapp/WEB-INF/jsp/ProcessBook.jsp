<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Process Book</title>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core" %>
</head>
<body>
    <h3>Process Book</h3>
	<p>
        Thank you for the information.<br /> 
        Name: ${helper.data.title}<br />
        Description: ${helper.data.description}<br />
        Source: ${helper.data.source}
    </p>
    <form action="Controller" method="post">
        <p>
            <input type="submit" name="editButton" value="Edit">
        </p>
    </form>
    <form method="GET" action="Controller">
      <p>
          <input type="submit" name="editButton" value="New">
      </p>
    </form>
    <table>
        <tr>
            <th>Id</th>
            <th>Title</th>
            <th>Description</th>
            <th>Source</th>
        </tr>
        <core:forEach var="row" items="${database}">
            <tr>
                <td>${row.id}</td>
                <td>${row.title}</td>
                <td>${row.description}</td>
                <td>${row.source}</td>
            </tr>
        </core:forEach>
    </table>
</body>
</html>