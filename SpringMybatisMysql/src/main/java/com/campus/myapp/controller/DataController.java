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
	//�ڷ�� �۾��� ��
	@GetMapping("/data/write")
	public ModelAndView dataWrite() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("data/dataWrite");
		return mav;
	}
	@PostMapping("/data/writeOk")
	public ResponseEntity<String> dataWriteOk(DataVO vo, HttpServletRequest request){
		//vo: subject, content�� request�� ��.
		vo.setUserid((String)request.getSession().getAttribute("logId"));
		
		ResponseEntity<String> entity = null;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("text", "html", Charset.forName("UTF-8")));
		
		//���Ͼ��ε带 ���� ���ε���ġ�� �����ּ�
		String path = request.getSession().getServletContext().getRealPath("/upload");
		System.out.println("path-->"+path);
		
		try {
			//���Ͼ��ε带 ó���ϱ� ���ؼ��� request��ü���� multipart��ü�� ����ȯ�ؾ��Ѵ�.
			MultipartHttpServletRequest mr = (MultipartHttpServletRequest)request;
			
			//mr�� ������ ����ŭ MultipartFile��ü�� �����Ѵ�.
			List<MultipartFile> files = mr.getFiles("filename");
			System.out.println("���ε� ���ϼ� = "+files.size());
			
			if(files!=null) { //if�� 1111
				int cnt=1; //���ε� ������ ���� filename1, filename2 ���ϸ��� �����ϱ����� ����(�ʱⰪ=1)
				//÷������ �� ��ŭ �ݺ��Ͽ� ���ε��Ѵ�.
				for(int i=0; i<files.size(); i++) {//for�� 2222
					//1. MultipartFile��ü ������
					MultipartFile mf = files.get(i);
					
					//2. ���ε��� ���� ���ϸ��� ���ϱ�
					String orgFileName = mf.getOriginalFilename();
					System.out.println("orgFileName->"+orgFileName);
					
					//3. rename�ϱ�
					if(orgFileName!=null && !orgFileName.equals("")) { //if�� 3333
						File f = new File(path, orgFileName);
						
						//������ �����ϴ��� Ȯ�� true:�����Ѵ�/ false: ���������ʴ´�.
						if(f.exists()) { //if�� 4444
							for(int renameNum=1;/*���ѷ���*/;renameNum++) { //for�� 5555 //�̷����ĵ� �ǳ�!
								//Ȯ���ڿ� ������ �и��Ѵ�.
								int point = orgFileName.lastIndexOf("."); //point="."�� �������� ���� ���ϸ�, �ڴ� Ȯ����
								String fileName = orgFileName.substring(0,point);
								String ext = orgFileName.substring(point+1); //Ȯ����extension(ext)
								
								f = new File(path, fileName+"("+renameNum +")."+ext);
								if(!f.exists()) { //���λ����� ���ϰ�ü�� ������
									orgFileName = f.getName();
									break;
								}
							} //for�� 5555
						} //if�� 4444
						
						//4. ���Ͼ��ε� ��
						try {
							mf.transferTo(f); //�������ε尡 �߻���. 
						}catch(Exception ee){
							
						}
						//5. ���ε���(���ο����ϸ�) vo�� ����
						if(cnt==1) vo.setFilename1(orgFileName);
						if(cnt==2) vo.setFilename2(orgFileName);
						cnt++;
					}//if�� 3333
				}//for�� 2222
			}//if�� 1111
			System.out.println(vo.toString());
			
			//DB���
			service.dataInsert(vo);
			//���ڵ� �߰� ����
			String msg = "<script>alert('�ڷ�ǿ� ���� ��ϵǾ����ϴ�.');location.href='/myapp/data/dataList';</script>";
			entity = new ResponseEntity<String>(msg, headers, HttpStatus.OK);
			
		}catch(Exception e) {
			e.printStackTrace();
			//���ڵ��߰� ����
			//������ �����
			fileDelete(path, vo.getFilename1());
			fileDelete(path, vo.getFilename2());
			//�޼���
			String msg = "<script>alert('�ڷ�� �۵���� �����Ͽ����ϴ�.');history.back();</script>";
			//������������ ������
			
			entity = new ResponseEntity<String>(msg, headers, HttpStatus.BAD_REQUEST); //error400
		}
		return entity;
	}
	//��������� �޼ҵ�
	public void fileDelete(String p, String f) {
		if(f!=null) { //���ϸ��� �����ϸ�
			File file = new File(p,f);
			file.delete();
		}
	}
	//�۳��뺸��
	@GetMapping("/data/view")
	public ModelAndView view(int no) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("dataVO", service.dataView(no));
		mav.setViewName("data/dataView");
		return mav;
	}
	//������
	@GetMapping("/data/dataEdit")
	public ModelAndView editForm(int no) {
		ModelAndView mav = new ModelAndView();
		DataVO vo = service.dataView(no);
		//DB�� ÷�ε� ������ ���� ���Ѵ�.
		int fileCount = 1; //ù��° ÷�������� not null
		if(vo.getFilename2()!=null){//�ι�° ÷������ ������ 1 ����
			fileCount++;
		}
		mav.addObject("fileCount", fileCount);
		mav.addObject("vo", vo);
		mav.setViewName("data/dataEdit");
		return mav;
	}
	//����(DB)
	@PostMapping("/data/editOk")
	public ResponseEntity<String> editOk(DataVO vo, HttpSession session){
		vo.setUserid((String)session.getAttribute("logId"));
		String path = session.getServletContext().getRealPath("/upload");
		
		System.out.println(vo.toString());
		if(vo.getDelFile()!=null) {
			for(int k=0; k<vo.getDelFile().length; k++) {
				System.out.println("���������ϸ�: "+vo.getDelFile()[k]);
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
