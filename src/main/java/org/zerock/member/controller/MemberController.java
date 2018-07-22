package org.zerock.member.controller;

import java.io.UnsupportedEncodingException;
import java.util.Random;

import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

	// 로그아웃을 시켜준다.
	@RequestMapping(value = "/logout.do", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		//세션에 담긴 login 네임으로 dto를 얻어온다.
		LoginDTO dto = (LoginDTO)session.getAttribute("login");
		//로그인 상태를 0으로 만들어 로그아웃 해주기 위해서 email을 전달한다.
		service.logout(dto.getEmail());
		//DB의 로그인 상태를 0으로 만들어준다.
		service.setLoginFalse(dto.getEmail());
		//dto의 로그인 상태를 false로 만들어준다. 
		dto.setLogin(getLoginStatus(dto.getEmail()));
		session.invalidate(); // 세션을 지운다. -> 로그아웃
		return "redirect:/";
	}

	// 로그인 시도 할시에 로그인 폼으로 보내준다.
	@RequestMapping(value = "/login.do", method = RequestMethod.GET)
	public String login() {
		return "member/loginForm";
	}

	// 로그인 폼에서 작성한 정보로 로그인을 시도할 때
	@RequestMapping(value = "/login.do", method = RequestMethod.POST)
	public String login(HttpSession session, String email, String pw, RedirectAttributes rttr) throws Exception {
		//입력한 raw비밀번호와 DB에 들어있는 암호화된 비밀번호가 일치하는 경우 true
		if (bcryptPasswordEncoder.matches(pw, service.selectCryptPw(email))) {
			//dto에 개인 정보를 모두 담아 준다
			LoginDTO dto = service.login(email);
			System.out.println(dto);
			// 데이터 베이스를 이메일로 조회한 후에 유저인증상태 값에 1이 들어있으면 이미 이메일 인증이 완료된 것으로 간주
			if (service.selectUserAuth(email).equals("1")) {
				//유저 인증 상태를 True로 세팅해준다.
				dto.setUser_authStatus(true);
				//유저 로그인 상태도 true로 만들어준다. getLoginStatus함수를 불러와 1또는 0인지 값을 조사한 후에 불린값으로 대입
				dto.setLogin(getLoginStatus(email));
				System.out.println(dto.getLogin());
				session.setAttribute("login", dto); // 로그인 처리 -> 세션에 값을 넣는다.
			} else {
				// 유저 인증상태 값에 0이 들어가 있는 경우 메일 인증을 안한 것으로 간주한다.
				rttr.addFlashAttribute("authmsg", "메일 인증을 해주세요");
				// 메일 인증을 안내하는 페이지로 안내한다.
				return "redirect:/member/authError.do";
			}
		}
		return "redirect:/";
	}

	// 로그인 스테이터스가 0(인증이 안된경우)인경우 인증에러 페이지로 돌아간다.
	@RequestMapping(value = "/authError.do", method = RequestMethod.GET)
	public String authError() {
		return "member/authError";
	}

	// 회원가입 주소를 입력하면 회원가입 폼으로 안내한다.
	@RequestMapping(value = "/join.do", method = RequestMethod.GET)
	public String join() {
		return "member/joinForm";
	}

	// 회원가입 폼에서 자료를 입력해서 들어오는 컨트롤러 메서드, 비밀번호를 암호화한다.
	@RequestMapping(value = "/join.do", method = RequestMethod.POST)
	public String join(Model model, LoginDTO dto, RedirectAttributes rttr, HttpServletRequest request,
			HttpSession session) throws Exception {
		// service.join(dto);
		
		dto.setHp(checkHpFormat(dto.getHp())); // 핸드폰 번호에서 문자를 빼고 숫자만으로 통일시켜준다.
		dto.setPw(this.bcryptPasswordEncoder.encode(dto.getPw())); // 비밀번호를 암호화해서 저장한다.
		service.create(dto); // 서비스로 dto를 보내서 회원가입을 시키고, 인증키 생성 DB에 저장, 메일 발송을 진행한다.
		rttr.addFlashAttribute("authmsg", "가입시 사용한 이메일로 인증해주세요."); //alert를 띄워서 안내
		return "redirect:/";
	}

	//앞으로 구현할 리스트 메서드
	@RequestMapping(value = "list.do", method = RequestMethod.GET)
	public String list(Model model, Criteria cri, LoginDTO dto) {
		if (dto.getGrade() == 9) {
			// model.addAttribute("list", service.list(cri));
		} else {
			model.addAttribute("list", "관리자만 접근 가능합니다.");
		}
		return "member/list";
	}

	// Ajax처리를 위한 컨트롤러 함수. 이메일 중복확인시 사용한다.
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<String> checkEmail(@RequestBody String email) {
		ResponseEntity<String> entity = null;

		String result = service.checkEmail(email); // 이메일이 중복되었는지 검사한다. 이미 사용중이면 이메일을 문자열로 반환한다.
		System.out.println(result);
		try {
			if (result == null) {
				entity = new ResponseEntity<>("empty", HttpStatus.OK); // 문자열이 비어있으면 사용할 수 있는 이메일.
			} else {
				entity = new ResponseEntity<>("exist", HttpStatus.OK); // 아니라면 사용할 수 없는 이메일.
			}
		} catch (Exception e) {
			// TODO: handle exception
			entity = new ResponseEntity<>("error", HttpStatus.BAD_REQUEST); // 에러가 났을 경우.
		}
		return entity;
	}

	// 발송된 이메일을 클릭했을 시에 이쪽 url을 타고 들어와서 emailConfirm.jsp의 안내를 받게 된다.
	@RequestMapping(value = "/emailConfirm", method = RequestMethod.GET)
	public String emailConfirm(String user_email, Model model) throws Exception { // 이메일인증
		service.userAuth(user_email);
		model.addAttribute("user_email", user_email);
		return "/member/emailConfirm";
	}

	// 아이디 비밀번호 찾기 버튼을 누르면, 아이디 비밀번호 찾기 페이지로 안내한다.
	@RequestMapping(value = "/searchForm.do", method = RequestMethod.GET)
	public String searchForm() {
		return "member/searchForm";
	}

	//비밀번호, 아이디 찾기 폼에서 넘어오는 데이타를 받아서 찾아 준다.
	@RequestMapping(value = "/searchForm.do", method = RequestMethod.POST)
	public String searchForm(Model model, String email, String hp, 
			String name, String age) throws UnsupportedEncodingException, MessagingException {
		//만약 아이디 찾기라면 이메일이 null일 것이고 비밀번호 찾기라면 이메일 주소가 있을 것이다.
		if (email == null) {
			String newHp = checkHpFormat(hp);
			String id = "";
			id = service.searchId(newHp, name, age); //입력한 정보로 ID를 찾아준다.
			if (id == null) { //입력한 정보로 찾았는데 아이디가 없을 때 안내문구
				System.out.println("상기 정보로 찾으시는 아이디가 존재하지 않습니다.");
				model.addAttribute("searchIdMsg", "찾으시는 아이디가 존재하지 않습니다.");
			} else { //입력한 정보로 아이디를 찾았을 때
				model.addAttribute("searchId", id);
			}
		} else { //이메일이 null이 아니라면 비밀번호 찾기를 시도한 것이다.
			String checkedEmail = service.checkEmail(email); //중복이메일 검사 함수를 또 사용해서 실제 그런 이메일이 있는지 조사한다.
			System.out.println("체크 이메일 : " + checkedEmail);
			if(checkedEmail != null) { //이메일이 null이 아니라면 진짜 이메일이 존재하는 경우
				String pw = randPw(); //랜덤 비밀번호 만들기 함수를 통해 비밀번호를 생성해준다.
				String newPw = this.bcryptPasswordEncoder.encode(pw); //생성한 비밀번호를 암호화한다
				System.out.println("암호화 비번 : " + newPw);
				service.setPw(newPw, checkedEmail); //생성해서 암호화된 비밀번호를 암호화해서 DB에 저장한다.
				service.sendMail(pw, checkedEmail); //암호화 안된 비밀번호를 메일로 발송해준다
				model.addAttribute("id", "메일로 임시 비밀번호를 발송해 드렸습니다. 확인해주세요");
				return "member/idPwInform";
				
			}else {
				System.out.println("이메일과 일치하는 회원이 없습니다."); //입력한 이메일 정보가 DB에 없을때 
				model.addAttribute("id", "이메일과 일치하는 회원이 없습니다. 새로 가입하세요");
			}
		}
		return "member/idPwInform";
	}

	// 핸드폰 형식을 통일해서 01012341234 처럼 숫자만 있는 형식으로 바꾼다.
	public String checkHpFormat(String hp) {
		String result = hp;
		//hp의 길이가 11자리이면 문자없이 숫자만으로 이루어진 경우, 13자리이면 도중에 '-', '.'이 들어간 경우
		if (hp.length() > 11 && hp.length() <= 13) {
			//11보다 긴 hp이면 3번째 문자 (즉, '-'나 '.'등)을 reg에 저장한다.
			String reg = String.valueOf(result.charAt(3)); 
			//reg문자를 모두 제거한다
			result = result.replaceAll(reg, "");
		} else {
			//제이쿼리 정규식을 벗어난 핸드폰 번호가 맞게 들어온경우, 정규식 디버깅이 필요
			System.out.println("핸드폰 형식이 안맞습니다. 디버깅하세요.");
		}
		return result;
	}
	
	//비밀번호 찾기를 눌렀을 때 무작위 비밀번호를 생성해서 전달해주는 함수(컨트롤러에서만 사용하므로 private)
	private String randPw() {
		String result = ""; 
		Random rand = new Random();
		for(int i = 0; i < 13; i++) { //임의로 13자리의 비밀번호를 생성하기 위한 for문
			//0~25 (알파벳 갯수) + 65 (아스키코드 65번부터 영어 대문자시작) =>A~Z까지 한 문자씩 임의로 생성
			 char ch = (char) (rand.nextInt(25) + 65); 			 
			 //결과에 13자리 문자를 더해준다.
			 result +=  String.valueOf(ch);
		}
		System.out.println(result);
		return result;
	}
	
	//로그인 상태를 조사해서 1이면 true 그외의 숫자면 false를 반환한다. dto의 boolean login변수 값을 설정하는데 사용
	private boolean getLoginStatus(String email) {
		if(service.getLogin(email) == 1) {
			return true;
		}else {
			return false;
		}
	}
	
	//메인화면에서 회원 이름을 클릭했을 때 회원 정보를 볼 수 있는 페이지로 이동시키는 함수
	@RequestMapping(value="/profile.do", method=RequestMethod.GET)
	public String profile(Model model) {
		
		return "member/profile";
	}
	
	

}
