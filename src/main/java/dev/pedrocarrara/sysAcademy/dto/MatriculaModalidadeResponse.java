package dev.pedrocarrara.sysAcademy.dto;

import dev.pedrocarrara.sysAcademy.entity.MatriculaModalidade;

import java.math.BigDecimal;
import java.time.LocalDate;

public record MatriculaModalidadeResponse(
        Long id,
        Long matriculaId,
        Long modalidadeId,
        String modalidadeNome,
        Long graduacaoId,
        String graduacaoNome,
        Long planoId,
        String planoNome,
        BigDecimal valorMensal,
        LocalDate dataInicio,
        LocalDate dataFim
) {
    public static MatriculaModalidadeResponse fromEntity(
            MatriculaModalidade vinculo
    ) {
        return new MatriculaModalidadeResponse(
                vinculo.getId(),
                vinculo.getMatricula().getId(),
                vinculo.getModalidade().getId(),
                vinculo.getModalidade().getNome(),
                vinculo.getGraduacao().getId(),
                vinculo.getGraduacao().getNome(),
                vinculo.getPlano().getId(),
                vinculo.getPlano().getNome(),
                vinculo.getPlano().getValorMensal(),
                vinculo.getDataInicio(),
                vinculo.getDataFim()
        );
    }
}

