package org.arones.advice;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;
import java.util.Collections;

@ApplicationScoped
@Slf4j
public class GlobalExceptionHandler {

    @ServerExceptionMapper
    public Response handle(Exception exception){
        log.error("ha ocurrido un error no controlado", exception);
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(Collections.singletonMap("message","Ha sucedido un problema"))
                .build();
    }
}
