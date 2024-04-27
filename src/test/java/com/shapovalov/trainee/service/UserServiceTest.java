package com.shapovalov.trainee.service;

import com.shapovalov.trainee.model.User;
import com.shapovalov.trainee.constant.TestConstants;
import com.shapovalov.trainee.repository.UserRepository;
import com.shapovalov.trainee.service.impl.UserServiceImpl;
import com.shapovalov.trainee.test.factory.UserFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@Mock
	private UserRepository userRepository;

	@Value("${user.age}")
	private int userAge;

	private UserServiceImpl userService;

	@BeforeEach
	void setUp() {
		userService = new UserServiceImpl(userAge, userRepository);
	}

	@Test
	void updateUserField_ShouldUpdateOnlyFirstName() {
		final User userToUpdate = UserFactory.createUserWithId();

		final User user = UserFactory.createUserWithId();
		user.setFirstName(TestConstants.OLD_FIST_NAME);
		final User updatedUser = UserFactory.createUserWithId();

		when(userRepository.findById(TestConstants.ID)).thenReturn(Optional.of(user));

		userService.updateUserField(userToUpdate);

		verify(userRepository).saveAndFlush(updatedUser);
	}

	@Test
	void updateUserField_ShouldThrewIllegalArgumentException() {
		final User userToUpdate = UserFactory.createUserWithId();

		when(userRepository.findById(TestConstants.ID)).thenReturn(Optional.empty());

		final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			userService.updateUserField(userToUpdate);
		});

		assertEquals("Can not update user! User with id " + TestConstants.ID + " not found.", exception.getMessage());
	}

	@Test
	void searchByBirthDateRange_ShouldThrewIllegalArgumentException(){

		when(userRepository.findByBirthdayBetween(TestConstants.BIRTHDAY, TestConstants.BIRTHDAY)).thenReturn(new ArrayList<>());

		final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			userService.searchByBirthDateRange(TestConstants.BIRTHDAY, TestConstants.BIRTHDAY);
		});

		assertEquals("No users found between these dates! from " + TestConstants.BIRTHDAY +
			" to " + TestConstants.BIRTHDAY, exception.getMessage());
	}

	@Test
	void searchByBirthDateRange_ShouldReturnUser(){
		final User user = UserFactory.createUserWithId();

		when(userRepository.findByBirthdayBetween(TestConstants.BIRTHDAY, TestConstants.BIRTHDAY)).thenReturn(List.of(user));

		userService.searchByBirthDateRange(TestConstants.BIRTHDAY, TestConstants.BIRTHDAY);

		verify(userRepository).findByBirthdayBetween(TestConstants.BIRTHDAY, TestConstants.BIRTHDAY);
	}

	@Test
	void deleteUser_ShouldThrewIllegalArgumentException() {
		when(userRepository.existsById(TestConstants.ID)).thenReturn(false);

		final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			userService.deleteUser(TestConstants.ID);
		});

		assertEquals("Can not delete user! User with id " + TestConstants.ID + " not found.", exception.getMessage());
	}

	@Test
	void deleteUser_ShouldDeleteUser() {
		when(userRepository.existsById(TestConstants.ID)).thenReturn(true);

		userService.deleteUser(TestConstants.ID);

		verify(userRepository).deleteById(TestConstants.ID);
	}

	@Test
	void updateUser_ShouldUpdateExistingUser() {
		final User userToUpdate = UserFactory.createUserWithId();
		final User oldUser = new User(TestConstants.ID, TestConstants.OLD_LAST_NAME, TestConstants.OLD_FIST_NAME);

		when(userRepository.findById(TestConstants.ID)).thenReturn(Optional.of(oldUser));

		userService.updateUser(userToUpdate);

		verify(userRepository).findById(TestConstants.ID);
		verify(userRepository).saveAndFlush(userToUpdate);
	}

	@Test
	void updateUser_ShouldThrewIllegalArgumentException() {
		final User userToUpdate = UserFactory.createUserWithId();

		when(userRepository.findById(TestConstants.ID)).thenReturn(Optional.empty());

		final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			userService.updateUser(userToUpdate);
		});

		assertEquals("Can not update user! User with id " + TestConstants.ID + " not found.", exception.getMessage());
	}

	@Test
	void createUser_ShouldThrewIllegalArgumentException() {
		final LocalDateTime wrongBirthday = LocalDateTime.of(2099, 1, 1, 0, 0);
		final User user = UserFactory.createUserWithId();
		user.setBirthday(wrongBirthday);

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			userService.createUser(user);
		});

		assertEquals("Can not create user! User should be older than 18.", exception.getMessage());
	}

	@Test
	void createUser_ShouldCreateNewUser() {
		final User newUser = UserFactory.createUser();

		userService.createUser(newUser);

		verify(userRepository).save(newUser);
	}
}
