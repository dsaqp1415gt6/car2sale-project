package edu.upc.eetac.dsa.dsaqp1415g6.api.model;

public class AnuncioError {
	private int status;
	private String message;
 
	public AnuncioError() {
		super();
	}
 
	public AnuncioError(int status, String message) {
		super();
		this.status = status;
		this.message = message;
	}
 
	public int getStatus() {
		return status;
	}
 
	public void setStatus(int status) {
		this.status = status;
	}
 
	public String getMessage() {
		return message;
	}
 
	public void setMessage(String message) {
		this.message = message;
	}

}
