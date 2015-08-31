<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Edit Book</title>
</head>
<body>
    <h3>Edit Book</h3>
    <form action="Controller">
        <p>
            Name: <input type="text" name="title"
                value="${helper.data.title}" />
        </p>
        <p>
            Description: <textarea name="description" rows="4" cols="50"
                >${helper.data.description}</textarea>
        </p>
        <p>
            Source: <input type="text" name="source"
                value="${helper.data.source}" />
        </p>
        <input type="submit" name="confirmButton" value="Confirm" />
    </form>
</body>
</html>