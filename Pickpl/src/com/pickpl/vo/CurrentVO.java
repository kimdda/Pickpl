package com.pickpl.vo;

public class CurrentVO {
	private String region;
	private String img;
	
	public CurrentVO() {}

	public CurrentVO(String region, String img) {
		this.region = region;
		this.img = img;
	}

	public String getRegion() {
		return region;
	}
	public String getImg() {
		return img;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public void setImg(String img) {
		this.img = img;
	}
}
