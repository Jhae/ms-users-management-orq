package org.arones.advice;

import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.slf4j.MDC;
import java.util.List;
import java.util.Map;

@Provider
public class BadRequestHandler implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {

        List<Map<String, String>> errors = exception.getConstraintViolations().stream()
                .map(cv -> Map.of(
                        "field", cv.getPropertyPath().toString(),
                        "message", cv.getMessage()
                )).toList();

        Map<String,Object> body = Map.of(
                "status", 400,
                "title","Bad Request - Datos invalidos",
                "detail", "La solicitud contiene campos no validos",
                "traceId", String.valueOf(MDC.get("traceId")),
                "errors", errors);

        return Response.status(Response.Status.BAD_REQUEST)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(body)
                .build();
    }
}
