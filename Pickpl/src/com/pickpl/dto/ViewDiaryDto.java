package com.pickpl.dto;

public class ViewDiaryDto {
	private int d_id;
	private String writer_id;
	private String profile;
	private String img;
	private String place_name;
	private String address;
	private String visit_date;
	private String visit_time;
	private String up_date;
	private int pick_count;
	private int view_count;
	private String pick;
	
	public ViewDiaryDto() {}

	public ViewDiaryDto(int d_id, String writer_id, String profile, String img, String place_name, String address,
			String visit_date, String visit_time, String up_date, int pick_count, int view_count, String pick) {
		this.d_id = d_id;
		this.writer_id = writer_id;
		this.profile = profile;
		this.img = img;
		this.place_name = place_name;
		this.address = address;
		this.visit_date = visit_date;
		this.visit_time = visit_time;
		this.up_date = up_date;
		this.pick_count = pick_count;
		this.view_count = view_count;
		this.pick = pick;
	}

	public int getD_id() {
		return d_id;
	}

	public String getWriter_id() {
		return writer_id;
	}

	public String getProfile() {
		return profile;
	}

	public String getImg() {
		return img;
	}

	public String getPlace_name() {
		return place_name;
	}

	public String getAddress() {
		return address;
	}

	public String getVisit_date() {
		return visit_date;
	}

	public String getVisit_time() {
		return visit_time;
	}

	public String getUp_date() {
		return up_date;
	}

	public int getPick_count() {
		return pick_count;
	}

	public int getView_count() {
		return view_count;
	}

	public String getPick() {
		return pick;
	}

	public void setD_id(int d_id) {
		this.d_id = d_id;
	}

	public void setWriter_id(String writer_id) {
		this.writer_id = writer_id;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public void setPlace_name(String place_name) {
		this.place_name = place_name;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setVisit_date(String visit_date) {
		this.visit_date = visit_date;
	}

	public void setVisit_time(String visit_time) {
		this.visit_time = visit_time;
	}

	public void setUp_date(String up_date) {
		this.up_date = up_date;
	}

	public void setPick_count(int pick_count) {
		this.pick_count = pick_count;
	}

	public void setView_count(int view_count) {
		this.view_count = view_count;
	}

	public void setPick(String pick) {
		this.pick = pick;
	}

	@Override
	public String toString() {
		return "[d_id=" + d_id + ", writer_id=" + writer_id + ", profile=" + profile + ", img=" + img
				+ ", place_name=" + place_name + ", address=" + address + ", visit_date=" + visit_date + ", visit_time="
				+ visit_time + ", up_date=" + up_date + ", pick_count=" + pick_count + ", view_count=" + view_count
				+ ", pick=" + pick + "]";
	}
	
}
