package com.shapovalov.trainee.controller;

import com.shapovalov.trainee.model.User;
import com.shapovalov.trainee.model.criteria.SearchDateCriteria;
import com.shapovalov.trainee.model.dto.UserCreateDto;
import com.shapovalov.trainee.model.dto.UserUpdateDto;
import com.shapovalov.trainee.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.shapovalov.trainee.model.constant.Constants.Endpoints.API_VERSION_1_0;
import static com.shapovalov.trainee.model.constant.Constants.Endpoints.NEW;
import static com.shapovalov.trainee.model.constant.Constants.Endpoints.PATCH;
import static com.shapovalov.trainee.model.constant.Constants.Endpoints.UPDATING;

@RestController
@RequestMapping(path = UserController.BASE_URL)
public class UserController {
	public static final String BASE_URL = API_VERSION_1_0 + "/users";

	private final UserService userService;

	private final ModelMapper modelMapper;

	public UserController(final UserService userService, final ModelMapper modelMapper) {
		this.userService = userService;
		this.modelMapper = modelMapper;
	}

	@PostMapping(path = NEW)
	public ResponseEntity<Void> createUser(@RequestBody UserCreateDto userCreateDto) {
		userService.createUser(modelMapper.map(userCreateDto, User.class));

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PostMapping(path = UPDATING)
	public ResponseEntity<UserUpdateDto> updateUser(@RequestBody UserUpdateDto userUpdateDto) {
		final User userToUpdate = modelMapper.map(userUpdateDto, User.class);
		final UserUpdateDto updatedUser = modelMapper.map(userService.updateUser(userToUpdate), UserUpdateDto.class);

		return ResponseEntity.ok(updatedUser);
	}

	@PostMapping(path = PATCH)
	public ResponseEntity<UserUpdateDto> updateUserField(@RequestBody UserUpdateDto userUpdateDto) {
		final User userToUpdate = modelMapper.map(userUpdateDto, User.class);
		final UserUpdateDto updatedUser = modelMapper.map(userService.updateUserField(userToUpdate), UserUpdateDto.class);

		return ResponseEntity.ok(updatedUser);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteById(@PathVariable Long id) {
		userService.deleteUser(id);

		return ResponseEntity.ok("User with ID " + id + " was successfully deleted.");
	}

	@GetMapping
	public ResponseEntity<List<UserUpdateDto>> searchUsersBetweenDates(@RequestBody SearchDateCriteria criteria) {
		final List<User> users = userService.searchByBirthDateRange(
			criteria.getFromBirthday(),
			criteria.getToBirthday()
		);

		final List<UserUpdateDto> userUpdateDtos = users.stream()
			.map(user -> modelMapper.map(user, UserUpdateDto.class))
			.toList();

		return ResponseEntity.ok(userUpdateDtos);
	}
}