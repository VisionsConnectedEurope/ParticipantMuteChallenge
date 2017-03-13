package com.vc.assignment.pexipmock.controller.exception;

import lombok.Data;

@Data
public class ErrorSpec {

	private final Integer code;
	private final String fields;
	private final String message;

	public ErrorSpec(Integer code, String message, String fields) {
		this.code = code;
		this.message = message;
		this.fields = fields;
	}
}

