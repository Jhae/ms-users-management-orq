package org.arones.saga;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.arones.contract.ms.users.model.UsuarioResponseDto;
import org.arones.dto.NotifyUserResponseDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SagaContext<T>{

    private String id;
    private T payload;

    private UsuarioResponseDto usuarioResponseDto;
    private NotifyUserResponseDto notifyUserResponseDto;
}
