package com.pickpl.dto;

import java.util.ArrayList;

public class RcmndListDto {
	private int no;
	private String title;
	private String update;
	private String open_date;
	private String close_date;
	private String d_id;
	private int d_count;
	private String hold;
	private ArrayList<ViewDiaryDto> rcmndList;
	
	public RcmndListDto() { }
	public RcmndListDto(int no, String title, String update, String open_date, String close_date, String d_id,
			int d_count, String hold, ArrayList<ViewDiaryDto> rcmndList) {
		this.no = no;
		this.title = title;
		this.update = update;
		this.open_date = open_date;
		this.close_date = close_date;
		this.d_id = d_id;
		this.d_count = d_count;
		this.hold = hold;
		this.rcmndList = rcmndList;
	}
	public int getNo() {
		return no;
	}
	public String getTitle() {
		return title;
	}
	public String getUpdate() {
		return update;
	}
	public String getOpen_date() {
		return open_date;
	}
	public String getClose_date() {
		return close_date;
	}
	public String getD_id() {
		return d_id;
	}
	public int getD_count() {
		return d_count;
	}
	public String getHold() {
		return hold;
	}
	public ArrayList<ViewDiaryDto> getRcmndList() {
		return rcmndList;
	}
	
	public void setNo(int no) {
		this.no = no;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setUpdate(String update) {
		this.update = update;
	}
	public void setOpen_date(String open_date) {
		this.open_date = open_date;
	}
	public void setClose_date(String close_date) {
		this.close_date = close_date;
	}
	public void setD_id(String d_id) {
		this.d_id = d_id;
	}
	public void setD_count(int d_count) {
		this.d_count = d_count;
	}
	public void setHold(String hold) {
		this.hold = hold;
	}
	public void setRcmndList(ArrayList<ViewDiaryDto> rcmndList) {
		this.rcmndList = rcmndList;
	}
	@Override
	public String toString() {
		return "[no=" + no + ", title=" + title + ", update=" + update + ", open_date=" + open_date
				+ ", close_date=" + close_date + ", d_id=" + d_id + ", d_count=" + d_count + ", hold=" + hold
				+ ", rcmndList=" + rcmndList + "]";
	}

	
}
