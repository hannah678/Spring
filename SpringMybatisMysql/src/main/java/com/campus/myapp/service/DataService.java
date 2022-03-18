package com.campus.myapp.service;

import java.util.List;

import com.campus.myapp.vo.DataVO;

public interface DataService {
	public int dataInsert(DataVO vo);
	public List<DataVO> dataSelectAll();
	public DataVO dataView(int no);
}
