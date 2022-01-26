package com.pickpl.dto;

public class MapDto {
	private int map_d_id;
	private String latitude;
	private String longitude;
	private String Do;
	private String Gu;
	
	public MapDto() {}
	public MapDto(int map_d_id, String latitude, String longitude, String do1, String gu) {
		this.map_d_id = map_d_id;
		this.latitude = latitude;
		this.longitude = longitude;
		Do = do1;
		Gu = gu;
	}
	
	public int getMap_d_id() {
		return map_d_id;
	}
	public String getLatitude() {
		return latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public String getDo() {
		return Do;
	}
	public String getGu() {
		return Gu;
	}
	public void setMap_d_id(int map_d_id) {
		this.map_d_id = map_d_id;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public void setDo(String do1) {
		Do = do1;
	}
	public void setGu(String gu) {
		Gu = gu;
	}
}
