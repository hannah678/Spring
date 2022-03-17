package com.campus.myapp.controller;

import java.io.File;
import java.nio.charset.Charset;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.campus.myapp.service.DataService;
import com.campus.myapp.vo.DataVO;

@RestController
public class DataController {
	@Autowired
	DataService service;
	
	@GetMapping("/data/dataList")
	public ModelAndView dataList() {
		ModelAndView mav = new ModelAndView();
		
		mav.addObject("lst", service.dataSelectAll());
		
		mav.setViewName("data/dataList");
		return mav;
	}
	//자료실 글쓰기 폼
	@GetMapping("/data/write")
	public ModelAndView dataWrite() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("data/dataWrite");
		return mav;
	}
	@PostMapping("/data/writeOk")
	public ResponseEntity<String> dataWriteOk(DataVO vo, HttpServletRequest request){
		//vo: subject, content는 request가 됨.
		vo.setUserid((String)request.getSession().getAttribute("logId"));
		
		ResponseEntity<String> entity = null;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("text", "html", Charset.forName("UTF-8")));
		
		//파일업로드를 위한 업로드위치의 절대주소
		String path = request.getSession().getServletContext().getRealPath("/upload");
		System.out.println("path-->"+path);
		
		try {
			//파일업로드를 처리하기 위해서는 request객체에서 multipart객체로 형변환해야한다.
			MultipartHttpServletRequest mr = (MultipartHttpServletRequest)request;
			
			//mr에 파일의 수만큼 MultipartFile객체가 존재한다.
			List<MultipartFile> files = mr.getFiles("filename");
			System.out.println("업로드 파일수 = "+files.size());
			
			if(files!=null) { //if문 1111
				int cnt=1; //업로드 순서에 따라 filename1, filename2 파일명을 대입하기위한 변수(초기값=1)
				//첨부파일 수 만큼 반복하여 업로드한다.
				for(int i=0; i<files.size(); i++) {//for문 2222
					//1. MultipartFile객체 얻어오기
					MultipartFile mf = files.get(i);
					
					//2. 업로드한 실제 파일명을 구하기
					String orgFileName = mf.getOriginalFilename();
					System.out.println("orgFileName->"+orgFileName);
					
					//3. rename하기
					if(orgFileName!=null && !orgFileName.equals("")) { //if문 3333
						File f = new File(path, orgFileName);
						
						//파일이 존재하는지 확인 true:존재한다/ false: 존재하지않는다.
						if(f.exists()) { //if문 4444
							for(int renameNum=1;/*무한루프*/;renameNum++) { //for문 5555 //이런형식도 되네!
								//확장자와 파일을 분리한다.
								int point = orgFileName.lastIndexOf("."); //point="."을 기준으로 앞은 파일명, 뒤는 확장자
								String fileName = orgFileName.substring(0,point);
								String ext = orgFileName.substring(point+1); //확장자extension(ext)
								
								f = new File(path, fileName+"("+renameNum +")."+ext);
								if(!f.exists()) { //새로생성된 파일객체가 없으면
									orgFileName = f.getName();
									break;
								}
							} //for문 5555
						} //if문 4444
						
						//4. 파일업로드 함
						try {
							mf.transferTo(f); //실제업로드가 발생함. 
						}catch(Exception ee){
							
						}
						//5. 업로드한(새로운파일명) vo에 세팅
						if(cnt==1) vo.setFilename1(orgFileName);
						if(cnt==2) vo.setFilename2(orgFileName);
						cnt++;
					}//if문 3333
				}//for문 2222
			}//if문 1111
			System.out.println(vo.toString());
			
			//DB등록
			service.dataInsert(vo);
			//레코드 추가 성공
			String msg = "<script>alert('자료실에 글이 등록되었습니다.');location.href='/myapp/data/dataList';</script>";
			entity = new ResponseEntity<String>(msg, headers, HttpStatus.OK);
			
		}catch(Exception e) {
			e.printStackTrace();
			//레코드추가 실패
			//파일을 지우기
			fileDelete(path, vo.getFilename1());
			fileDelete(path, vo.getFilename2());
			//메세지
			String msg = "<script>alert('자료실 글등록을 실패하였습니다.');history.back();</script>";
			//이전페이지로 보내기
			
			entity = new ResponseEntity<String>(msg, headers, HttpStatus.BAD_REQUEST); //error400
		}
		return entity;
	}
	//파일지우기 메소드
	public void fileDelete(String p, String f) {
		if(f!=null) { //파일명이 존재하면
			File file = new File(p,f);
			file.delete();
		}
	}
	//글내용보기
	@GetMapping("/data/view")
	public ModelAndView view(int no) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("dataVO", service.dataView(no));
		mav.setViewName("data/dataView");
		return mav;
	}
	//수정폼
	@GetMapping("/data/dataEdit")
	public ModelAndView editForm(int no) {
		ModelAndView mav = new ModelAndView();
		DataVO vo = service.dataView(no);
		//DB에 첨부된 파일의 수를 구한다.
		int fileCount = 1; //첫번째 첨부파일은 not null
		if(vo.getFilename2()!=null){//두번째 첨부파일 있으면 1 증가
			fileCount++;
		}
		mav.addObject("fileCount", fileCount);
		mav.addObject("vo", vo);
		mav.setViewName("data/dataEdit");
		return mav;
	}
	//수정(DB)
	@PostMapping("/data/editOk")
	public ResponseEntity<String> editOk(DataVO vo, HttpSession session){
		vo.setUserid((String)session.getAttribute("logId"));
		String path = session.getServletContext().getRealPath("/upload");
		
		System.out.println(vo.toString());
		if(vo.getDelFile()!=null) {
			for(int k=0; k<vo.getDelFile().length; k++) {
				System.out.println("삭제된파일명: "+vo.getDelFile()[k]);
			}
		}
		
		ResponseEntity<String> entity = null;
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "text/html; charset=utf-8");
		try {
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return entity;
	}
}
