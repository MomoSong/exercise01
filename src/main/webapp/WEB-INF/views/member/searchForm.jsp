<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>아이디 비밀번호 찾기</title>
</head>
<body>

<div class="container">
  <h2>아이디 찾기</h2>
  
<!--   <button id="test" type="button">디버깅용</button> -->
  
  <form method="post" id="searchForm">
    
    <div class="form-group">
      <label for="age">나이:</label><br/>
      <input type="number" class="form-control col-sm-4" id="age" placeholder="나이를 입력하세요"
       name="age"><br/>
    </div>
    <div class="form-group">
      <label for="name">이름:</label><br/>
      <input type="text" class="form-control col-sm-4" id="name" placeholder="이름을 입력하세요"
       name="name"><br/>
    </div>
    <div class="form-group">
      <label for="hp">휴대폰 번호:</label><br/>
      <input type="tel" class="form-control col-sm-4" id="hp" placeholder="휴대폰번호를 입력하세요"
       name="hp"><br/>
    </div>
    
 
    <button type="submit" id="submitBtn" class="btn btn-success">찾기</button>
    
    
    
  </form>
</div>

<hr/>

<div class="container">
  <h2>비밀번호 찾기</h2>
  
<!--   <button id="test" type="button">디버깅용</button> -->
  
  <form method="post" id="searchForm">
    <div class="form-group">
      <label for="email">이메일 아이디:</label><br/>
      <input type="email" class="form-control col-sm-4" id="email" placeholder="아이디 입력"
       name="email" >
    </div>
 	<br/>
    <button type="submit" id="submitBtn" class="btn btn-success">찾기</button>
    
    <hr/>
    
  </form>
</div>

</body>
</html>