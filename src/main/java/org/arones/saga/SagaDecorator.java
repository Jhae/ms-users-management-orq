package org.arones.saga;

import jakarta.annotation.Priority;
import jakarta.decorator.Decorator;
import jakarta.decorator.Delegate;
import jakarta.inject.Inject;
import jakarta.interceptor.Interceptor;
import jakarta.ws.rs.WebApplicationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arones.contract.ms.users.management.orq.model.UserManagementRequestDto;
import org.arones.exception.SagaException;

@Decorator
@Priority(Interceptor.Priority.APPLICATION)
@RequiredArgsConstructor(onConstructor_ = @Inject)
@Slf4j
public class SagaDecorator implements SagaStep {

    @Inject
    @Delegate
    private SagaStep delegate;

    private final SagaCoordinator sagaCoordinator;

    @Override
    public void execute(SagaContext<UserManagementRequestDto> sagaContext) {
        try {
            delegate.execute(sagaContext);
            sagaCoordinator.registerSuccess(sagaContext.getId(), delegate);
        } catch (Exception e){
            log.error("Error en ejecucion de Step sagaId: " + sagaContext.getId(), e);
            log.error("Se manda a compensar steps anteriores");

            sagaCoordinator.compensateAll(sagaContext);

            if (e instanceof WebApplicationException webAppException){
                int remoteStatus = webAppException.getResponse().getStatus();
                throw new SagaException(sagaContext.getId(),
                        remoteStatus,
                        "No se pudo completar operaciones",
                        e);
            }
            if (e instanceof jakarta.ws.rs.ProcessingException) {
                throw new SagaException(sagaContext.getId(),
                        502,
                        "Error de comunicación con servicio dependiente",
                        e);
            }

            throw e;
        }
    }

    @Override
    public void compensate(SagaContext<UserManagementRequestDto> sagaContext) {
        delegate.compensate(sagaContext);
    }
}
