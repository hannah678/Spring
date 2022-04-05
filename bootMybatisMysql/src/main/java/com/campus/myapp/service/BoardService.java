package com.campus.myapp.service;

import java.util.List;

import com.campus.myapp.vo.BoardVO;

public interface BoardService {
	public List<BoardVO> allSelect();
	public int boardInsert(BoardVO vo);
	public BoardVO boardSelect(int no);
	public int boardUpdate(BoardVO vo);
	public int boardDelete(int no, String userid);
}
