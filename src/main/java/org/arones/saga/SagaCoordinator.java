package org.arones.saga;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.arones.contract.ms.users.management.orq.model.UserManagementRequestDto;

@ApplicationScoped
@Slf4j
public class SagaCoordinator {
    private final Map<String, Deque<SagaStep>> stepStack = new ConcurrentHashMap<>();

    public void registerSuccess(String sagaId, SagaStep sagaStep){
        stepStack.computeIfAbsent(sagaId, s -> new ArrayDeque<>()).push(sagaStep);
    }

    public void compensateAll(SagaContext<UserManagementRequestDto> sagaContext){
        Deque<SagaStep> steps  = stepStack.get(sagaContext.getId());
        if (steps == null || steps.isEmpty()) {
            log.info("No hay steps para compensar");
            return;
        }
        log.info("camtidad de steps a compesan: {}",steps.size());
        List<String> stepNames = stepStack.get(sagaContext.getId())
                .stream()
                .map(sagaStep -> sagaStep.getClass().toString())
                .toList();
        log.info("steps a compensar: [{}]", stepNames);

        Deque<SagaStep> stepsCompesate = stepStack.remove(sagaContext.getId());
        if(stepsCompesate != null){
            while(!stepsCompesate.isEmpty()){
                stepsCompesate.pop().compensate(sagaContext);
            }
        }
    }

    public void clear(String sagaId){
        stepStack.remove(sagaId);
    }
}
