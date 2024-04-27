package com.shapovalov.trainee.repository;

import com.shapovalov.trainee.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	@Query("SELECT u FROM User u WHERE u.birthday >= :fromBirthday AND u.birthday <= :toBirthday")
	List<User> findByBirthdayBetween(
		@Param("fromBirthday") LocalDateTime fromBirthday,
		@Param("toBirthday") LocalDateTime toBirthday
	);

}
