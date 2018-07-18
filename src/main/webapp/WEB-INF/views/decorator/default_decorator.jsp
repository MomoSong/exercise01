<%@page import="org.zerock.member.dto.LoginDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="decorator"
	uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
pageContext.setAttribute("absUri", request.getContextPath());
%>
<%
	LoginDTO dto = new LoginDTO(); 
	dto = (LoginDTO)session.getAttribute("login");
%>

<!DOCTYPE html>
<html>
  <head>
  
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  
    <!-- Bootstrap core CSS -->
   <link href=" ${pageContext.request.contextPath}/resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

   <!-- Custom fonts for this template -->
   <link href="${pageContext.request.contextPath}/resources/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
   <link href='https://fonts.googleapis.com/css?family=Lora:400,700,400italic,700italic' rel='stylesheet' type='text/css'>
   <link href='https://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800' rel='stylesheet' type='text/css'>

   <!-- Custom styles for this template -->
   <link href="${pageContext.request.contextPath}/resources/css/clean-blog.min.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/resources/css/nanumFont.css" rel="stylesheet" type="text/css">

  
  <decorator:head></decorator:head>
  </head>
  <body>
  
  <!-- Navigation -->
    <nav class="navbar navbar-expand-lg navbar-light fixed-top" id="mainNav">
      <div class="container">
        <a class="navbar-brand" href="index.html">부동산닷컴</a>
        <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
          Menu
          <i class="fa fa-bars"></i>
        </button>
        <div class="collapse navbar-collapse" id="navbarResponsive">
          <ul class="navbar-nav ml-auto">
            <li class="nav-item">
              <a class="nav-link" href="/">메인</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="#">매물정보</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="#">뉴스</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="#">공시지가 </a>
            </li>
            
           
           <c:if test="<%=dto != null %>"> 
            <c:choose>
            	<c:when test="<%= dto.getLogin()==true && dto.getGrade()==9 %>">
            		<li class="nav-item">
            		  <a class="nav-link" href="/member/logout.do">로그아웃</a>
            		</li>
            		<li class="nav-item">
            		  <a class="nav-link" href="#">회원리스트</a>
            		</li>
            	</c:when>
            	
            	<c:when test="<%= dto.getLogin()==true && dto.getGrade()==1 %>">
            		<li class="nav-item">
            		  <a class="nav-link" href="/member/logout.do">로그아웃</a>
            		</li>
            	</c:when>
            </c:choose>
            </c:if>
            
            <c:choose>
            	<c:when test="<%=dto == null %>">
            		<li class="nav-item">
              			<a class="nav-link" href="/member/login.do">로그인</a>
            		</li>
		            <li class="nav-item">
		              <a class="nav-link" href="/member/join.do">회원가입</a>
		            </li>
		        </c:when>
            </c:choose>
                        
          </ul>
        </div>
      </div>
    </nav>
    
  
   <!-- Page Header -->
    <header class="masthead" style="background-image: url('img/home-bg.jpg')">
      <div class="overlay"></div>
      <div class="container">
        <div class="row">
          <div class="col-lg-8 col-md-10 mx-auto">
            <div class="site-heading">
              <h1>부동산닷컴</h1>
            </div>
          </div>
        </div>
      </div>
    </header>
  
  <decorator:body></decorator:body>
    
  </body>
</html>
        
        