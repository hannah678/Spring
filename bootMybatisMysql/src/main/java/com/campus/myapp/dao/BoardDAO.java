package com.campus.myapp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.campus.myapp.vo.BoardVO;

@Mapper
@Repository
public interface BoardDAO {
	public List<BoardVO> allSelect();
	public int boardInsert(BoardVO vo);
	public BoardVO boardSelect(int no);
	public int boardUpdate(BoardVO vo);
	public int boardDelete(int no, String userid);
}
