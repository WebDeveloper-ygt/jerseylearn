package com.learn.jersey.model;

public class ZonedDateTimeModel {
	private String input;
	private String req_month;
	private String req_day;
	private int req_year;
	private String req_time;
	
	public String getInput() {
		return input;
	}
	public void setInput(String input) {
		this.input = input;
	}
	public String getReq_month() {
		return req_month;
	}
	public void setReq_month(String req_month) {
		this.req_month = req_month;
	}
	public String getReq_day() {
		return req_day;
	}
	public void setReq_day(String req_day) {
		this.req_day = req_day;
	}
	public int getReq_year() {
		return req_year;
	}
	public void setReq_year(int i) {
		this.req_year = i;
	}
	public String getReq_time() {
		return req_time;
	}
	public void setReq_time(String req_time) {
		this.req_time = req_time;
	}
}
