package com.shapovalov.trainee.repository;

import com.shapovalov.trainee.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;

	@Test
	void FindByBirthdayBetween_ShouldReturnUserList() {
		final LocalDateTime fromBirthday = LocalDateTime.of(1990, 1, 1, 0, 0);
		final LocalDateTime toBirthday = LocalDateTime.of(1995, 12, 31, 23, 59);
		List<User> users = userRepository.findByBirthdayBetween(fromBirthday, toBirthday);

		assertEquals(3, users.size());
	}
}
