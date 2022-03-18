package com.campus.myapp.service;

import com.campus.myapp.vo.MemberVO;

public interface MemberService {
	// 회원등록
	int memberInsert(MemberVO vo);
	public MemberVO loginCheck(MemberVO vo);
	public MemberVO memberSelect(String userid);
	public int memberUpdate(MemberVO vo);
	int idCheck(String userid);

}
