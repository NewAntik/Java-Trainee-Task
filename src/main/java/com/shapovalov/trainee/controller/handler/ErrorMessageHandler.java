package com.shapovalov.trainee.controller.handler;

import jakarta.validation.ConstraintViolationException;

import static com.shapovalov.trainee.model.constant.Constants.ErrorMessages.BIRTHDAY_MUST_NOT_BE_IN_THE_PAST;
import static com.shapovalov.trainee.model.constant.Constants.ErrorMessages.BIRTHDAY_MUST_NOT_BE_NULL;
import static com.shapovalov.trainee.model.constant.Constants.ErrorMessages.EMAIL_MUST_BE_VALID_EMAIL_ADDRESS;
import static com.shapovalov.trainee.model.constant.Constants.ErrorMessages.EMAIL_MUST_NOT_BE_EMPTY;
import static com.shapovalov.trainee.model.constant.Constants.ErrorMessages.EMAIL_MUST_NOT_BE_NULL;
import static com.shapovalov.trainee.model.constant.Constants.ErrorMessages.FIRST_NAME_MUST_NOT_BE_EMPTY;
import static com.shapovalov.trainee.model.constant.Constants.ErrorMessages.FIRST_NAME_MUST_NOT_BE_NULL;
import static com.shapovalov.trainee.model.constant.Constants.ErrorMessages.ID_MUST_NOT_BE_NULL;
import static com.shapovalov.trainee.model.constant.Constants.ErrorMessages.LAST_NAME_MUST_NOT_BE_EMPTY;
import static com.shapovalov.trainee.model.constant.Constants.ErrorMessages.LAST_NAME_MUST_NOT_BE_NULL;

final class ErrorMessageHandler {
	private ErrorMessageHandler() {}

	public static String getErrorMessage(final ConstraintViolationException e) {
		final String errorMessage = e.getMessage();

		if (errorMessage.contains(FIRST_NAME_MUST_NOT_BE_NULL)) {
			return FIRST_NAME_MUST_NOT_BE_NULL;
		} else if (errorMessage.contains(LAST_NAME_MUST_NOT_BE_NULL)) {
			return LAST_NAME_MUST_NOT_BE_NULL;
		} else if (errorMessage.contains(EMAIL_MUST_NOT_BE_NULL)) {
			return EMAIL_MUST_NOT_BE_NULL;
		} else if (errorMessage.contains(BIRTHDAY_MUST_NOT_BE_NULL)) {
			return BIRTHDAY_MUST_NOT_BE_NULL;
		}else if (errorMessage.contains(EMAIL_MUST_BE_VALID_EMAIL_ADDRESS)) {
			return EMAIL_MUST_BE_VALID_EMAIL_ADDRESS;
		}else if (errorMessage.contains(EMAIL_MUST_NOT_BE_EMPTY)) {
			return EMAIL_MUST_NOT_BE_EMPTY;
		}else if (errorMessage.contains(BIRTHDAY_MUST_NOT_BE_IN_THE_PAST)) {
			return BIRTHDAY_MUST_NOT_BE_IN_THE_PAST;
		}else if (errorMessage.contains(FIRST_NAME_MUST_NOT_BE_EMPTY)) {
			return FIRST_NAME_MUST_NOT_BE_EMPTY;
		}else if (errorMessage.contains(LAST_NAME_MUST_NOT_BE_EMPTY)) {
			return LAST_NAME_MUST_NOT_BE_EMPTY;
		}else if (errorMessage.contains(ID_MUST_NOT_BE_NULL)) {
			return ID_MUST_NOT_BE_NULL;
		}

		return errorMessage;
	}
}
