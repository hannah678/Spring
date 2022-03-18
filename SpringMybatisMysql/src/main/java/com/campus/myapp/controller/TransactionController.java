package com.campus.myapp.controller;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.campus.myapp.service.BoardService;
import com.campus.myapp.vo.BoardVO;

@Controller
public class TransactionController {
	@Inject
	BoardService service;
	// root-context.xml에 있는 transaction객체를 가져오기
	@Autowired
	private DataSourceTransactionManager transactionManager;
	
	ModelAndView mav;
	
	@RequestMapping("/board/boardTran")
	@Transactional(rollbackFor = {Exception.class, RuntimeException.class})
	public ModelAndView transactionTest() {
		// 트랜잭션처리를 하기 위해서 객체를 생성한다.
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(DefaultTransactionDefinition.PROPAGATION_REQUIRED);
		
		TransactionStatus status = transactionManager.getTransaction(def);
		
		mav = new ModelAndView();
		
		try {
			BoardVO vo1 = new BoardVO();
			vo1.setSubject("aaaa66");
			vo1.setContent("bbbb");
			vo1.setUserid("hyeon330");
			vo1.setIp("192.168.1.3");
			service.boardInsert(vo1);
			
			BoardVO vo2 = new BoardVO();
			vo2.setSubject("aaaa77");
			vo2.setContent("bbbb");
			vo2.setUserid("hyeon330");
			vo2.setIp("192.168.1.3");
			service.boardInsert(vo2);
			
			transactionManager.commit(status);
		} catch (Exception e) {
			e.printStackTrace();
			transactionManager.rollback(status);
			System.out.println("Transaction Error..");
		}
		
		mav.setViewName("redirect:boardList");
		
		return mav;
	}
}