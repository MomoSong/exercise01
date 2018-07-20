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
	
	public LoginDTO login(String email, String pw) {
		System.out.println(email + ", " + pw);
		Map<String, String> map = new HashMap<>();
		map.put("email", email);
		map.put("pw", pw);
		return sqlSession.selectOne(namespace+"login", map);
	}

	public void join(LoginDTO dto) {
		System.out.println(getClass() + " : " + dto);
		sqlSession.insert(namespace+"join", dto);
	}
	
	public String checkEmail(String email) {
		System.out.println(email);
		return sqlSession.selectOne(namespace + "checkEmail", email);
	}
	
	public void createAuthKey(String user_email, String user_authCode) throws Exception {
		// TODO Auto-generated method stub
		LoginDTO dto = new LoginDTO();
		dto.setUser_authCode(user_authCode);
		dto.setEmail(user_email);

		sqlSession.selectOne(namespace + "createAuthKey", dto);
	}
	
	public void userAuth(String user_email) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.update(namespace + "userAuth", user_email);
	}
	
	public String selectUserAuth(String user_email) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(namespace + "selectUserAuth", user_email);
	}
}
