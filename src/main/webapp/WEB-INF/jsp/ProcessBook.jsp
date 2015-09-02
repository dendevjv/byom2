<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Process Book</title>
</head>
<body>
    <h3>Process Book</h3>
	<p>
        Thank you for the information.<br /> 
        Name: ${helper.data.title}<br />
        Description: ${helper.data.description}<br />
        Source: ${helper.data.source}<br />
        Persistence will be implemented later
    </p>
    <form action="Controller" method="post">
        <p>
            <input type="submit" name="editButton" value="Edit">
        </p>
    </form>
</body>
</html>