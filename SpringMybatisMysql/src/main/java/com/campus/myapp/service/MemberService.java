package com.campus.myapp.service;

import com.campus.myapp.vo.MemberVO;

public interface MemberService {
	// ȸ�����
	int memberInsert(MemberVO vo);
	public MemberVO loginCheck(MemberVO vo);
	public MemberVO memberSelect(String userid);
	public int memberUpdate(MemberVO vo);
	int idCheck(String userid);

}
