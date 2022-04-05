package com.campus.myapp.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.campus.myapp.service.MemberService;
import com.campus.myapp.vo.MemberVO;

@RestController
public class MemberController {
	@Inject //서비스 객체 만들기
	MemberService service;
	
	@GetMapping("/member/login")
	public ModelAndView login() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("member/loginForm");
		return mav;
	}
	
	@PostMapping("/member/loginOk")
	public ResponseEntity<String> loginOk(MemberVO vo, HttpSession session){
		ResponseEntity<String> entity = null;
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type","text/html; charset=utf-8");
		
		//예외처리
		try {
			MemberVO rVo = service.login(vo);
			if(rVo !=null) { //로그인 성공
				session.setAttribute("logId", rVo.getUserid());
				session.setAttribute("logName", rVo.getUsername());
				session.setAttribute("logStatus", "Y");
				
				String msg = "<script>location.href='/';</script>";
				entity = new ResponseEntity<String>(msg, headers, HttpStatus.OK);
		
			}else { //로그인 실패
				throw new Exception(); //예외처리로 보내기
			}
		}catch(Exception e) {
			e.printStackTrace();
			//로그인 실패
			//다시 로그인폼으로 보내기
			String msg="<script>alert('로그인 실패하였습니다.'); history.go(-1);</script>";
			entity = new ResponseEntity<String>(msg, headers, HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	@GetMapping("/member/logout")
	public ModelAndView logout(HttpSession session) {
		//세션제거 = 로그아웃
		//새로운 세션이 할당된다.
		session.invalidate();
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/");
		return mav;
	}
}
