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
	
	@RequestMapping(value="/logout.do", method=RequestMethod.GET)
	public String logout(HttpSession session) {
		session.invalidate(); // 세션을 지운다. -> 로그아웃
		return "redirect:/";
	}
	
	@RequestMapping(value="/login.do", method=RequestMethod.GET)
	public String login() {
		return "member/loginForm";
	}
	
	@RequestMapping(value="/login.do", method=RequestMethod.POST)
	public String login(HttpSession session, String email, String pw) {
		LoginDTO dto = service.login(email, pw);
		if(dto == null) {
			System.out.println("아이디나 비밀번호를 확인하세요.");
			return "member/loginForm";
		}
		// 아이디와 비밀번호가 맞는 경우
		dto.setLogin(1);
		System.out.println(dto.getLogin());
		session.setAttribute("login", dto); // 로그인 처리 -> 세션에 값을 넣는다.
		return "redirect:/";
	}

	@RequestMapping(value="/join.do", method=RequestMethod.GET)
	public String join() {
		return "member/joinForm";
	}

	@RequestMapping(value="/join.do", method=RequestMethod.POST)
	public String join(LoginDTO dto) {
		service.join(dto);
		return "redirect:/";
	}
	
	@RequestMapping(value="list.do", method = RequestMethod.GET)
	public String list(Model model, Criteria cri, LoginDTO dto) {
		if(dto.getGrade() == 9) {
//			model.addAttribute("list", service.list(cri));
					
		}else {
			model.addAttribute("list", "관리자만 접근 가능합니다.");
		}
		
		return "member/list";
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String RegisterPost(LoginDTO dto, Model model, RedirectAttributes rttr, HttpServletRequest request, HttpSession session) throws Exception {
		service.create(dto);
		rttr.addFlashAttribute("authmsg" , "가입시 사용한 이메일로 인증해주세요.");
		return "redirect:/";
	}
	
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
	
}
