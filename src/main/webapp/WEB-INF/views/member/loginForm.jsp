<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<meta name="viewport" content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, width=device-width"/>
<script src="//developers.kakao.com/sdk/js/kakao.min.js"></script>
<title>로그인</title>

<script type="text/javascript">

$(document).ready(function(){

	$("#loginBtn").click(function(){
		document.getElementById("loginForm").submit();
	});		
	
	$("#searchBtn").click(function(){
		location = "/member/searchForm.do";
	});
	
	$("#loginForm").keypress(function(f){
		if(f.which==13){
			$("#loginForm").submit();
		}
	});
	
});

</script>

</head>
<body>
  <div class="container col-md-6" style="float: none;">
  	<h2>회원 로그인</h2>
	  <form action="/member/login.do" id="loginForm" method="post">
	    <div class="form-group">
	      <label for="id">아이디:</label><br/>
	      <input type="text" class="form-control" id="email" placeholder="이메일 아이디 입력"
	       name="email">
	    </div>
	    <div class="form-group">
	      <label for="pw">비밀번호:</label><br/>
	      <input type="password" class="form-control" id="pw" placeholder="비밀번호 입력"
	       name="pw">
	    </div>
	    <button type="button" id="loginBtn" class="btn">로그인</button>
	    <button type="button" id="searchBtn" class="btn btn-info">아이디, 비밀번호 찾기</button>
	  </form>
	  
	  <a id="kakao-login-btn"></a>
    <a href="http://developers.kakao.com/logout"></a>

    <script type='text/javascript'>
      //<![CDATA[
        // 사용할 앱의 JavaScript 키를 설정해 주세요.
        Kakao.init('526c7e7f8130d9506ed668e8bb6dc8b6');
        // 카카오 로그인 버튼을 생성합니다.
        Kakao.Auth.createLoginButton({
          container: '#kakao-login-btn',
          success: function(authObj) {
            alert(JSON.stringify(authObj));
          },
          fail: function(err) {
             alert(JSON.stringify(err));
          }
        });
      //]]>
    </script>
    
  </div>

	
  
</body>
</html>