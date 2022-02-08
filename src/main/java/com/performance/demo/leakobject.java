package com.performance.demo;


public class leakobject {
	private String message;


	public leakobject(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}



}