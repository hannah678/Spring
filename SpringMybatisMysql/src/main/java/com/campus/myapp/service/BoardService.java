package com.campus.myapp.service;

import java.util.List;

import com.campus.myapp.vo.BoardVO;
import com.campus.myapp.vo.PagingVO;

public interface BoardService {
	//±Ûµî·Ï
	public int boardInsert(BoardVO vo);
	
	public List<BoardVO> boardList(PagingVO pVO);

	public int totalRecord(PagingVO pVO);
	
	public BoardVO boardSelect(int no);
	
	public void hitCount(int no);
	
	public int boardUpdate(BoardVO vo);
	
	public int boardDelete(int no, String userid);
}