package com.campus.myapp.controller;



import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.campus.myapp.service.ReplyService;
import com.campus.myapp.vo.ReplyVO;

@RestController
public class ReplyController {
	@Inject
	ReplyService service;
	
		//댓글등록
		@RequestMapping(value="/reply/writeOk", method=RequestMethod.POST)
		public int writeOk(ReplyVO vo, HttpSession session) {
			//1) DB의 replyBoard에 저장할 userid를 session의 logId값으로 지정(현재 로그인된 아이디)
			vo.setUserid((String)session.getAttribute("logId"));	
			//2) 해당 아이디로, 댓글 등록(sql)
			return service.replyWrite(vo);
		}
		// 댓글 목록
	    @GetMapping("/reply/list")
	    public List<ReplyVO> list(int no) {
	        return service.replyList(no);  // List전체를 구해서 return해준다. (변수에 담아서 보내줄 필요 xx)
	    }
	    
	    // 댓글 수정
	    @PostMapping("/reply/editOk")
	    public int editOk(ReplyVO vo, HttpSession session) {
	    	vo.setUserid((String)session.getAttribute("logId"));
	    	return service.replyEdit(vo);
	    }
	    
	    //댓글 삭제
	    @GetMapping("/reply/del")
	    public int delOk(int replyno, HttpSession session) {
	    	return service.replyDel(replyno, (String)session.getAttribute("logId"));
	    }
	}