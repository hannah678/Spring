package com.campus.myapp.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public final class LoginInterceptor extends HandlerInterceptorAdapter {
	//��Ʈ�ѷ��� ȣ��Ǳ� ���� ����� �޼ҵ�
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//false: �α������� ������
		//true: �ش���Ʈ�ѷ��� �̵��Ѵ�.
		
		//request��ü���� session��ü�� ������
		HttpSession session = request.getSession();
		
		//�α��� ���±��ϱ� null
		String logStatus = (String) session.getAttribute("logStatus");
		
		if(logStatus !=null && logStatus.equals("Y")) { //�α��εǾ��� ��
			return true; //������ ����
		}else { //�α��� �� �� ���
			//�α��� �� �������� �̵�
			response.sendRedirect(request.getContextPath()+"/member/loginForm");
			return false;
		}
	}
}
