package com.pickpl.admin.dto;

public class RcmndDto {
	private int no;
	private String title;
	private String up_date;
	private String open_date;
	private String close_date;
	private String d_id;
	private int d_count;
	private String hold;
	
	public RcmndDto() { }
	public RcmndDto(int no, String title, String up_date, String open_date, String close_date, String d_id, int d_count,
			String hold) {
		this.no = no;
		this.title = title;
		this.up_date = up_date;
		this.open_date = open_date;
		this.close_date = close_date;
		this.d_id = d_id;
		this.d_count = d_count;
		this.hold = hold;
	}

	public int getNo() {
		return no;
	}
	public String getTitle() {
		return title;
	}
	public String getUp_date() {
		return up_date;
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
	public void setNo(int no) {
		this.no = no;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setUp_date(String up_date) {
		this.up_date = up_date;
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
	
	@Override
	public String toString() {
		return "[no=" + no + ", title=" + title + ", up_date=" + up_date + ", open_date=" + open_date
				+ ", close_date=" + close_date + ", d_id=" + d_id + ", d_count=" + d_count + ", hold=" + hold + "]";
	}
}
