package org.zerock.member.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.zerock.member.dao.MemberDao;
import org.zerock.member.dto.LoginDTO;

@Service
public class MemberService {

	@Inject
	private MemberDao dao;
	
	public LoginDTO login(String email, String pw){
		return dao.login(email, pw);
	}

	public void join(LoginDTO dto) {
		dao.join(dto);
	}
}
