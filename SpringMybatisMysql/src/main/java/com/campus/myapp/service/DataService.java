package com.campus.myapp.service;

import java.util.List;

import com.campus.myapp.vo.DataVO;

public interface DataService {

	public int dataInsert(DataVO vo);
	List<DataVO> dataSelectAll();
	public DataVO dataView(int no);
	public DataVO getFileName(int no);
	public int dataUpdate(DataVO vo);
	public int dataDelete(int no, String userid);

}