package org.arones.exception;

import lombok.Getter;

@Getter
public class SagaException extends RuntimeException{

    private final String sagaId;
    private final int remoteStatus;

    public SagaException(String sagaId, int remoteStatus, String message, Throwable cause){
        super(message, cause);
        this.sagaId = sagaId;
        this.remoteStatus = remoteStatus;
    }

}
