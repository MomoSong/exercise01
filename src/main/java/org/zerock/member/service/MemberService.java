package org.zerock.member.service;

import java.io.UnsupportedEncodingException;

import javax.inject.Inject;
import javax.mail.MessagingException;

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
	
	//로그인을 하기위해 비밀번호와 아이디가 맞는지 조사하는 함수 DAO로 보내서 조사한다.
	public LoginDTO login(String email){
		return dao.login(email);
	}

	//로그아웃을 하기위해 로그인 상태를 0으로 표시해주는 함수
	public void logout(String email) {
		dao.logout(email);
	}
	
	//로그인 상태를 불러와 넘겨주는 함수, 로그인 상태는 1혹은 0
	public int getLogin(String email) {
		return dao.getLogin(email);
	}
	
	//로그인 상태를 1로 만들어주는 함수, 1==true로 사용
	public void setLoginTrue(String email) {
		dao.setLoginTrue(email);
	}
	
	//로그인 상태를 0으로 만들어주는 함수, 0==false로 사용
	public void setLoginFalse(String email) {
		dao.setLoginFalse(email);
	}
	
	// 이메일 중복확인할때 DB에 존재하는 이메일인지 검사하는 함수. 메일이 있으면 메일을 반환한다. dao로 보내서 검사
	public String checkEmail(String email) {
		return dao.checkEmail(email);
	}
	
	//트랜잭션을 이용해 병행제어를 한다. 병행제어를 하는 이유는 연쇄적인 복귀나 모순성 갱신내용 손실을 막기 위함이다.
	@Transactional
	public void create(LoginDTO dto) throws Exception {
	dao.join(dto); // 회원가입 DAO로 dto를 보내서 처리한다.

	String key = new TempKey().getKey(50, false); // 인증키 생성

	dao.createAuthKey(dto.getEmail(), key); // 인증키 DB저장

	//인증 메일을 보내는 함수
	MailHandler sendMail = new MailHandler(mailSender); 
	sendMail.setSubject("[부동산 닷컴 이메일 서비스]");
	sendMail.setText(
			new StringBuffer().append("<h1>메일인증</h1>").append("<a href='http://localhost/member/emailConfirm?user_email=").append(dto.getEmail()).append("&key=").append(key).append("' target='_blenk'>이메일 인증 확인</a>").toString());
	sendMail.setFrom("johnmor78@gmail.com", "부동산 닷컴");
	sendMail.setTo(dto.getEmail().trim());
	sendMail.send();
	}
	
	//새로운 비밀번호를 보내주는 메일보내기 함수
	public void sendMail(String pw, String email) throws MessagingException, UnsupportedEncodingException {
		MailHandler sendMail = new MailHandler(mailSender); 
		sendMail.setSubject("[부동산 닷컴 이메일 서비스]");
		sendMail.setText(
				new StringBuffer().append("<h1>신규 비밀번호</h1>").append("<h3>신규 비밀번호가 설정되었습니다.  </h3>").append("<p>" + pw + "</p>").toString());
		sendMail.setFrom("johnmor78@gmail.com", "부동산 닷컴");
		sendMail.setTo(email.trim());
		sendMail.send();
	}
	
	//유저의 이메일에 일치하는 DB 레코드를 조회하여 user_authStatus를 1로 업데이트 한다.
	public void userAuth(String userEmail) throws Exception {
		dao.userAuth(userEmail);
	}
	
	//유저의 인증상태가 1인지 0인지 조사하기 위해 DB에서 user_authStatus를 조회한다.
	public String selectUserAuth(String userEmail) throws Exception {
		return dao.selectUserAuth(userEmail);
	}
	
	//암호화된 비밀번호를 조회해서 오는 메서드. 로그인 할 때 쓰인다.
	public String selectCryptPw(String email) {
		return dao.selectCryptPw(email);
	}

	// 아이디 찾기에 사용될 service폼
	public String searchId(String newHp, String name, String age) {
		return dao.searchId(newHp, name, age);
	}

	//비밀번호 찾기할때 새로운 비밀번호를 저장하는 함수
	public void setPw(String pw, String email) {
		dao.setPw(pw, email);
	}
}
