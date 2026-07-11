package dev.pedrocarrara.sysAcademy.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record AlunoFiltro(
        String nome,
        String cpf,
        String email,
        String cidade,
        String estado,
        String sexo,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate nascimentoDe,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate nascimentoAte
) {
}
