package org.zerock.member.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
		//데이터 베이스에 아이디와 비밀번호를 조회한 후에 null 값이 나오면 아이디나 비번 매칭이 안된 것으로 간주
		LoginDTO dto = service.login(email, pw);
		
		if(dto == null) {
			System.out.println("아이디나 비밀번호를 확인하세요.");
			return "member/loginForm";
		}
		
		System.out.println(dto.isUser_authStatus());
		System.out.println("서비스 셀렉트 유저 이메일 결과 : " + service.selectUserAuth(email));
		//데이터 베이스를 이메일로 조회한 후에 로그인 스테이터스 값에 1이 들어있으면 인증이 완료된 것으로 간주
		if(service.selectUserAuth(email).equals("1")) {
			dto.setUser_authStatus(true);
			// 아이디와 비밀번호가 맞는 경우
			dto.setLogin(true);
			System.out.println(dto.getLogin());
			session.setAttribute("login", dto); // 로그인 처리 -> 세션에 값을 넣는다.
			session.setAttribute("authMsg", "이메일 인증을 해주세요!");
		}else {
			// 로그인 스테이터스에 0이 들어가 있는 경우
			rttr.addFlashAttribute("msg", "메일 인증을 해주세요");
			return "redirect:/member/authError.do";
		}
		return "redirect:/";
	}
	
	// 로그인 스테이터스가 0인경우 인증에러 페이지로 돌아간다.
	@RequestMapping(value="/authError.do", method=RequestMethod.GET)
	public String authError() {
		return "member/authError";
	}

	@RequestMapping(value="/join.do", method=RequestMethod.GET)
	public String join() {
		return "member/joinForm";
	}

	@RequestMapping(value="/join.do", method=RequestMethod.POST)
	public String join(Model model, LoginDTO dto, RedirectAttributes rttr, HttpServletRequest request, HttpSession session) throws Exception {
//		service.join(dto);
		service.create(dto);
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
	
	@RequestMapping(value="/", method=RequestMethod.POST)
	public ResponseEntity<String> checkEmail(@RequestBody String email){
		ResponseEntity<String> entity = null;
		
		String result = service.checkEmail(email);
		System.out.println(result);
		try {
			if(result == null) {
				entity = new ResponseEntity<>("empty", HttpStatus.OK);
			}else {
				entity = new ResponseEntity<>("exist", HttpStatus.OK);
			}
			
		}catch (Exception e) {
			// TODO: handle exception
			entity = new ResponseEntity<>("error", HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	@RequestMapping(value = "/emailConfirm", method = RequestMethod.GET)
	public String emailConfirm(String user_email, Model model) throws Exception { // 이메일인증
		service.userAuth(user_email);
		model.addAttribute("user_email", user_email);

		return "/member/emailConfirm";
	}
}
