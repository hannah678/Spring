package com.campus.myapp.vo;

public class PagingVO {
	//1.페이징
		// 1) 한 페이지당 출력할 레코드 수
		private int onePageRecord = 10;
		// 2) 현재 페이지
		private int pageNum = 1;
		// 3) 총 레코드 수 : DB에서 받아와야 함
		private int totalRecord;
		// 4) 총 페이지 수
		private int totalPage;
		private int offsetIndex;
		private int onePageCount = 5;
		private int startPage = 1;

		//2.검색
		private String searchKey;
		private String searchWord;
		
		public int getOnePageRecord() {
			return onePageRecord;
		}
		public void setOnePageRecord(int onePageRecord) {
			this.onePageRecord = onePageRecord;
		}
		public int getPageNum() {
			return pageNum;
		}
		public void setPageNum(int pageNum) {
			this.pageNum = pageNum;
			//offset위치계산
			offsetIndex = (pageNum-1)*onePageRecord;
		}
		public int getTotalRecord() {
			return totalRecord;
		}
		public void setTotalRecord(int totalRecord) {
			this.totalRecord = totalRecord;
			
			//총 페이지수
			if(totalRecord%onePageRecord==0) {
				totalPage = totalRecord/onePageRecord;
			}else {//나머지 레코드가 있을 때
				totalPage = totalRecord/onePageRecord + 1;	
			}
		}
		public int getTotalPage() {
			return totalPage;
		}
		public void setTotalPage(int totalPage) {
			this.totalPage = totalPage;
		}
		public int getOffsetIndex() {
			return offsetIndex;
		}
		public void setOffsetIndex(int offsetIndex) {
			this.offsetIndex = offsetIndex;
		}
		public int getOnePageCount() {
			return onePageCount;
		}
		public void setOnePageCount(int onePageCount) {
			this.onePageCount = onePageCount;
		}
		public int getStartPage() {
			return startPage;
		}
		public void setStartPage(int startPage) {
			this.startPage = startPage;
		}
		public String getSearchKey() {
			return searchKey;
		}
		public void setSearchKey(String searchKey) {
			this.searchKey = searchKey;
		}
		public String getSearchWord() {
			return searchWord;
		}
		public void setSearchWord(String searchWord) {
			this.searchWord = searchWord;
		}
		
}
