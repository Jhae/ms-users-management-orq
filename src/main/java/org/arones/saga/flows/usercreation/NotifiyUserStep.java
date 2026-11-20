package org.arones.saga.flows.usercreation;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arones.contract.ms.users.management.orq.model.UserManagementRequestDto;
import org.arones.dto.NotifyUserRequestDto;
import org.arones.dto.NotifyUserResponseDto;
import org.arones.proxy.NotificationsApi;
import org.arones.saga.SagaContext;
import org.arones.saga.SagaStep;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
@RequiredArgsConstructor(onConstructor_ = @Inject)
@Slf4j
@Named("notifStep")
public class NotifiyUserStep implements SagaStep {

    @RestClient
    private NotificationsApi notificationsApi;

    @Override
    public void execute(SagaContext<UserManagementRequestDto> sagaContext ) {
        NotifyUserRequestDto request = NotifyUserRequestDto.builder()
                .evento("Creacion Usuario")
                .destino(sagaContext.getPayload().getMail())
                .build();

        NotifyUserResponseDto notifyUserResponseDto = notificationsApi.notifyUser(request);
        sagaContext.setNotifyUserResponseDto(notifyUserResponseDto);

    }

    @Override
    public void compensate(SagaContext<UserManagementRequestDto> sagaContext) {
        log.info("COMPENSANDOOOOOOO... NotifiyUserStep");


    }
}
