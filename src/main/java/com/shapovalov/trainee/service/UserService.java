package com.shapovalov.trainee.service;

import com.shapovalov.trainee.model.User;

import java.time.LocalDateTime;
import java.util.List;

public interface UserService {

	void createUser(User user);

	User updateUser(User user);

	User updateUserField(User user);

	void deleteUser(Long id);

	List<User> searchByBirthDateRange(final LocalDateTime fromBirthday, final LocalDateTime toBirthday);
}
