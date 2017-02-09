package ar.com.nameless.webapp.controller.validation;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Set;

@Provider
public class ValidationExceptionMapper implements ExceptionMapper<javax.validation.ValidationException> {

    public Response toResponse(javax.validation.ValidationException e) {
        final StringBuilder strBuilder = new StringBuilder();
        Set<ConstraintViolation<?>> constraintViolations = ((ConstraintViolationException) e).getConstraintViolations();
        strBuilder.append(constraintViolations.size() + " errors\n");
        for (ConstraintViolation<?> cv : constraintViolations) {
            strBuilder.append(cv.getPropertyPath().toString() + " " + cv.getMessage());
        }
        return Response
                .status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
                .type(MediaType.TEXT_PLAIN)
                .entity(strBuilder.toString())
                .build();
    }
}