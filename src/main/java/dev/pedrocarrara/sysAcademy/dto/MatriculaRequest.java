package dev.pedrocarrara.sysAcademy.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record MatriculaRequest(
        @NotNull(message = "O aluno e obrigatorio")
        @Positive(message = "O ID do aluno deve ser maior que zero")
        Long idAluno,

        @NotNull(message = "O dia de vencimento e obrigatorio")
        @Min(value = 1, message = "Dia de vencimento deve ser maior ou igual a 1")
        @Max(value = 31, message = "Dia de vencimento deve ser menor ou igual a 31")
        Integer diaVencimento
) {
}
