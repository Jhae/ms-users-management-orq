package org.arones.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class NotifyUserRequestDto {
    @NotNull(message = "campo es obligatorio")
    @Size(min = 3, message = "campo debe tener al menos 3 letras")
    private String evento;

    @NotNull(message = "campo es obligatorio")
    @Email(message = "debe ser un correo valido")
    private String destino;
}