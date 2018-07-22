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

	<div class="container">
	  <div class="jumbotron">
	    <c:if test="${not empty id }">
			<h1>${id }</h1>
		</c:if>
		
		<c:if test="${not empty searchIdMsg }">
			<h1>${searchIdMsg }</h1>
		</c:if>
		
		<c:if test="${not empty searchId }">
			<h1>찾으시는 아이디는 ${searchId } 입니다.</h1>
		</c:if>
	  </div>
	</div>
</body>
</html>