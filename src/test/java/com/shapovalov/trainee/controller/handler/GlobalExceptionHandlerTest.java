package com.shapovalov.trainee.controller.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shapovalov.trainee.controller.UserController;
import com.shapovalov.trainee.model.User;
import com.shapovalov.trainee.model.constant.Constants;
import com.shapovalov.trainee.model.dto.UserCreateDto;
import com.shapovalov.trainee.service.UserService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(GlobalExceptionHandler.class)
@Import(UserController.class)
class GlobalExceptionHandlerTest {
	private static final String BASE_URL = Constants.Endpoints.API_VERSION_1_0 + "/users";

	@Mock
	private ConstraintViolation constraintViolation;

	@MockBean
	private ModelMapper modelMapper;

	@MockBean
	private UserService userService;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void handleConstraintViolationException_ShouldReturnErrorResponse() throws Exception {
		final Set<ConstraintViolation<?>> violations = new HashSet<>();
		violations.add(constraintViolation);
		ConstraintViolationException exception = new ConstraintViolationException("First name must not be null!", violations);

		when(modelMapper.map(any(UserCreateDto.class), any())).thenThrow(exception);

		mockMvc.perform(post(BASE_URL.concat(Constants.Endpoints.NEW))
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(new User())))
			.andExpect(status().isBadRequest())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
			.andExpect(jsonPath("$.error").value(HttpStatus.BAD_REQUEST.getReasonPhrase()))
			.andExpect(jsonPath("$.message").value(exception.getMessage()));
	}

	@Test
	void handleIllegalArgumentException_ShouldReturnErrorResponse() throws Exception {
		Mockito.doThrow(new IllegalArgumentException("Can not delete user! User with id 1 not found.")).when(userService).deleteUser(anyLong());

		mockMvc.perform(delete(BASE_URL.concat("/1"))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.timestamp").exists())
			.andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
			.andExpect(jsonPath("$.error").value(HttpStatus.BAD_REQUEST.getReasonPhrase()))
			.andExpect(jsonPath("$.message").value("Can not delete user! User with id 1 not found."));
	}
}

