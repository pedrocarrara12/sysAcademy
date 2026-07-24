package dev.pedrocarrara.sysAcademy.dto;

import dev.pedrocarrara.sysAcademy.enums.StatusMatricula;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record MatriculaFiltro(
        Long alunoId,
        String alunoNome,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate dataMatriculaDe,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate dataMatriculaAte,
        Integer diaVencimento,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate dataEncerramentoDe,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate dataEncerramentoAte,
        StatusMatricula status
) {
}
