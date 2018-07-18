<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login</title>
</head>
<body>
  <div class="container col-md-6" style="float: none;">
  	<h2>회원 로그인</h2>
	  <form action="/member/login.do" method="post">
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
	    <button type="submit" class="btn">로그인</button>
	  </form>
  </div>

</body>
</html>