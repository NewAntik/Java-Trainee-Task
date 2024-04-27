package com.shapovalov.trainee.controller.handler;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ResponseBody
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Object> handleConstraintViolationException(final ConstraintViolationException e) {
		final ErrorDto errorDto = new ErrorDto(HttpStatus.BAD_REQUEST, ErrorMessageHandler.getErrorMessage(e));

		return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
	}

	@ResponseBody
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Object> handleIllegalArgumentException(final IllegalArgumentException e) {
		final ErrorDto errorDto = new ErrorDto(HttpStatus.BAD_REQUEST, e.getMessage());

		return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
	}

	private static class ErrorDto {
		private final LocalDateTime timestamp;
		private final int status;
		private final String error;
		private final String message;

		public ErrorDto(final HttpStatus status, final String message) {
			this.timestamp = LocalDateTime.now();
			this.status = status.value();
			this.error = status.getReasonPhrase();
			this.message = message;
		}

		public LocalDateTime getTimestamp() {
			return timestamp;
		}

		public int getStatus() {
			return status;
		}

		public String getError() {
			return error;
		}

		public String getMessage() {
			return message;
		}
	}
}
