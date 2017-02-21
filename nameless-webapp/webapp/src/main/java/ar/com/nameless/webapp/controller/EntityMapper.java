package ar.com.nameless.webapp.controller;

import ar.com.nameless.model.User;
import ar.com.nameless.webapp.form.UserNewForm;
import org.modelmapper.ModelMapper;

/**
 * Maps instances from models to DTOs and forms to models
 */
public class EntityMapper {
	private final ModelMapper mapper;

	public EntityMapper() {
		mapper = new ModelMapper();
	}

	User convertToUser(final UserNewForm userNewForm) {
		return mapper.map(userNewForm, User.class);
	}
}
