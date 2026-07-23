package dev.pedrocarrara.sysAcademy.dto;

import dev.pedrocarrara.sysAcademy.entity.Matricula;
import dev.pedrocarrara.sysAcademy.enums.StatusMatricula;

import java.time.LocalDate;

public record MatriculaResponse(
        Long idMatricula,
        Long idAluno,
        String nomeAluno,
        LocalDate dataMatricula,
        Integer diaVencimento,
        LocalDate dataEncerramento,
        StatusMatricula statusMatricula
) {
    public static MatriculaResponse fromEntity(Matricula matricula) {
        return new MatriculaResponse(
                matricula.getId(),
                matricula.getAluno().getId(),
                matricula.getAluno().getNome(),
                matricula.getDataMatricula(),
                matricula.getDiaVencimento(),
                matricula.getDataEncerramento(),
                matricula.getStatus()
        );
    }
}
