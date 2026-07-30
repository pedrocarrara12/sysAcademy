package dev.pedrocarrara.sysAcademy.dto;

import dev.pedrocarrara.sysAcademy.entity.FaturaMatricula;
import dev.pedrocarrara.sysAcademy.enums.StatusFatura;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record FaturaMatriculaResponse(
        Long id,
        Long matriculaId,
        Long alunoId,
        String alunoNome,
        LocalDate dataVencimento,
        BigDecimal valor,
        LocalDateTime dataPagamento,
        LocalDate dataCancelamento,
        StatusFatura status
) {
    public static FaturaMatriculaResponse fromEntity(FaturaMatricula fatura) {
        return new FaturaMatriculaResponse(
                fatura.getId(),
                fatura.getMatricula().getId(),
                fatura.getMatricula().getAluno().getId(),
                fatura.getMatricula().getAluno().getNome(),
                fatura.getDataVencimento(),
                fatura.getValor(),
                fatura.getDataPagamento(),
                fatura.getDataCancelamento(),
                fatura.getStatus()
        );
    }
}
