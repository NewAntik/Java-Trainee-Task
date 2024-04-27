package com.shapovalov.trainee.model.constant;

public final class Constants {

	private Constants() {}

	public static final class Endpoints {
		public static final String API_VERSION_1_0 = "/v1.0";
		public static final String UPDATING = "/updating";
		public static final String NEW = "/new";
		public static final String PATCH = "/patch";

		private Endpoints() {}
	}

	public static final class ErrorMessages {
		public static final String FIRST_NAME_MUST_NOT_BE_NULL = "First name must not be null!";
		public static final String LAST_NAME_MUST_NOT_BE_NULL = "Last name must not be null!";
		public static final String EMAIL_MUST_NOT_BE_NULL = "Email must not be null!";
		public static final String BIRTHDAY_MUST_NOT_BE_NULL = "Birthday must not be null!";
		public static final String EMAIL_MUST_BE_VALID_EMAIL_ADDRESS = "Email must be valid email address!";
		public static final String EMAIL_MUST_NOT_BE_EMPTY = "Email must not be empty!";
		public static final String BIRTHDAY_MUST_NOT_BE_IN_THE_PAST = "Birthday must be in the past!";
		public static final String FIRST_NAME_MUST_NOT_BE_EMPTY = "First name must not be empty!";
		public static final String LAST_NAME_MUST_NOT_BE_EMPTY = "Last name must not be empty!";
		public static final String ID_MUST_NOT_BE_NULL = "Id must not be null!";

		private ErrorMessages() {}
	}
}
