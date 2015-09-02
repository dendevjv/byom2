<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Confirmation</title>
</head>
<body>
    <h3>Confirm Book</h3>
    <p>
        The value of the title that was sent to this page is: <b>${helper.data.title}</b>
    </p>
    <p>
        The value of the description that was sent to this page is: <b>${helper.data.description}</b>
    </p>
    <p>
        The value of the source that was sent to this page is: <b>${helper.data.source}</b>
    </p>
    <p>
        If there is an error, please select <em>Edit</em>, otherwise
        please select <em>Process</em>.
    </p>

    <form action="Controller" method="post">
        <p>
            <input type="submit" name="editButton" value="Edit">
            <input type="submit" name="processButton" value="Process">
        </p>
    </form>
</body>
</html>