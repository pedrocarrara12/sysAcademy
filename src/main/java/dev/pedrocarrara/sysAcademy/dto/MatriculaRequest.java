package dev.pedrocarrara.sysAcademy.dto;

import dev.pedrocarrara.sysAcademy.enums.StatusMatricula;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record MatriculaRequest(
        @NotNull(message = "O aluno é obrigatório")
        @Positive(message = "O ID do aluno deve ser maior que zero")
        Long idAluno,

        @NotNull(message = "A data da matrícula é obrigatória")
        @PastOrPresent(message = "A data da matrícula não pode estar no futuro")
        LocalDate dataMatricula,

        @NotNull
        @Min(value = 1, message = "Dia de vencimento deve ser maior ou igual a 1")
        @Max(value = 31, message = "Dia de vencimento deve ser menor ou igual a 31")
        Integer diaVencimento,

        @FutureOrPresent(message = "A data de encerramento não pode estar no passado")
        LocalDate dataEncerramento,

        @NotNull(message = "O status da matrícula é obrigatório")
        StatusMatricula statusMatricula

) {
}
