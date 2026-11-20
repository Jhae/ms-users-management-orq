package org.arones.saga;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.WebApplicationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arones.contract.ms.users.management.orq.model.UserManagementRequestDto;
import org.arones.contract.ms.users.management.orq.model.UserManagementResponseDto;
import org.arones.exception.SagaException;
import org.arones.mapper.UserManagementMapper;

import java.util.UUID;

@ApplicationScoped
@Slf4j
public class SagaOrchestator {

    private final SagaStep createUserStep;
    private final SagaStep notifyUserStep;
    private final SagaCoordinator sagaCoordinator;
    private final UserManagementMapper userManagementMapper;

    @Inject
    public SagaOrchestator(@Named("createStep")SagaStep createUserStep,
                           @Named("notifStep") SagaStep notifyUserStep,
                           SagaCoordinator sagaCoordinator,
                           UserManagementMapper userManagementMapper
    ){
        this.createUserStep = createUserStep;
        this.notifyUserStep = notifyUserStep;
        this.sagaCoordinator = sagaCoordinator;
        this.userManagementMapper = userManagementMapper;
    }


    public UserManagementResponseDto processUserCreationManagement(UserManagementRequestDto userManagementRequestDto){
        SagaContext<UserManagementRequestDto> sagaContext = SagaContext.<UserManagementRequestDto>builder()
                .id(UUID.randomUUID().toString())
                .payload(userManagementRequestDto)
                .build();
        UserManagementResponseDto responseDto = null;

        createUserStep.execute(sagaContext);
        notifyUserStep.execute(sagaContext);
        sagaCoordinator.clear(sagaContext.getId());
        responseDto = userManagementMapper.fromUsuarioRsToUserManagementRs(sagaContext.getUsuarioResponseDto());

        return responseDto;
    }
}
