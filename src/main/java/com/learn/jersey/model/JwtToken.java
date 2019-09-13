package com.learn.jersey.model;

import java.util.Date;

public class JwtToken {

	private String token;
	private Date expiryAt;
	public JwtToken() {

	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Date getExpiryAt() {
		return expiryAt;
	}
	public void setExpiryAt(Date expiryAt) {
		this.expiryAt = expiryAt;
	}
	
	
	
}
