package com.campus.myapp.dao;

import java.util.List;

import com.campus.myapp.vo.ReplyVO;

public interface ReplyDAO {
	//´ñ±Ûµî·Ï
	public int replyWrite(ReplyVO vo);
	//´ñ±Û¸ñ·Ï
	public List<ReplyVO> replyList(int no);
	//´ñ±Û¼öÁ¤
	public int replyEdit(ReplyVO vo); //¹øÈ£, ¾ÆÀÌµð, ÄÚ¸àÆ® µî ´Ù ÇÊ¿ä ->VO
	//´ñ±Û»èÁ¦
	public int replyDel(int replyno, String userid);
}
