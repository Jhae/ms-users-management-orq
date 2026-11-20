package org.arones.proxy;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.arones.dto.NotifyUserRequestDto;
import org.arones.dto.NotifyUserResponseDto;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.reactive.RestResponse;

@RegisterRestClient
@Path("/notifications")
@RegisterClientHeaders
public interface NotificationsApi {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public NotifyUserResponseDto notifyUser(NotifyUserRequestDto notifyUserRequestDto);
}

