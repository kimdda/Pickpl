package com.pickpl.admin.dto;

import java.util.HashMap;

public class EtcDto {
	private HashMap<String, String> drone = new HashMap<String, String>();
	private HashMap<String, String> public_tran = new HashMap<String, String>();
	private HashMap<String, String> public_info = new HashMap<String, String>();
	private HashMap<String, String> park = new HashMap<String, String>();
	private HashMap<String, String> park_info = new HashMap<String, String>();
	private HashMap<String, String> toilet = new HashMap<String, String>();
	private HashMap<String, String> shower = new HashMap<String, String>();
	private HashMap<String, String> locker = new HashMap<String, String>();
	
	public EtcDto() {
		drone.put("Y", "드론 이용 가능");
		drone.put("N", "드론 이용 불가능");
		
		public_tran.put("Y", "대중교통 접근성 좋음");
		public_tran.put("N", "대중교통 접근성 나쁨");
		
		public_info.put("less10m", "10분 미만");
		public_info.put("less20m", "20분 미만");
		public_info.put("more20m", "20분 이상");
		
		park.put("Y", "주차 가능");
		park.put("N", "주차 불가능");
		
		park_info.put("F", "무료 주차");
		park_info.put("P", "유료 주차");
		
		toilet.put("Y", "공중화장실 있음");
		toilet.put("N", "공중화장실 없음");
		
		shower.put("Y", "샤워실 있음");
		shower.put("N", "샤워실 없음");
		
		locker.put("Y", "짐 보관 가능");
		locker.put("N", "짐 보관 불가능");
	}

	public HashMap<String, String> getDrone() {
		return drone;
	}
	public HashMap<String, String> getPublic_tran() {
		return public_tran;
	}
	public HashMap<String, String> getPublic_info() {
		return public_info;
	}
	public HashMap<String, String> getPark() {
		return park;
	}
	public HashMap<String, String> getPark_info() {
		return park_info;
	}
	public HashMap<String, String> getToilet() {
		return toilet;
	}
	public HashMap<String, String> getShower() {
		return shower;
	}
	public HashMap<String, String> getLocker() {
		return locker;
	}
	
	public static void main(String[] args) {
		EtcDto etc = new EtcDto();
		System.out.println(etc.getDrone().get("Y"));
	}
}
