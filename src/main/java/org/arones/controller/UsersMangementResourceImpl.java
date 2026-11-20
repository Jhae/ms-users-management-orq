package org.arones.controller;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arones.contract.ms.users.management.orq.model.UserManagementRequestDto ;
import org.arones.contract.ms.users.management.orq.model.UserManagementResponseDto ;
import org.arones.saga.SagaOrchestator;
import org.jboss.resteasy.reactive.RestResponse;
import org.arones.contract.ms.users.management.orq.resources.UsersManagementResource;

@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class UsersMangementResourceImpl implements UsersManagementResource {

    private final SagaOrchestator sagaOrchestator;

    @Override
    @RolesAllowed("administrador")
    public RestResponse<UserManagementResponseDto> createUsusarioOrq(UserManagementRequestDto userManagementRequestDto) {
        log.info("Incio  [userManagementRequestDto]: ", userManagementRequestDto);

        UserManagementResponseDto responseDto = sagaOrchestator.processUserCreationManagement(userManagementRequestDto);
        log.info("Fin [responseDto]: {}", responseDto);
        return RestResponse.ok(responseDto) ;
    }
}
