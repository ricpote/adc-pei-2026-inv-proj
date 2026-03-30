package pt.unl.fct.di.adc.firstwebapp.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import pt.unl.fct.di.adc.firstwebapp.dto.ErrorResponse;

@Provider
public class ApiExceptionMapper implements ExceptionMapper<ApiException> {

    @Override
    public Response toResponse(ApiException exception) {
        ErrorResponse errorResponse = new ErrorResponse(
                exception.getErrorCode().getCode(),
                exception.getMessage());

        return Response.ok(errorResponse).build();
    }
}