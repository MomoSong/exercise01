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
	
	$("#email").keyup(function(){
		  var emailVal = $("#email").val();
		  var ok = "glyphicon-ok";
		  var no = "glyphicon-remove";
		  if(emailVal != 0){
			  if(isValidEmailAddress(emailVal)){
				  $("#icon").removeClass(no);
				  $("#icon").addClass(ok);
			  }else{
				  $("#icon").removeClass(ok);
				  $("#icon").addClass(no);
			  }
		  }
	  
	});
	
	function isValidEmailAddress(emailAddress) {
		var pattern = new RegExp(/^(("[\w-\s]+")|([\w-]+(?:\.[\w-]+)*)|("[\w-\s]+")([\w-]+(?:\.[\w-]+)*))(@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$)|(@\[?((25[0-5]\.|2[0-4][0-9]\.|1[0-9]{2}\.|[0-9]{1,2}\.))((25[0-5]|2[0-4][0-9]|1[0-9]{2}|[0-9]{1,2})\.){2}(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[0-9]{1,2})\]?$)/i);
		return pattern.test(emailAddress);
	}
	

});
</script>


</head>
<body>
<div class="container">
  <h2>회원가입</h2>
  <form method="post">
    <div class="form-group">
      <label for="email">아이디:</label>
      <input type="email" class="form-control " id="email" placeholder="아이디 입력"
       name="email" ><span id="icon" class="glyphicon glyphicon-remove" ></span>
        
    </div>
    <div class="form-group">
      <label for="pw">비밀번호:</label>
      <input type="password" class="form-control" id="pw" placeholder="비밀번호 6~20 영문대소문자와 최소 1개의 숫자 혹은 특수문자를 포함하세요. "
       name="pw"><span id="icon" class="glyphicon glyphicon-remove" ></span>
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
       name="hp"><span id="icon" class="glyphicon glyphicon-remove" ></span>
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