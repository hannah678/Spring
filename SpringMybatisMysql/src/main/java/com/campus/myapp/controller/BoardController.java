package com.campus.myapp.controller;

import java.nio.charset.Charset;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.campus.myapp.service.BoardService;
import com.campus.myapp.vo.BoardVO;
import com.campus.myapp.vo.PagingVO;

@RestController		//Controller + @ResponseBody
@RequestMapping("/board/*") //�ƽ�Ʈ��('*')�� �ٿ��� ��θ� ��Ī�ص� �ȴ�.
public class BoardController {
	
	//	/board/boardList
	@Inject
	BoardService service; //��Ʈ�ѷ��� ���������� �ƴ϶� ���� ��������
	//�۸��
	@GetMapping("boardList")
	public ModelAndView boardList(PagingVO pVO) {
		System.out.println(pVO.getSearchWord());
		
		ModelAndView mav = new ModelAndView();
		
		//�ѷ��ڵ��
		pVO.setTotalRecord(service.totalRecord(pVO));
		
		//DBó��
		mav.addObject("list", service.boardList(pVO));
		mav.addObject("pVO", pVO);
		
		mav.setViewName("board/boardList"); // WEB-INF/views/board/boardList.jsp
		return mav;
		
	}
	//�۵�� ��***************************************************
	@GetMapping("boardWrite")
	public ModelAndView boardWrite() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("board/boardWrite");
		return mav;
	}
	
	//�۵��***************************************************
	@PostMapping("boardWriteOk")
	public ResponseEntity boardWriteOk(BoardVO vo, HttpServletRequest request) {
		vo.setIp(request.getRemoteAddr()); //������ ������
		//�۾���.session�α��� ���̵� ���Ѵ�.
		vo.setUserid((String)request.getSession().getAttribute("logId"));
		
		ResponseEntity<String> entity = null; //�����Ϳ� ó�����¸� ������.
		HttpHeaders headers = new HttpHeaders();
		
		/*
		
		headers.add("Content-Type", "text/html; charset=utf-8");
		headers.setContentType(new MediaType("text", "html", Charset.forName("UTF-8")));
		���� ���߿� �ϳ��� ��󾲸� �ȴ�.
		*/
		headers.add("Content-Type", "text/html; charset=utf-8");
		
		try {
			service.boardInsert(vo);
			//������
			String 	msg  = "<script>";
					msg += "alert('���� ��ϵǾ����ϴ�.');";
					msg += "location.href='/myapp/board/boardList';";
					msg += "</script>";
			entity = new ResponseEntity<String>(msg, headers, HttpStatus.OK); // 200
			
		}catch(Exception e) {
			e.printStackTrace();
			//��Ͼȵ�..
			String  msg  = "<script>";
					msg += "alert('�� ��� �����Ͽ����ϴ�');";
					msg += "history.back();";
					msg += "</script>";	
			entity = new ResponseEntity<String>(msg, headers, HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	//�۳��� ����
	@GetMapping("boardView")
	public ModelAndView boardView(int no) {
		ModelAndView mav = new ModelAndView();
		
		service.hitCount(no); //��ȸ�� ����
		
		mav.addObject("vo", service.boardSelect(no));
		mav.setViewName("board/boardView");
		
		return mav;
	}
	//�ۼ��� ��
	// �ۼ��� ��
    @GetMapping("boardEdit")
    public ModelAndView boardEdit(int no) {
        ModelAndView mav = new ModelAndView();
        
        mav.addObject("vo", service.boardSelect(no));
        mav.setViewName("board/boardEdit");
        
        return mav;
    }
	//�ۼ��� (DB)
		@PostMapping("boardEditOk")
		public  ResponseEntity<String> boardEditOk(BoardVO vo, HttpSession session) {
			ResponseEntity<String> entity = null;
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(new MediaType("text", "html", Charset.forName("UTF-8")));
			
			vo.setUserid((String)session.getAttribute("logId"));
			try {
				int result = service.boardUpdate(vo);
				if(result>0){//��������
					entity = new ResponseEntity<String>(getEditSuccessMessage(vo.getNo()), headers, HttpStatus.OK);
				}else {//��������
					entity = new ResponseEntity<String>(getEditFailMessage(), headers, HttpStatus.BAD_REQUEST);
				}
				
			}catch(Exception e) {//��������
				e.printStackTrace();
				entity = new ResponseEntity<String>(getEditFailMessage(), headers, HttpStatus.BAD_REQUEST);
			}
			return entity;
		}
		//�ۻ���
		@GetMapping("boardDel")
		public ModelAndView boardDel(int no, HttpSession session) {
			String userid = (String)session.getAttribute("logId");
			
			int result = service.boardDelete(no, userid);
			
			ModelAndView mav = new ModelAndView();
			if(result>0) { //����
				mav.setViewName("redirect:boardList");
			} else { //�����ȵ�
				mav.addObject("no", no);
				mav.setViewName("redirect:boardView");
			}
			return mav;
		}
		//�ۼ��� �޼��� ���� �޼ҵ�
		public String getEditFailMessage() {
			String msg="<script>";
			msg += "alert('�ۼ��� �����߽��ϴ�.\\n���������� �̵��մϴ�.');";
			msg += "history.back();";
			msg += "</script>";
			return msg;
		}
		public String getEditSuccessMessage(int no) {
			String msg="<script>";
			msg += "alert('���� �����Ǿ����ϴ�.\\n�� ���� �������� �̵��մϴ�.');";
			msg += "location.href='/myapp/board/boardView?no="+no+"'";
			msg += "</script>";
			return msg;
		}
}




