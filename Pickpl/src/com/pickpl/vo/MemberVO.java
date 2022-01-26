package com.pickpl.vo;

public class MemberVO {
	private String id;
	private String profile;
	private String email;
	private String name;
	private String phone;
	private String birth;
	private String genger;
	private String cf_mail;
	
	public MemberVO() {}

	

	public MemberVO(String id, String profile, String email, String name, String phone, String birth, String genger,
			String cf_mail) {
		this.id = id;
		this.profile = profile;
		this.email = email;
		this.name = name;
		this.phone = phone;
		this.birth = birth;
		this.genger = genger;
		this.cf_mail = cf_mail;
	}


	
	public String getProfile() {
		return profile;
	}
	public String getId() {
		return id;
	}
	public String getEmail() {
		return email;
	}
	public String getName() {
		return name;
	}
	public String getPhone() {
		return phone;
	}
	public String getBirth() {
		return birth;
	}
	public String getGenger() {
		return genger;
	}
	public String getCf_mail() {
		return cf_mail;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}
	public void setGenger(String genger) {
		this.genger = genger;
	}
	public void setCf_mail(String cf_mail) {
		this.cf_mail = cf_mail;
	}
}
