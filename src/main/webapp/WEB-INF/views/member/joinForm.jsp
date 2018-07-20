<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>회원가입</title>

<script type="text/javascript">

$(document).ready(function () {
	var validEmail = false;
	var validPw = false;
	var validHp = false;
	var validAll = false;
	var checkEmail = false;

	//취소 버튼을 누를시 메인 페이지로
	$("#cancel").click(function(){
		location = "/";
	});
	
	// 키가 변경될때마다 이메일 유효성을 체크해서 <span>태그의 글리피콘 모양을 바꾼다.
	$("#email").keyup(function(){
		  var emailVal = $("#email").val();
		  var ok = "glyphicon-ok";
		  var no = "glyphicon-remove";
		  if(emailVal != 0){
			  if(isValidEmailAddress(emailVal)){
				  $("#icon1").removeClass(no);
				  $("#icon1").addClass(ok);
				  validEmail = true;
			  }else{
				  $("#icon1").removeClass(ok);
				  $("#icon1").addClass(no);
				  validEmail = false;
			  }
		  }else{
			  $("#icon1").removeClass(ok);
			  $("#icon1").addClass(no);
			  validEmail = false;
		  }
	  
	});
	
	// 키가 변경될때마다 패스워드 유효성을 체크해서 <span>태그의 글리피콘 모양을 바꾼다.
	$("#pw").keyup(function(){
		  var pw = $("#pw").val();
		  var ok = "glyphicon-ok";
		  var no = "glyphicon-remove";
		  if(pw != 0){
			  if(isValidPw(pw)){
				  $("#icon2").removeClass(no);
				  $("#icon2").addClass(ok);
				  validPw = true;
			  }else{
				  $("#icon2").removeClass(ok);
				  $("#icon2").addClass(no);
				  validPw = false;
			  }
		  }else{
			  $("#icon2").removeClass(ok);
			  $("#icon2").addClass(no);
			  validPw = false;
		  }
	  
	});
	
	// 키가 변경될때마다 핸드폰 형식 유효성을 체크해서 <span>태그의 글리피콘 모양을 바꾼다.
	$("#hp").keyup(function(){
		  var tel = $("#hp").val();
		  var ok = "glyphicon-ok";
		  var no = "glyphicon-remove";
		  if(tel != 0){
			  if(isValidTel(tel)){
				  $("#icon3").removeClass(no);
				  $("#icon3").addClass(ok);
				  validHp = true;
			  }else{
				  $("#icon3").removeClass(ok);
				  $("#icon3").addClass(no);
				  validHp = false;
			  }
		  }else{
			  $("#icon3").removeClass(ok);
			  $("#icon3").addClass(no);
			  validHp = false;
		  }
	  
	});
	
	//이메일 유효성을 검사하는 정규식 함수
	function isValidEmailAddress(emailAddress) {
		var pattern = new RegExp(/^(("[\w-\s]+")|([\w-]+(?:\.[\w-]+)*)|("[\w-\s]+")([\w-]+(?:\.[\w-]+)*))(@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$)|(@\[?((25[0-5]\.|2[0-4][0-9]\.|1[0-9]{2}\.|[0-9]{1,2}\.))((25[0-5]|2[0-4][0-9]|1[0-9]{2}|[0-9]{1,2})\.){2}(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[0-9]{1,2})\]?$)/i);
		return pattern.test(emailAddress);
	}
	
	//패스워드 유효성을 검사하는 정규식 함수
	function isValidPw(pw) {
		var pattern = new RegExp(/^(?=.*[a-zA-Z])((?=.*\d)|(?=.*\W)).{6,20}$/);
		return pattern.test(pw);
	}
	
	//핸드폰 형식 유효성을 검사하는 정규식 함수
	function isValidTel(tel) {
		var pattern = new RegExp(/(\d{3}).*(\d{3}).*(\d{4})/);
		return pattern.test(tel);
	}
	
	//서브밋 버튼을 실행하면 이메일, 비밀번호, 핸드폰 형식이 맞는지 확인하고 전체 칸이 다 찾는지 확인한 후에 안되면 alert을 띄우고 되었으면 전송시킨다.
	$("#submitBtn").click(function(){
		
		
		
		if(checkEmail == false){
			alert("이메일 중복 체크를 해주세요!");
			return false;
		}
		
		if(validEmail == true && validPw == true && validHp == true && validAll == true){
			alert("이메일 인증을 해주세요!")
			document.getElementById("joinForm").submit();
		}else if(validEmail == false){
			alert("이메일 형식을 확인하세요");
		}else if(validPw == false){
			alert("비밀번호 형식을 확인하세요");			
		}else if(validHp == false){
			alert("핸드폰 번호 형식을 확인하세요");
		}else{
			for(var i = 0; i < $("input").length; i++){
				if($(".form-control").eq(i).val() == ""){
					alert("빈칸을 모두 입력하세요");
					validAll = false;
					return false;
				}else{
					validAll = true;
					
				}
			}
		}
		return; 
	});
	
	// 댓글 등록 이벤트 처리
	$("#checkBtn").click(function(){
//			alert("add");
		// Ajax를 통해서 데이터를 서버에 보내기
		var email = $("#email").val();
		
		
		$.ajax({
			// method 방식
			type: "post",
			// 요청주소 - 댓글쓰기
			url : "/member/",
			// 서버에서 돌려 받는 데이터의 형식
			dataType : "text",
			contentType:"application/text;charset=UTF-8",
			data : email,
			// 서버에서 정상적인 처리가 됐을 때 실행하는 함수
			success : function(result){
				if(result == "empty"){
					alert("사용 가능합니다.");
					checkEmail = true;
				}else{
					alert("이미 사용중인 이메일입니다.");
					$("#email").val("");
					checkEmail = false;
				}
			}
		});
	});
	
	$("#test").click(function(){
		alert($(".form-control").eq(1).val());
	});

});
</script>


