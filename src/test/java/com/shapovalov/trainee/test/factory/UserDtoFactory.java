package com.shapovalov.trainee.test.factory;

import com.shapovalov.trainee.model.dto.UserCreateDto;
import com.shapovalov.trainee.model.dto.UserUpdateDto;
import com.shapovalov.trainee.constant.TestConstants;

public final class UserDtoFactory {

	private UserDtoFactory() {
	}

	public static UserCreateDto createUserCreateDto() {
		return new UserCreateDto(
			TestConstants.FIST_NAME,
			TestConstants.LAST_NAME,
			TestConstants.EMAIL,
			TestConstants.BIRTHDAY,
			TestConstants.ADDRESS,
			TestConstants.PHONE
		);
	}

	public static UserUpdateDto createUserUpdateDtoWithId() {
		return new UserUpdateDto(
			TestConstants.ID,
			TestConstants.FIST_NAME,
			TestConstants.LAST_NAME,
			TestConstants.EMAIL,
			TestConstants.BIRTHDAY,
			TestConstants.ADDRESS,
			TestConstants.PHONE
		);
	}
}
