package dev.pedrocarrara.sysAcademy.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record MatriculaAtualizacaoRequest(
        @NotNull(message = "O dia de vencimento e obrigatorio")
        @Min(value = 1, message = "Dia de vencimento deve ser maior ou igual a 1")
        @Max(value = 31, message = "Dia de vencimento deve ser menor ou igual a 31")
        Integer diaVencimento
) {
}
