package com.pickpl.dto;

public class SearchListDto {
	private int d_id;
	private String writer_id;
	private String profile;
	private String img;
	private String place_name;
	private String address;
	private int pick_count;
	private int view_count;
	private String pick;
	private String lat;
	private String lng;
	
	public SearchListDto() { }
	public SearchListDto(int d_id, String writer_id, String profile, String img, String place_name, String address,
			int pick_count, int view_count, String pick, String lat, String lng) {
		this.d_id = d_id;
		this.writer_id = writer_id;
		this.profile = profile;
		this.img = img;
		this.place_name = place_name;
		this.address = address;
		this.pick_count = pick_count;
		this.view_count = view_count;
		this.pick = pick;
		this.lat = lat;
		this.lng = lng;
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
	public int getPick_count() {
		return pick_count;
	}
	public int getView_count() {
		return view_count;
	}
	public String getPick() {
		return pick;
	}
	public String getLat() {
		return lat;
	}
	public String getLng() {
		return lng;
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
	public void setPick_count(int pick_count) {
		this.pick_count = pick_count;
	}
	public void setView_count(int view_count) {
		this.view_count = view_count;
	}
	public void setPick(String pick) {
		this.pick = pick;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	
	@Override
	public String toString() {
		return "[d_id=" + d_id + ", writer_id=" + writer_id + ", profile=" + profile + ", img=" + img
				+ ", place_name=" + place_name + ", address=" + address + ", visit_date=" + ", pick_count=" 
				+ pick_count + ", view_count=" + view_count + ", pick=" + pick + ", lat=" + lat + ", lng=" + lng + "]";
	}
	
}
