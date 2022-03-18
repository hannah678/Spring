package com.campus.myapp.service;

import java.util.List;

import com.campus.myapp.vo.ReplyVO;

public interface ReplyService {
    
        //��� ���
        public int replyWrite(ReplyVO vo);
        //��� ���
        public List<ReplyVO> replyList(int no);
        //��� ����
        public int replyEdit(ReplyVO vo);
        //��� ����
        public int replyDel(int replyno, String userid);

}