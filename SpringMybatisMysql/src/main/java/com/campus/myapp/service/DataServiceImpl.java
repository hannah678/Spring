package com.campus.myapp.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.campus.myapp.dao.DataDAO;
import com.campus.myapp.vo.DataVO;

@Service
public class DataServiceImpl implements DataService{
	@Inject
	DataDAO dao;
	
	@Override
	public int dataInsert(DataVO vo) {
		return dao.dataInsert(vo);
	}

	@Override
	public List<DataVO> dataSelectAll() {
		return dao.dataSelectAll();
	}

	@Override
	public DataVO dataView(int no) {
		return dao.dataView(no);
	}

}
