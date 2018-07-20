<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>로그인</title>

<script type="text/javascript">

$(document).ready(function(){

	$("#loginBtn").click(function(){
		document.getElementById("loginForm").submit();
	});		
});


</script>


</head>
<body>
  <div class="container col-md-6" style="float: none;">
  	<h2>회원 로그인</h2>
	  <form action="/member/login.do" id="loginForm" method="post">
	    <div class="form-group">
	      <label for="id">아이디:</label>
	      <input type="text" class="form-control" id="email" placeholder="이메일 아이디 입력"
	       name="email">
	    </div>
	    <div class="form-group">
	      <label for="pw">비밀번호:</label>
	      <input type="password" class="form-control" id="pw" placeholder="비밀번호 입력"
	       name="pw">
	    </div>
	    <button type="button" id="loginBtn" class="btn">로그인</button>
	    <button type="button" class="btn btn-info">아이디, 비밀번호 찾기</button>
	  </form>
  </div>

</body>
</html>