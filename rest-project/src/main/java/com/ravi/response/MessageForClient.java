/**
 * 
 */
package com.ravi.response;

/**
 * @author Ravi Ranjan
 *
 */
public class MessageForClient {

	Integer status;
	String message;

	public MessageForClient() {
		super();
	}

	public MessageForClient(Integer status, String message) {
		super();
		this.status = status;
		this.message = message;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
