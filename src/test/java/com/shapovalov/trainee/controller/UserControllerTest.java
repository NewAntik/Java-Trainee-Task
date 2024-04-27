package com.shapovalov.trainee.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shapovalov.trainee.model.User;
import com.shapovalov.trainee.model.constant.Constants;
import com.shapovalov.trainee.model.criteria.SearchDateCriteria;
import com.shapovalov.trainee.model.dto.UserCreateDto;
import com.shapovalov.trainee.model.dto.UserUpdateDto;
import com.shapovalov.trainee.service.UserService;
import com.shapovalov.trainee.constant.TestConstants;
import com.shapovalov.trainee.test.factory.UserDtoFactory;
import com.shapovalov.trainee.test.factory.UserFactory;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest {
	private static final String BASE_URL = Constants.Endpoints.API_VERSION_1_0 + "/users";

	@MockBean
	private UserService userService;

	@MockBean
	private ModelMapper modelMapper;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void searchUsersBetweenDates_ShouldReturnResponseNotFound() throws Exception {
		final SearchDateCriteria criteria = new SearchDateCriteria();
		final String criteriaJson = objectMapper.writeValueAsString(criteria);

		when(userService.searchByBirthDateRange(any(), any())).thenThrow(
			new IllegalArgumentException(
				"No users found between these dates! from " + TestConstants.BIRTHDAY +
					" to " + TestConstants.BIRTHDAY
			));

		mockMvc.perform(get(BASE_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(criteriaJson))
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.jsonPath("$.message")
				.value("No users found between these dates! from " +
					TestConstants.BIRTHDAY + " to " + TestConstants.BIRTHDAY
				)
			);
	}

	@Test
	void searchUsersBetweenDates_ShouldReturnUserList() throws Exception {
		final SearchDateCriteria criteria = new SearchDateCriteria(TestConstants.BIRTHDAY, TestConstants.BIRTHDAY);
		final String criteriaJson = objectMapper.writeValueAsString(criteria);
		final List<User> users = List.of(UserFactory.createUserWithId());

		when(userService.searchByBirthDateRange(any(), any())).thenReturn(users);
		when(modelMapper.map(users.get(0), UserUpdateDto.class)).thenReturn(UserDtoFactory.createUserUpdateDtoWithId());

		mockMvc.perform(get(BASE_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(criteriaJson))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
			.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1))) ;
	}

	@Test
	void deleteById_ShouldDeleteUserById() throws Exception {
		mockMvc.perform(delete(BASE_URL.concat( "/{id}"), TestConstants.ID.toString())
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(
				MockMvcResultMatchers.content().string(
					"User with ID " + TestConstants.ID + " was successfully deleted."
				)
			);
	}

	@Test
	void updateUserField_ShouldUpdateExistingUserWhereFieldsNotNull() throws Exception {
		final UserUpdateDto userUpdateDto = UserDtoFactory.createUserUpdateDtoWithId();
		final User user = UserFactory.createUserWithId();
		final String userJson = objectMapper.writeValueAsString(user);

		when(userService.updateUserField(any())).thenReturn(user);
		when(modelMapper.map(user, UserUpdateDto.class)).thenReturn(userUpdateDto);
		when(modelMapper.map(userUpdateDto, User.class)).thenReturn(user);

		mockMvc.perform(post(BASE_URL.concat(Constants.Endpoints.PATCH))
				.contentType(MediaType.APPLICATION_JSON)
				.content(userJson))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(user.getId()))
			.andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(user.getFirstName()))
			.andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(user.getLastName()));
	}

	@Test
	void updateUser_ShouldUpdateExistingUser() throws Exception {
		final UserUpdateDto userUpdateDto = UserDtoFactory.createUserUpdateDtoWithId();
		final User user = UserFactory.createUserWithId();
		final String userJson = objectMapper.writeValueAsString(user);

		when(userService.updateUser(any())).thenReturn(user);
		when(modelMapper.map(user, UserUpdateDto.class)).thenReturn(userUpdateDto);
		when(modelMapper.map(userUpdateDto, User.class)).thenReturn(user);

		mockMvc.perform(post(BASE_URL.concat(Constants.Endpoints.UPDATING))
				.contentType(MediaType.APPLICATION_JSON)
				.content(userJson))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(user.getId()))
			.andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(user.getFirstName()))
			.andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(user.getLastName()));
	}

	@Test
	void createUser_ShouldCreateNewUser() throws Exception {
		final UserCreateDto userCreateDto = UserDtoFactory.createUserCreateDto();
		final String userDtoJson = objectMapper.writeValueAsString(userCreateDto);

		when(modelMapper.map(userCreateDto, User.class)).thenReturn(UserFactory.createUserWithId());

		mockMvc.perform(post(BASE_URL.concat(Constants.Endpoints.NEW))
				.contentType(MediaType.APPLICATION_JSON)
				.content(userDtoJson))
			.andExpect(status().isCreated());
	}
}