</head>
<body>
<div class="container">
  <h2>회원가입</h2>
  
<!--   <button id="test" type="button">디버깅용</button> -->
  
  <form method="post" id="joinForm">
    <div class="form-group">
      <label for="email">이메일 아이디:</label>
      <input type="email" class="form-control col-sm-4" id="email" placeholder="아이디 입력"
       name="email" ><button type="button" class="btn btn-success" id="checkBtn">중복체크</button><span id="icon1" class="glyphicon glyphicon-remove" ></span>
        
    </div>
    <div class="form-group">
      <label for="pw">비밀번호:</label>
      <input type="password" class="form-control col-sm-4" id="pw" placeholder="비밀번호 6~20 영문대소문자와 최소 1개의 숫자 혹은 특수문자를 포함하세요. "
       name="pw"><span id="icon2" class="glyphicon glyphicon-remove" ></span>
    </div>
    <div class="form-group">
      <label for="age">나이:</label>
      <input type="number" class="form-control col-sm-4" id="age" placeholder="나이 입력"
       name="age">
    </div>
    <div class="form-group">
      <label for="name">이름:</label>
      <input type="text" class="form-control col-sm-4" id="name" placeholder="이름 입력"
       name="name">
    </div>
    <div class="form-group">
      <label for="hp">휴대폰 번호:</label>
      <input type="tel" class="form-control col-sm-4" id="hp" placeholder="휴대폰번호 입력"
       name="hp"><span id="icon3" class="glyphicon glyphicon-remove" ></span>
    </div>
    <div class="form-group">
      <label for="addr">주소입력:</label>
      <input type="text" class="form-control col-sm-4" id="addr" placeholder="주소 입력"
       name="addr">
    </div>
 
    <button type="button" id="submitBtn" class="btn btn-success">회원가입</button>
    <button type="button" id="cancel" class="btn btn-danger">취소</button>
  </form>
</div>
</body>
</html>