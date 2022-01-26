package com.pickpl.vo;

public class MapVO {
	private int map_d_id;
	private int latitude;
	private int longtitude;
	private String Do;
	private String Gu;
	private String[] img;
	private String place_name;
	private String profile;
	private String writer_id;
	
	public MapVO() {}

	public MapVO(int map_d_id, int latitude, int longtitude, String do1, String gu, String[] img, String place_name,
			String profile, String writer_id) {
		this.map_d_id = map_d_id;
		this.latitude = latitude;
		this.longtitude = longtitude;
		Do = do1;
		Gu = gu;
		this.img = img;
		this.place_name = place_name;
		this.profile = profile;
		this.writer_id = writer_id;
	}

	public int getMap_d_id() {
		return map_d_id;
	}
	public int getLatitude() {
		return latitude;
	}
	public int getLongtitude() {
		return longtitude;
	}
	public String getDo() {
		return Do;
	}
	public String getGu() {
		return Gu;
	}
	public String[] getImg() {
		return img;
	}
	public String getPlace_name() {
		return place_name;
	}
	public String getProfile() {
		return profile;
	}
	public String getWriter_id() {
		return writer_id;
	}
	public void setMap_d_id(int map_d_id) {
		this.map_d_id = map_d_id;
	}
	public void setLatitude(int latitude) {
		this.latitude = latitude;
	}
	public void setLongtitude(int longtitude) {
		this.longtitude = longtitude;
	}
	public void setDo(String do1) {
		Do = do1;
	}
	public void setGu(String gu) {
		Gu = gu;
	}
	public void setImg(String[] img) {
		this.img = img;
	}
	public void setPlace_name(String place_name) {
		this.place_name = place_name;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	public void setWriter_id(String writer_id) {
		this.writer_id = writer_id;
	}
	
}
