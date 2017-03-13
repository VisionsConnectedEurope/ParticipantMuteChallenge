package com.vc.assignment.pexipmock.controller.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ApiException extends Exception {
	private final HttpStatus httpStatus;
	private final ErrorSpec errorSpec;
	private final String responseBody;

	public ApiException(HttpStatus status, String responseBody) {
		this.httpStatus = status;
		this.responseBody = responseBody;
		this.errorSpec = null;
	}

	public ApiException(HttpStatus status, ErrorSpec errorSpec) {
		this.httpStatus = status;
		this.errorSpec = errorSpec;
		this.responseBody = null;
	}


}


