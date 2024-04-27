package com.shapovalov.trainee.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDateTime;

import static com.shapovalov.trainee.model.constant.Constants.ErrorMessages.ID_MUST_NOT_BE_NULL;

public class UserUpdateDto {

	@NotNull(message = ID_MUST_NOT_BE_NULL)
	private Long id;

	private String firstName;

	private String lastName;

	private String email;

	@Past(message = "Birthday must be in the past")
	private LocalDateTime birthday;

	private String address;

	private String phone;

	public UserUpdateDto() {
	}

	public UserUpdateDto(
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

	public UserUpdateDto(
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
}
