package com.learn.jersey.exceptions;

public class TokenException extends Exception {

	private String desc;
	private String mess;
	
	public TokenException(String desc, String mess) {
		super();
		this.desc = desc;
		this.mess = mess;
	}

	public TokenException() {
	}

	public String getDesc() {
		return desc;
	}


	public void setDesc(String desc) {
		this.desc = desc;
	}


	public String getMess() {
		return mess;
	}


	public void setMess(String mess) {
		this.mess = mess;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	private static final long serialVersionUID = 1L;

}
