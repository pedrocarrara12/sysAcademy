package dev.pedrocarrara.sysAcademy.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record MatriculaModalidadeRequest(
        @NotNull(message = "A modalidade e obrigatoria")
        @Positive(message = "O ID da modalidade deve ser maior que zero")
        Long modalidadeId,

        @NotNull(message = "A graduacao e obrigatoria")
        @Positive(message = "O ID da graduacao deve ser maior que zero")
        Long graduacaoId,

        @NotNull(message = "O plano e obrigatorio")
        @Positive(message = "O ID do plano deve ser maior que zero")
        Long planoId,
        @PastOrPresent(message = "A data de inicio nao pode estar no futuro")
        LocalDate dataInicio) {
}
