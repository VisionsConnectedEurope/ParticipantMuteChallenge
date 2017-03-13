package com.vc.assignment.mcubrokerlight.controller.exception;

import java.util.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface ApiExceptionService {

	interface Method {

		ResponseEntity run() throws Exception;
	}

	default <T> ResponseEntity<T> handleApiException(Method method) {
		Map<String, Object> errors = new HashMap<>();
		try {
			return method.run();
		} catch (ApiException ex) {
			if (ex.getResponseBody() != null) {
				errors.put("error", ex.getResponseBody());
			}
			if (ex.getErrorSpec() != null) {
				errors.put("error", ex.getErrorSpec());
			}
			return ResponseEntity.status(ex.getHttpStatus().value()).body((T)errors);
		} catch (Exception e) {
			logError("Exception occured: " + e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body((T)makeErrorSpec(e));
		}
	}

	void logError(String error, Exception e);

	default String makeErrorSpec(Throwable e) {
		String msg = e.getMessage();
		String classname = e.getClass().getSimpleName();
		if (msg == null && e.getCause() != null) {
			classname = e.getCause().getClass().getSimpleName();
			msg = e.getCause().getMessage();
		}
		msg = (msg == null) ? "" : (" : " + msg);
		return classname + msg;
	}
}
