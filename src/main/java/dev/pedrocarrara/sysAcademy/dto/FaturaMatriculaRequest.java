package dev.pedrocarrara.sysAcademy.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public record FaturaMatriculaRequest(
        @NotNull(message = "A matricula e obrigatoria")
        @Positive(message = "O ID da matricula deve ser maior que zero")
        Long matriculaId,

        @NotNull(message = "A data de vencimento e obrigatoria")
        LocalDate dataVencimento,

        @NotNull(message = "O valor e obrigatorio")
        @DecimalMin(value = "0.00", message = "O valor deve ser maior ou igual a zero")
        BigDecimal valor
) {
}
