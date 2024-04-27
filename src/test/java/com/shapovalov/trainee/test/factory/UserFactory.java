package com.shapovalov.trainee.test.factory;

import com.shapovalov.trainee.model.User;
import com.shapovalov.trainee.constant.TestConstants;

public final class UserFactory {

	private UserFactory() {
	}

	public static User createUser() {
		return new User(
			TestConstants.FIST_NAME,
			TestConstants.LAST_NAME,
			TestConstants.EMAIL,
			TestConstants.BIRTHDAY,
			TestConstants.ADDRESS,
			TestConstants.PHONE
		);
	}

	public static User createUserWithId() {
		return new User(
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
