<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>회원가입</title>

<script type="text/javascript">
$(document).ready(function () {
	$("#cancel").click(function(){
		location = "/";
	});
});
</script>

</head>
<body>
<div class="container">
  <h2>회원가입</h2>
  <form method="post">
    <div class="form-group">
      <label for="email">아이디:</label>
      <input type="email" class="form-control" id="email" placeholder="아이디 입력"
       name="email">
    </div>
    <div class="form-group">
      <label for="pw">비밀번호:</label>
      <input type="password" class="form-control" id="pw" placeholder="비밀번호 입력"
       name="pw">
    </div>
    <div class="form-group">
      <label for="age">나이:</label>
      <input type="number" class="form-control" id="age" placeholder="나이 입력"
       name="age">
    </div>
    <div class="form-group">
      <label for="name">이름:</label>
      <input type="text" class="form-control" id="name" placeholder="이름 입력"
       name="name">
    </div>
    <div class="form-group">
      <label for="hp">휴대폰 번호:</label>
      <input type="tel" class="form-control" id="hp" placeholder="휴대폰번호 입력"
       name="hp">
    </div>
    <div class="form-group">
      <label for="addr">주소입력:</label>
      <input type="text" class="form-control" id="addr" placeholder="주소 입력"
       name="addr">
    </div>
 
    <button type="submit" class="btn">회원가입</button>
    <button type="button" id="cancel" class="btn btn-danger">취소</button>
  </form>
</div>
</body>
</html>