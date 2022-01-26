package com.pickpl.admin.dto;

public class WeatherDto {
	private String[] weather = {"맑음", "흐림", "구름", "비", "안개", "바람", "눈"};

	public String[] getWeather() {
		return weather;
	}

	public void setWeather(String[] weather) {
		this.weather = weather;
	}
	
}
