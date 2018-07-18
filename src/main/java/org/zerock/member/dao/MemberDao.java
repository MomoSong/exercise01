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
}
