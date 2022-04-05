package com.campus.myapp.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public final class LoginInterceptor extends HandlerInterceptorAdapter {
	//컨트롤러가 호출되기 전에 실행될 메소드
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//false: 로그인으로 보내기
		//true: 해당컨트롤러로 이동한다.
		
		//request객체에서 session객체를 얻어오기
		HttpSession session = request.getSession();
		
		//로그인 상태구하기 null
		String logStatus = (String) session.getAttribute("logStatus");
		
		if(logStatus !=null && logStatus.equals("Y")) { //로그인되었을 때
			return true; //가던길 가기
		}else { //로그인 안 된 경우
			//로그인 폼 페이지로 이동
			response.sendRedirect("/member/login");
			return false;
		}
	}
}