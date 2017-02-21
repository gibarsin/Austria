package ar.com.nameless.webapp.controller;

import ar.com.nameless.interfaces.service.UserService;
import ar.com.nameless.model.User;
import ar.com.nameless.webapp.form.UserNewForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

import static javax.ws.rs.core.Response.Status.CONFLICT;
import static javax.ws.rs.core.Response.created;
import static javax.ws.rs.core.Response.status;

@Path("/users")
@Component
public class UserController {

	@Autowired
	private UserService us;

	@Autowired
	private EntityMapper entityMapper;

	@Context
	private UriInfo uriInfo;

	@POST
	public Response usersNew(@Valid final UserNewForm userNewForm) {
		if(us.getByUsername(userNewForm.getUsername()) != null) {
			return status(CONFLICT).build();
		}
		final User user = entityMapper.convertToUser(userNewForm);

		us.create(user);
		final URI uri = uriInfo.getAbsolutePathBuilder().path(user.getUsername()).build();

		return created(uri).build();
	}
}
