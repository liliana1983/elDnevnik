package com.iktpreobuka.elektronskidnevnik.util;

public class RestError {

	public Integer code;
	public String message;
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public RestError() {
		super();
		// TODO Auto-generated constructor stub
	}
	public RestError(Integer code, String message) {
		super();
		this.code = code;
		this.message = message;
	}
}