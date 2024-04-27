package com.shapovalov.trainee.service.impl;

import com.shapovalov.trainee.model.User;
import com.shapovalov.trainee.repository.UserRepository;
import com.shapovalov.trainee.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static java.time.temporal.ChronoUnit.YEARS;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository repository;

	private final int userAge;

	public UserServiceImpl(@Value("${user.age}") final int userAge, final UserRepository repository) {
		this.userAge = userAge;
		this.repository = repository;
	}

	@Override
	@Transactional
	public void createUser(final User user) {
		if (!isValidBirthDate(user.getBirthday())) {
			throw new IllegalArgumentException("Can not create user! User should be older than 18.");
		}

		repository.save(user);
	}

	@Override
	@Transactional
	public User updateUser(final User user) {
		final User userFromDb = repository.findById(user.getId()).orElseThrow(
				() -> new IllegalArgumentException("Can not update user! User with id " + user.getId() + " not found.")
		);

		updateExistingUser(userFromDb, user);
		repository.saveAndFlush(userFromDb);

		return userFromDb;
	}

	@Override
	@Transactional
	public User updateUserField(final User user) {
		final User userFromDb = repository.findById(user.getId()).orElseThrow(
			() -> new IllegalArgumentException("Can not update user! User with id " + user.getId() + " not found.")
		);
		updateNotNullFields(user, userFromDb);
		repository.saveAndFlush(userFromDb);

		return userFromDb;
	}

	@Override
	@Transactional
	public void deleteUser(final Long id) {
		if (repository.existsById(id)) {
			repository.deleteById(id);
		} else {
			throw new IllegalArgumentException("Can not delete user! User with id " + id + " not found.");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<User> searchByBirthDateRange(final LocalDateTime fromBirthday, final LocalDateTime toBirthday) {
		List<User> users = repository.findByBirthdayBetween(fromBirthday, toBirthday);

		if (!users.isEmpty()) {
			return users;
		}else{
			throw new IllegalArgumentException(
				"No users found between these dates! from " + fromBirthday +
					" to " + toBirthday
			);
		}
	}

	private void updateNotNullFields(final User source, final User user){
		if(source.getFirstName() != null){
			user.setFirstName(source.getFirstName());
		}
		if(source.getLastName() != null){
			user.setLastName(source.getLastName());
		}
		if(source.getEmail() != null){
			user.setEmail(source.getEmail());
		}
		if(source.getBirthday() != null){
			user.setBirthday(source.getBirthday());
		}
		if(source.getAddress() != null){
			user.setAddress(source.getAddress());
		}
		if(source.getPhone() != null){
			user.setPhone(source.getPhone());
		}
	}

	private boolean isValidBirthDate(final LocalDateTime birthday) {
		long age = YEARS.between(birthday, LocalDateTime.now());

		return age >= userAge;
	}

	private void updateExistingUser(final User target, final User source) {
		target.setFirstName(source.getFirstName());
		target.setLastName(source.getLastName());
		target.setEmail(source.getEmail());
		target.setBirthday(source.getBirthday());
		target.setAddress(source.getAddress());
		target.setPhone(source.getPhone());
	}
}
