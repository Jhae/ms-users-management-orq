package org.arones.saga.flows.usercreation;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arones.contract.ms.users.management.orq.model.UserManagementRequestDto;
import org.arones.contract.ms.users.model.UsuarioResponseDto;
import org.arones.saga.SagaContext;
import org.arones.saga.SagaStep;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.arones.contract.ms.users.api.UsuariosApi;
import org.arones.contract.ms.users.model.UsuarioRequestDto;

@ApplicationScoped
@RequiredArgsConstructor(onConstructor_ = @Inject)
@Slf4j
@Named("createStep")
public class CreateUserStep implements SagaStep {

    @RestClient
    private UsuariosApi usersApi;

    @Override
    public void execute(SagaContext<UserManagementRequestDto> sagaContext) {
        UsuarioRequestDto request = new UsuarioRequestDto()
                .nombre(sagaContext.getPayload().getNombre())
                .contrasena(sagaContext.getPayload().getContrasena())
                .usuario(sagaContext.getPayload().getUsuario())
                .mail(sagaContext.getPayload().getMail());

        UsuarioResponseDto usuarioResponseDto = usersApi.createUsusarioOrq(request);

        sagaContext.setUsuarioResponseDto(usuarioResponseDto);

    }

    @Override
    public void compensate(SagaContext<UserManagementRequestDto> userManagementRequestDtoSagaContext) {
        log.info("COMPENSANDOOOOOOO... CreateUserStep");
    }
}
