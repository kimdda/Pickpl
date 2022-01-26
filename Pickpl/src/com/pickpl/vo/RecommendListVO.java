package com.pickpl.vo;

import java.util.ArrayList;

public class RecommendListVO {
	private String title;
	private ArrayList<DiaryListVO> list;
	
	public RecommendListVO() { }

	public RecommendListVO(String title, ArrayList<DiaryListVO> list) {
		super();
		this.title = title;
		this.list = list;
	}

	public String getTitle() {
		return title;
	}
	public ArrayList<DiaryListVO> getList() {
		return list;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setList(ArrayList<DiaryListVO> list) {
		this.list = list;
	}
}
