package com.todelete;

public class Station {
	private String code;
	private String name;
	private double latitude;
	private double longitude;
	
	public Station(String code, String name, double latitude, double longitude) {
		super();
		this.code = code;
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	public String getCode() {
		return code;
	}
	public String getName() {
		return name;
	}
	public double getLatitude() {
		return latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	@Override
	public String toString() {
		return String.format("%s,%s,%f,%f", code, name, latitude,longitude);
	}
	
}
