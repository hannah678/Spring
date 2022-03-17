package com.campus.myapp.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
//ȸ������, ȸ����������, �α���, �α׾ƿ�
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.campus.myapp.service.MemberService;
import com.campus.myapp.vo.MemberVO;

@Controller
@RequestMapping("/member/")
public class MemberController {
	@Inject
	MemberService service;
	
	// ȸ������������ �̵��ϴ� ����
	@GetMapping("memberForm")
	public String memberForm() {
		return "member/memberForm"; // member������ memberForm.jsp������ ��� ����Ѵ�.
	}
	
	
	// ȸ�����
	// @RequestMapping(value="/member/memberOk",method=RequestMethod.POST)
	@PostMapping("memberOk")
	public String memberFormOk(MemberVO vo, Model model) {
		// ȸ�����
		int cnt = service.memberInsert(vo);
		
		// Ŭ���̾�Ʈ �������� insert ����� ������.
		model.addAttribute("cnt",cnt);
		return "member/memberResult";
	}
	
	// �α��������� �̵�
	@GetMapping("loginForm")
	public String loginForm() {
		return "member/loginForm";
	}
	
	// �α���
	@PostMapping("loginOk")
	public ModelAndView loginOk(MemberVO vo, HttpSession session) {
		// request userid, userpwd�� ��ġ�ϴ� ���ڵ��� userid, username�� �����Ѵ�.
		MemberVO vo2 = service.loginCheck(vo);
		
		ModelAndView mav = new ModelAndView();
		if(vo2!=null){
			// �α��� ���� : session�� ���̵�� �̸��� �����Ѵ�. > Ȩ���� �̵�
			session.setAttribute("logId",vo2.getUserid());
			session.setAttribute("logName", vo2.getUsername());
			session.setAttribute("logStatus","Y");
			// ��Ʈ�ѷ����� �ٸ� ��Ʈ�ѷ� �����ּҸ� �ٷ� ȣ���Ѵ�.
			mav.setViewName("redirect:/");
		}else {
			// �α��� ���� : �α��� ������ �̵�
			mav.setViewName("redirect:loginForm");
		}
		return mav;
	}
	
	// �α׾ƿ�
	@GetMapping("logout")
	public ModelAndView logout(HttpSession session) {
		// ���� ��ü�� ����� ���ǿ� ����� �α������� �� ��絥���Ͱ� �����ǰ�
		// ���ο� ������ �Ҵ��Ѵ�.
		session.invalidate();
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/");
		return mav;
	}
	
	// ȸ���������� (��)
	@GetMapping("memberEdit")
	public ModelAndView memberEdit(HttpSession session) {
		String userid = (String)session.getAttribute("logId");
		
		MemberVO vo = service.memberSelect(userid);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("vo",vo);
		
		mav.setViewName("member/memberEdit");
		return mav;
	}
	
	// ȸ���������� (DB)
	@PostMapping("memberEditOk")
	public ModelAndView memberEditOk(MemberVO vo, HttpSession session) {
		// session �α��� ���̵� 
		vo.setUserid((String)session.getAttribute("logId"));
		
		service.memberUpdate(vo);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:memberEdit");
		return mav;
	}
	
	// ���̵� �ߺ��˻�
	@PostMapping("memberIdCheck")
	@ResponseBody
	public int idCheck(String userid) {
		System.out.println(userid);
		int cnt = service.idCheck(userid);
		System.out.println(cnt);
		return cnt;
	}
}
