package dev.pedrocarrara.sysAcademy.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record PlanoRequest(
        @NotNull(message = "Modalidade e obrigatoria")
        Long modalidadeId,

        @NotBlank(message = "Nome e obrigatorio")
        @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
        String nome,

        @NotNull(message = "Valor mensal e obrigatorio")
        @DecimalMin(value = "0.00", message = "Valor mensal deve ser maior ou igual a zero")
        BigDecimal valorMensal,

        Boolean ativo
) {
}
