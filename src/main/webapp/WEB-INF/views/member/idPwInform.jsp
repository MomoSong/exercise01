<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>아이디 안내</title>
</head>
<body>
	<c:if test="${id eq null }">
		<h1>${searchMsg }</h1>
	</c:if>
	
	<c:if test="${id ne null }">
		<h1>사용하신 아이디는 ${id } 입니다.</h1>
	</c:if>
</body>
</html>