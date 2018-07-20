package org.zerock.member.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.member.dto.Criteria;
import org.zerock.member.dto.LoginDTO;
import org.zerock.member.service.MemberService;

@Controller
@RequestMapping("/member")
public class MemberController {

	@Inject
	private MemberService service;
	
	@Inject
	private BCryptPasswordEncoder bcryptPasswordEncoder;
	
	//로그아웃을 시켜준다.
	@RequestMapping(value="/logout.do", method=RequestMethod.GET)
	public String logout(HttpSession session) {
		session.invalidate(); // 세션을 지운다. -> 로그아웃
		return "redirect:/";
	}
	
	//로그인 시도 할시에 로그인 폼으로 보내준다.
	@RequestMapping(value="/login.do", method=RequestMethod.GET)
	public String login() {
		return "member/loginForm";
	}
	
	//로그인 폼에서 작성한 정보로 로그인을 시도할 때
	@RequestMapping(value="/login.do", method=RequestMethod.POST)
	public String login(HttpSession session, String email, String pw, RedirectAttributes rttr) throws Exception {
		
		if(bcryptPasswordEncoder.matches(pw, service.selectCryptPw(email))) {
			LoginDTO dto = service.login(email);
			//데이터 베이스를 이메일로 조회한 후에 로그인 스테이터스 값에 1이 들어있으면 인증이 완료된 것으로 간주
			if(service.selectUserAuth(email).equals("1")) {
				dto.setUser_authStatus(true);
				// 아이디와 비밀번호가 맞는 경우
				dto.setLogin(true);
				System.out.println(dto.getLogin());
				session.setAttribute("login", dto); // 로그인 처리 -> 세션에 값을 넣는다.
				session.setAttribute("authmsg", "이메일 인증을 해주세요!");
			}else {
				// 로그인 스테이터스에 0이 들어가 있는 경우
				rttr.addFlashAttribute("authmsg", "메일 인증을 해주세요");
				return "redirect:/member/authError.do";
			}
		}
		return "redirect:/";
	}
	
	// 로그인 스테이터스가 0(인증이 안된경우)인경우 인증에러 페이지로 돌아간다.
	@RequestMapping(value="/authError.do", method=RequestMethod.GET)
	public String authError() {
		return "member/authError";
	}

	//회원가입 주소를 입력하면 회원가입 폼으로 안내한다.
	@RequestMapping(value="/join.do", method=RequestMethod.GET)
	public String join() {
		return "member/joinForm";
	}

	//회원가입 폼에서 자료를 입력해서 들어오는 컨트롤러 메서드
	@RequestMapping(value="/join.do", method=RequestMethod.POST)
	public String join(Model model, LoginDTO dto, RedirectAttributes rttr, HttpServletRequest request, HttpSession session) throws Exception {
//		service.join(dto);
		dto.setPw(this.bcryptPasswordEncoder.encode(dto.getPw()));
		service.create(dto); //서비스로 dto를 보내서 회원가입을 시키고, 인증시 생성, 메일 발송을 진행한다.
		rttr.addFlashAttribute("authmsg" , "가입시 사용한 이메일로 인증해주세요.");
		return "redirect:/";
	}
	
	@RequestMapping(value="list.do", method = RequestMethod.GET)
	public String list(Model model, Criteria cri, LoginDTO dto ) {
		if(dto.getGrade() == 9) {
//			model.addAttribute("list", service.list(cri));
					
		}else {
			model.addAttribute("list", "관리자만 접근 가능합니다.");
		}
		
		return "member/list";
	}
	
//	@RequestMapping(value = "/join.do", method = RequestMethod.POST)
//	public String RegisterPost(LoginDTO dto, Model model, RedirectAttributes rttr, HttpServletRequest request, HttpSession session) throws Exception {
//		service.create(dto);
//		rttr.addFlashAttribute("authmsg" , "가입시 사용한 이메일로 인증해주세요.");
//		return "redirect:/";
//	}
	
	//Ajax처리를 위한 컨트롤러 함수. 이메일 중복확인시 사용한다.
	@RequestMapping(value="/", method=RequestMethod.POST)
	public ResponseEntity<String> checkEmail(@RequestBody String email){
		ResponseEntity<String> entity = null;
		
		String result = service.checkEmail(email); //이메일이 중복되었는지 검사한다. 이미 사용중이면 이메일을 문자열로 반환한다.
		System.out.println(result);
		try {
			if(result == null) {
				entity = new ResponseEntity<>("empty", HttpStatus.OK); //문자열이 비어있으면 사용할 수 있는 이메일.
			}else {
				entity = new ResponseEntity<>("exist", HttpStatus.OK); //아니라면 사용할 수 없는 이메일.
			}
			
		}catch (Exception e) {
			// TODO: handle exception
			entity = new ResponseEntity<>("error", HttpStatus.BAD_REQUEST); //에러가 났을 경우.
		}
		return entity;
	}
	
	//발송된 이메일을 클릭했을 시에 이쪽 url을 타고 들어와서 emailConfirm.jsp의 안내를 받게 된다.
	@RequestMapping(value = "/emailConfirm", method = RequestMethod.GET)
	public String emailConfirm(String user_email, Model model) throws Exception { // 이메일인증
		service.userAuth(user_email);
		model.addAttribute("user_email", user_email);

		return "/member/emailConfirm";
	}
}
