package dev.pedrocarrara.sysAcademy.dto;

import dev.pedrocarrara.sysAcademy.entity.Plano;

import java.math.BigDecimal;

public record PlanoResponse(
        Long id,
        Long modalidadeId,
        String modalidadeNome,
        String nome,
        BigDecimal valorMensal,
        Boolean ativo
) {
    public static PlanoResponse fromEntity(Plano plano) {
        return new PlanoResponse(
                plano.getId(),
                plano.getModalidade().getId(),
                plano.getModalidade().getNome(),
                plano.getNome(),
                plano.getValorMensal(),
                plano.getAtivo()
        );
    }
}
