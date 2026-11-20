package org.arones.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class NotifyUserResponseDto {

    private Long id;
    private String evento;
    private String payload;
    private String destino;
    private String estado;
    private OffsetDateTime fecha;
}
