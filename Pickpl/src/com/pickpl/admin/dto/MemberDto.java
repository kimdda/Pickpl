package com.pickpl.admin.dto;

public class MemberDto {
	private String id;
	private String name;
	private String gender;
	private String birth;
	private String phone;
	private String email;
	private String cfMail;
	private String joinDate;
	private String acctStat;
	
	public MemberDto() { }
	public MemberDto(String id, String name, String gender, String birth, String phone, 
			String email, String cfMail, String joinDate, String acctStat) {
		this.id = id;
		this.name = name;
		this.gender = gender;
		this.birth = birth;
		this.phone = phone;
		this.email = email;
		this.cfMail = cfMail;
		this.joinDate = joinDate;
		this.acctStat = acctStat;
	}
	
	public String getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getGender() {
		return gender;
	}
	public String getBirth() {
		return birth;
	}
	public String getPhone() {
		return phone;
	}
	public String getCfMail() {
		return cfMail;
	}
	public String getEmail() {
		return email;
	}
	public String getJoinDate() {
		return joinDate;
	}
	public String getAcctStat() {
		return acctStat;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setCfMail(String cfMail) {
		this.cfMail = cfMail;
	}
	public void setJoinDate(String joinDate) {
		this.joinDate = joinDate;
	}
	public void setAcctStat(String acctStat) {
		this.acctStat = acctStat;
	}
	@Override
	public String toString() {
		return "[id=" + id + ", name=" + name + ", gender=" + gender + ", birth=" + birth + ", phone=" + phone
				+ ", email=" + email + ", cfMail=" + cfMail + ", joinDate=" + joinDate + ", acctStat=" + acctStat + "]";
	}
	
}
