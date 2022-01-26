package com.pickpl.dto;

public class MemberDto {
	private String id;
	private String pw;
	private String profile;
	private String email;
	private String name;
	private String phone;
	private String birth;
	private String gender;
	private String cf_mail;
	
	public MemberDto() {}
	public MemberDto(String id, String pw, String profile, String email, String name, String phone, String birth,
			String gender, String cf_mail) {
		this.id = id;
		this.pw = pw;
		this.profile = profile;
		this.email = email;
		this.name = name;
		this.phone = phone;
		this.birth = birth;
		this.gender = gender;
		this.cf_mail = cf_mail;
	}
	
	public String getId() {
		return id;
	}
	public String getPw() {
		return pw;
	}
	public String getProfile() {
		return profile;
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
	public String getGender() {
		return gender;
	}
	public String getCf_mail() {
		return cf_mail;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setPw(String pw) {
		this.pw = pw;
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
	public void setGender(String genger) {
		this.gender = genger;
	}
	public void setCf_mail(String cf_mail) {
		this.cf_mail = cf_mail;
	}
	@Override
	public String toString() {
		return "[id=" + id + ", pw=" + pw + ", profile=" + profile + ", email=" + email + ", name=" + name
				+ ", phone=" + phone + ", birth=" + birth + ", gender=" + gender + ", cf_mail=" + cf_mail + "]";
	}
	
}
