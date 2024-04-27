package com.shapovalov.trainee.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDateTime;
import java.util.Objects;

import static com.shapovalov.trainee.model.constant.Constants.ErrorMessages.BIRTHDAY_MUST_NOT_BE_IN_THE_PAST;
import static com.shapovalov.trainee.model.constant.Constants.ErrorMessages.BIRTHDAY_MUST_NOT_BE_NULL;
import static com.shapovalov.trainee.model.constant.Constants.ErrorMessages.EMAIL_MUST_BE_VALID_EMAIL_ADDRESS;
import static com.shapovalov.trainee.model.constant.Constants.ErrorMessages.EMAIL_MUST_NOT_BE_EMPTY;
import static com.shapovalov.trainee.model.constant.Constants.ErrorMessages.EMAIL_MUST_NOT_BE_NULL;
import static com.shapovalov.trainee.model.constant.Constants.ErrorMessages.FIRST_NAME_MUST_NOT_BE_EMPTY;
import static com.shapovalov.trainee.model.constant.Constants.ErrorMessages.FIRST_NAME_MUST_NOT_BE_NULL;
import static com.shapovalov.trainee.model.constant.Constants.ErrorMessages.LAST_NAME_MUST_NOT_BE_EMPTY;
import static com.shapovalov.trainee.model.constant.Constants.ErrorMessages.LAST_NAME_MUST_NOT_BE_NULL;

@Entity
@Table(name = "users")
public class User {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = FIRST_NAME_MUST_NOT_BE_NULL)
	@NotEmpty(message = FIRST_NAME_MUST_NOT_BE_EMPTY)
	@Column(name = "first_name", length = 20)
	private String firstName;

	@NotNull(message = LAST_NAME_MUST_NOT_BE_NULL)
	@NotEmpty(message = LAST_NAME_MUST_NOT_BE_EMPTY)
	@Column(name = "last_name", length = 20)
	private String lastName;

	@Email(message = EMAIL_MUST_BE_VALID_EMAIL_ADDRESS)
	@NotNull(message = EMAIL_MUST_NOT_BE_NULL)
	@NotEmpty(message = EMAIL_MUST_NOT_BE_EMPTY)
	@Column(name = "email", length = 200)
	private String email;

	@NotNull(message = BIRTHDAY_MUST_NOT_BE_NULL)
	@Past(message = BIRTHDAY_MUST_NOT_BE_IN_THE_PAST)
	@Column(name = "birthday")
	private LocalDateTime birthday;

	@Column(name = "address", length = 100)
	private String address;

	@Column(name = "phone", length = 13)
	private String phone;

	public User (){}

	public User(
		final Long id,
		final String firstName,
		final String lastName
	) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public User(
		final String firstName,
		final String lastName,
		final String email,
		final LocalDateTime birthday,
		final String address,
		final String phone
	) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.birthday = birthday;
		this.address = address;
		this.phone = phone;
	}

	public User(
		final Long id,
		final String firstName,
		final String lastName,
		final String email,
		final LocalDateTime birthday,
		final String address,
		final String phone
	) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.birthday = birthday;
		this.address = address;
		this.phone = phone;
	}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public LocalDateTime getBirthday() {
		return birthday;
	}

	public void setBirthday(final LocalDateTime birthday) {
		this.birthday = birthday;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(final String phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "User{" +
			"id=" + id +
			", firstName='" + firstName + '\'' +
			", lastName='" + lastName + '\'' +
			", email='" + email + '\'' +
			", birthday=" + birthday +
			", address='" + address + '\'' +
			", phone='" + phone + '\'' +
			'}';
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		final User user = (User) o;
		return Objects.equals(id, user.id) &&
			Objects.equals(firstName, user.firstName) &&
			Objects.equals(lastName, user.lastName) &&
			Objects.equals(email, user.email) &&
			Objects.equals(birthday, user.birthday) &&
			Objects.equals(address, user.address) &&
			Objects.equals(phone, user.phone);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, firstName, lastName, email, birthday, address, phone);
	}
}
