package com.pickpl.admin.dto;

public class DiaryDetailDto {
	private int dId;
	private String writer_id;
	private String up_date;
	private String re_date;
	private String del_date;
	private String up_stat;
	private int pick_count;
	private int view_count;
	private String visit_date;
	private int visit_time;
	private int weather_id;
	private String weather_text;
	private String place_name;
	private String address;
	private String contents;
	private String drone;
	private String public_tran;
	private String public_info;
	private String park;
	private String park_info;
	private String toilet;
	private String shower;
	private String locker;
	private String[] img;
	
	public DiaryDetailDto() { }
	public DiaryDetailDto(int dId, String writer_id, String up_date, String re_date, String del_date, String up_stat,
			int pick_count, int view_count, String visit_date, int visit_time, int weather_id, String weather_text,
			String place_name, String address, String contents, String drone, String public_tran, String public_info,
			String park, String park_info, String toilet, String shower, String locker, String[] img) {
		this.dId = dId;
		this.writer_id = writer_id;
		this.up_date = up_date;
		this.re_date = re_date;
		this.del_date = del_date;
		this.up_stat = up_stat;
		this.pick_count = pick_count;
		this.view_count = view_count;
		this.visit_date = visit_date;
		this.visit_time = visit_time;
		this.weather_id = weather_id;
		this.weather_text = weather_text;
		this.place_name = place_name;
		this.address = address;
		this.contents = contents;
		this.drone = drone;
		this.public_tran = public_tran;
		this.public_info = public_info;
		this.park = park;
		this.park_info = park_info;
		this.toilet = toilet;
		this.shower = shower;
		this.locker = locker;
		this.img = img;
	}
	
	public int getdId() {
		return dId;
	}
	public String getWriter_id() {
		return writer_id;
	}
	public String getUp_date() {
		return up_date;
	}
	public String getRe_date() {
		return re_date;
	}
	public String getDel_date() {
		return del_date;
	}
	public String getUp_stat() {
		return up_stat;
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
	public int getVisit_time() {
		return visit_time;
	}
	public int getWeather_id() {
		return weather_id;
	}
	public String getWeather_text() {
		return weather_text;
	}
	public String getPlace_name() {
		return place_name;
	}
	public String getAddress() {
		return address;
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
	public String[] getImg() {
		return img;
	}
	public void setdId(int dId) {
		this.dId = dId;
	}
	public void setWriter_id(String writer_id) {
		this.writer_id = writer_id;
	}
	public void setUp_date(String up_date) {
		this.up_date = up_date;
	}
	public void setRe_date(String re_date) {
		this.re_date = re_date;
	}
	public void setDel_date(String del_date) {
		this.del_date = del_date;
	}
	public void setUp_stat(String up_stat) {
		this.up_stat = up_stat;
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
	public void setVisit_time(int visit_time) {
		this.visit_time = visit_time;
	}
	public void setWeather_id(int weather_id) {
		this.weather_id = weather_id;
	}
	public void setWeather_text(String weather_text) {
		this.weather_text = weather_text;
	}
	public void setPlace_name(String place_name) {
		this.place_name = place_name;
	}
	public void setAddress(String address) {
		this.address = address;
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
	public void setImg(String[] img) {
		this.img = img;
	}
	
	@Override
	public String toString() {
		return "[dId=" + dId + ", writer_id=" + writer_id + ", up_date=" + up_date + ", re_date="
				+ re_date + ", del_date=" + del_date + ", up_stat=" + up_stat + ", pick_count=" + pick_count
				+ ", view_count=" + view_count + ", visit_date=" + visit_date + ", visit_time=" + visit_time
				+ ", weather_id=" + weather_id + ", weather_text=" + weather_text + ", place_name=" + place_name
				+ ", address=" + address + ", contents=" + contents + ", drone=" + drone + ", public_tran="
				+ public_tran + ", public_info=" + public_info + ", park=" + park + ", park_info=" + park_info
				+ ", toilet=" + toilet + ", shower=" + shower + ", locker=" + locker + ", img=" + img + "]";
	}
}
