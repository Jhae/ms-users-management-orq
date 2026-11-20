package org.arones.advice;

import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.arones.exception.SagaException;
import org.slf4j.MDC;
import java.util.List;
import java.util.Map;

@Provider
public class SagaExceptionHandler implements ExceptionMapper<SagaException> {

    @Override
    public Response toResponse(SagaException sagaException) {

        int status = switch (sagaException.getRemoteStatus()) {
            case 409, 422 -> sagaException.getRemoteStatus();
            default -> Response.Status.BAD_GATEWAY.getStatusCode();
        };

        String title = switch (status) {
            case 409 -> "Conflict - El recurso tiene conflicto de datos";
            case 422 -> "Unprocessable Entity - Operacion rechazada por regla de negocio";
            default -> "Bad Gateway - Error de comunicacion con servicio dependiente";
        };

        Map<String,Object> body = Map.of(
                "status", status,
                "title",title,
                "detail", sagaException.getMessage(),
                "traceId", String.valueOf(MDC.get("traceId"))
//                "errors", errors<
        );

        return Response.status(status)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(body)
                .build();
    }
}
