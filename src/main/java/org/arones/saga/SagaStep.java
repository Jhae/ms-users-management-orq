package org.arones.saga;

import  org.arones.contract.ms.users.management.orq.model.UserManagementRequestDto;
import  org.arones.contract.ms.users.management.orq.model.UserManagementResponseDto;

public interface SagaStep {
    void execute(SagaContext<UserManagementRequestDto> sagaContext);
    void compensate(SagaContext<UserManagementRequestDto> sagaContext);
}
