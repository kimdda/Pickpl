package com.pickpl.dto;

public class DiaryAllDto {
	private int d_id;
	private String writer_id;
	private String img;
	private String place_name;
	private String address;
	private String visit_date;
	private int visit_time;
	private int weather_id;
	private String contents;
	private String drone;
	private String public_tran;
	private String public_info;
	private String park;
	private String park_info;
	private String toilet;
	private String shower;
	private String locker;
	private String latitude;
	private String longitude;
	private String Do;
	private String Gu;
	
	public DiaryAllDto() { }

	public DiaryAllDto(int d_id, String writer_id, String img, String place_name, String address, String visit_date,
			int visit_time, int weather_id, String contents, String drone, String public_tran, String public_info,
			String park, String park_info, String toilet, String shower, String locker, String latitude,
			String longitude, String do1, String gu) {
		this.d_id = d_id;
		this.writer_id = writer_id;
		this.img = img;
		this.place_name = place_name;
		this.address = address;
		this.visit_date = visit_date;
		this.visit_time = visit_time;
		this.weather_id = weather_id;
		this.contents = contents;
		this.drone = drone;
		this.public_tran = public_tran;
		this.public_info = public_info;
		this.park = park;
		this.park_info = park_info;
		this.toilet = toilet;
		this.shower = shower;
		this.locker = locker;
		this.latitude = latitude;
		this.longitude = longitude;
		Do = do1;
		Gu = gu;
	}

	public int getD_id() {
		return d_id;
	}

	public String getWriter_id() {
		return writer_id;
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

	public int getVisit_time() {
		return visit_time;
	}

	public int getWeather_id() {
		return weather_id;
	}

	public String getContents() {
		return contents;
	}

	public String getDrone() {
		return drone;
	}

	public String getPublic_tran() {
		return public_tran;
	}

	public String getPublic_info() {
		return public_info;
	}

	public String getPark() {
		return park;
	}

	public String getPark_info() {
		return park_info;
	}

	public String getToilet() {
		return toilet;
	}

	public String getShower() {
		return shower;
	}

	public String getLocker() {
		return locker;
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

	public void setD_id(int d_id) {
		this.d_id = d_id;
	}

	public void setWriter_id(String writer_id) {
		this.writer_id = writer_id;
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

	public void setVisit_time(int visit_time) {
		this.visit_time = visit_time;
	}

	public void setWeather_id(int weather_id) {
		this.weather_id = weather_id;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public void setDrone(String drone) {
		this.drone = drone;
	}

	public void setPublic_tran(String public_tran) {
		this.public_tran = public_tran;
	}

	public void setPublic_info(String public_info) {
		this.public_info = public_info;
	}

	public void setPark(String park) {
		this.park = park;
	}

	public void setPark_info(String park_info) {
		this.park_info = park_info;
	}

	public void setToilet(String toilet) {
		this.toilet = toilet;
	}

	public void setShower(String shower) {
		this.shower = shower;
	}

	public void setLocker(String locker) {
		this.locker = locker;
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
