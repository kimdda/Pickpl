package com.pickpl.dto;

public class ChatDto {
	private int idx;
	private String to_id;
	private String from_id;
	private String message;
	private String send_date;
	private String check_stat;
	private String profile;
	
	public ChatDto() {}

	public ChatDto(int idx, String to_id, String from_id, String message, String send_date, String check_stat, String profile) {
		this.idx = idx;
		this.to_id = to_id;
		this.from_id = from_id;
		this.message = message;
		this.send_date = send_date;
		this.check_stat = check_stat;
		this.profile = profile;
	}

	public int getIdx() {
		return idx;
	}
	public String getTo_id() {
		return to_id;
	}
	public String getFrom_id() {
		return from_id;
	}
	public String getMessage() {
		return message;
	}
	public String getSend_date() {
		return send_date;
	}
	public String getCheck_stat() {
		return check_stat;
	}
	public String getProfile() {
		return profile;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public void setTo_id(String to_id) {
		this.to_id = to_id;
	}
	public void setFrom_id(String from_id) {
		this.from_id = from_id;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public void setSend_date(String send_date) {
		this.send_date = send_date;
	}
	public void setCheck_stat(String check_stat) {
		this.check_stat = check_stat;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}

	@Override
	public String toString() {
		return "[idx=" + idx + ", to_id=" + to_id + ", from_id=" + from_id + ", message=" + message
				+ ", send_date=" + send_date + ", check_stat=" + check_stat + ", profile=" + profile + "]";
	}
	
}
