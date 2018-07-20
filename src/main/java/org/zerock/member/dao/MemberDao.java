package org.zerock.member.dao;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import org.zerock.member.dto.LoginDTO;

@Repository
public class MemberDao {

	@Inject
	private SqlSession sqlSession;
	
	private String namespace = "org.zerock.mapper.MemberMapper.";
	
	//로그인을 시도하면 해쉬맵에 아이디와 비밀번호를 담아서 session으로 보내준다.
	public LoginDTO login(String email, String pw) {
		System.out.println(email + ", " + pw);
		Map<String, String> map = new HashMap<>();
		map.put("email", email);
		map.put("pw", pw);
		return sqlSession.selectOne(namespace+"login", map);
	}

	//dto를 DB에 보내서 회원가입을 한다.
	public void join(LoginDTO dto) {
		System.out.println(getClass() + " : " + dto);
		sqlSession.insert(namespace+"join", dto);
	}
	
	// 이메일 중복확인할때 DB에 존재하는 이메일인지 검사하는 함수. 메일이 있으면 메일을 반환한다.
	public String checkEmail(String email) {
		System.out.println(email);
		return sqlSession.selectOne(namespace + "checkEmail", email);
	}
	
	//유저의 인증키를 업데이트한다. 이메일이 일치하는 유저만 키를 업데이트
	public void createAuthKey(String user_email, String user_authCode) throws Exception {
		// TODO Auto-generated method stub
		LoginDTO dto = new LoginDTO();
		dto.setUser_authCode(user_authCode);
		dto.setEmail(user_email);

		sqlSession.selectOne(namespace + "createAuthKey", dto);
	}
	
	
	//유저의 이메일에 일치하는 DB 레코드를 조회하여 user_authStatus를 1로 업데이트 한다.
	public void userAuth(String user_email) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.update(namespace + "userAuth", user_email);
	}
	
	//유저의 인증상태가 1인지 0인지 조사하기 위해 DB에서 user_authStatus를 조회한다.
	public String selectUserAuth(String user_email) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(namespace + "selectUserAuth", user_email);
	}
}
