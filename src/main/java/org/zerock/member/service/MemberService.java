package org.zerock.member.service;

import javax.inject.Inject;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.member.dao.MemberDao;
import org.zerock.member.dto.LoginDTO;
import org.zerock.member.util.MailHandler;
import org.zerock.member.util.TempKey;

@Service
public class MemberService {

	@Inject
	private JavaMailSender mailSender;
	
	@Inject
	private MemberDao dao;
	
	public LoginDTO login(String email, String pw){
		return dao.login(email, pw);
	}

	public void join(LoginDTO dto) {
		dao.join(dto);
	}
	
	public String checkEmail(String email) {
		return dao.checkEmail(email);
	}
	
	@Transactional
	public void create(LoginDTO dto) throws Exception {
	dao.join(dto); // 회원가입 DAO

	String key = new TempKey().getKey(50, false); // 인증키 생성

	dao.createAuthKey(dto.getEmail(), key); // 인증키 DB저장

	MailHandler sendMail = new MailHandler(mailSender);
	sendMail.setSubject("[ALMOM 서비스 이메일 인증]");
	sendMail.setText(
			new StringBuffer().append("<h1>메일인증</h1>").append("<a href='http://localhost/member/emailConfirm?user_email=").append(dto.getEmail()).append("&key=").append(key).append("' target='_blenk'>이메일 인증 확인</a>").toString());
	sendMail.setFrom("johnmor78@gmail.com", "찐따킹");
	sendMail.setTo(dto.getEmail().trim());
	sendMail.send();
	}
	
	public void userAuth(String userEmail) throws Exception {
		dao.userAuth(userEmail);
	}
	
	public String selectUserAuth(String userEmail) throws Exception {
		return dao.selectUserAuth(userEmail);
	}
	
}
