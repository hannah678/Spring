package com.campus.myapp.service;

import java.util.List;

import com.campus.myapp.vo.ReplyVO;

public interface ReplyService {
    
        //´ñ±Û µî·Ï
        public int replyWrite(ReplyVO vo);
        //´ñ±Û ¸ñ·Ï
        public List<ReplyVO> replyList(int no);
        //´ñ±Û ¼öÁ¤
        public int replyEdit(ReplyVO vo);
        //´ñ±Û »èÁ¦
        public int replyDel(int replyno, String userid);

}