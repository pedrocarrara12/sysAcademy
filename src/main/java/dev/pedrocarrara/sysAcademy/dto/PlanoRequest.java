package dev.pedrocarrara.sysAcademy.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record PlanoRequest(
        @NotNull
        Long modalidadeId,

        @NotBlank
        @Size(max = 100)
        String nome,

        @NotNull
        @DecimalMin(value = "0.00")
        BigDecimal valorMensal,

        Boolean ativo
) {
}
