package com.pickpl.admin.dto;

public class DiaryListDto {
	private int d_id;
	private String writer_id;
	private String place_name;
	private String region;
	private String up_date;
	private String up_stat;
	
	public DiaryListDto() { }
	public DiaryListDto(int d_id, String writer_id, String place_name, String region, String up_date, String up_stat) {
		this.d_id = d_id;
		this.writer_id = writer_id;
		this.place_name = place_name;
		this.region = region;
		this.up_date = up_date;
		this.up_stat = up_stat;
	}
	
	public int getD_id() {
		return d_id;
	}
	public String getWriter_id() {
		return writer_id;
	}
	public String getPlace_name() {
		return place_name;
	}
	public String getRegion() {
		return region;
	}
	public String getUp_date() {
		return up_date;
	}
	public String getUp_stat() {
		return up_stat;
	}
	public void setD_id(int d_id) {
		this.d_id = d_id;
	}
	public void setWriter_id(String writer_id) {
		this.writer_id = writer_id;
	}
	public void setPlace_name(String place_name) {
		this.place_name = place_name;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public void setUp_date(String up_date) {
		this.up_date = up_date;
	}
	public void setUp_stat(String up_stat) {
		this.up_stat = up_stat;
	}
	@Override
	public String toString() {
		return "[d_id=" + d_id + ", writer_id=" + writer_id + ", place_name=" + place_name + ", region="
				+ region + ", up_date=" + up_date + ", up_stat=" + up_stat + "]";
	}
	
}
