package com.pickpl.admin.dto;

public class RcmndDiaryDto {
	private int d_id;
	private String writer;
	private int pick_count;
	private int view_count;
	private String visit_date;
	private String place_name;
	private String region;
	
	public RcmndDiaryDto() {}

	public RcmndDiaryDto(int d_id, String writer, int pick_count, int view_count, String visit_date, String place_name,
			String region) {
		super();
		this.d_id = d_id;
		this.writer = writer;
		this.pick_count = pick_count;
		this.view_count = view_count;
		this.visit_date = visit_date;
		this.place_name = place_name;
		this.region = region;
	}

	public int getD_id() {
		return d_id;
	}
	public String getWriter() {
		return writer;
	}
	public int getPick_count() {
		return pick_count;
	}
	public int getView_count() {
		return view_count;
	}
	public String getVisit_date() {
		return visit_date;
	}
	public String getPlace_name() {
		return place_name;
	}
	public String getRegion() {
		return region;
	}
	public void setD_id(int d_id) {
		this.d_id = d_id;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public void setPick_count(int pick_count) {
		this.pick_count = pick_count;
	}
	public void setView_count(int view_count) {
		this.view_count = view_count;
	}
	public void setVisit_date(String visit_date) {
		this.visit_date = visit_date;
	}
	public void setPlace_name(String place_name) {
		this.place_name = place_name;
	}
	public void setRegion(String region) {
		this.region = region;
	}
}
