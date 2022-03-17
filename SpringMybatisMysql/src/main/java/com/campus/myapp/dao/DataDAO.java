package com.campus.myapp.dao;

import java.util.List;

import com.campus.myapp.vo.DataVO;

public interface DataDAO {
	public int dataInsert(DataVO vo);
	public List<DataVO> dataSelectAll();
	public DataVO dataView(int no);
}
