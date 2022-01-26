package com.pickpl.dto;

public class CmntsDto {
	private int idx;
	private int d_id;
	private String id;
	private String contents;
	private String date;
	private String profile;
	
	public CmntsDto() {}

	public CmntsDto(int idx, int d_id, String id, String contents, String date, String profile) {
		this.idx = idx;
		this.d_id = d_id;
		this.id = id;
		this.contents = contents;
		this.date = date;
		this.profile = profile;
	}
	
	public int getIdx() {
		return idx;
	}
	public int getD_id() {
		return d_id;
	}
	public String getId() {
		return id;
	}
	public String getContents() {
		return contents;
	}
	public String getDate() {
		return date;
	}
	public String getProfile() {
		return profile;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public void setD_id(int d_id) {
		this.d_id = d_id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}

	@Override
	public String toString() {
		return "[d_id=" + d_id + ", id=" + id + ", contents=" + contents + ", date=" + date + ", profile="
				+ profile + "]";
	}

	
}
