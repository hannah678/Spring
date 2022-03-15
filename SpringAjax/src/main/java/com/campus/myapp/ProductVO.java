package com.campus.myapp;

public class ProductVO {
	private int proNO;
	private String proName;
	private String option;
	private int price;
	private int cnt;
	
	public ProductVO(int proNo, String proName, int price, int cnt) {
		this.proNO = proNo;
		this.proName = proName;
		this.price = price;
		this.cnt = cnt;
	}
	public int getProNO() {
		return proNO;
	}
	public void setProNO(int proNO) {
		this.proNO = proNO;
	}
	public String getProName() {
		return proName;
	}
	public void setProName(String proName) {
		this.proName = proName;
	}
	public String getOption() {
		return option;
	}
	public void setOption(String option) {
		this.option = option;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getCnt() {
		return cnt;
	}
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}

}
