package dev.pedrocarrara.sysAcademy.dto;

import dev.pedrocarrara.sysAcademy.entity.Plano;

import java.math.BigDecimal;

public record PlanoResponse(
        Long id,
        String nome,
        BigDecimal valorMensal,
        Boolean ativo,
        Long modalidadeId,
        String modalidadeNome
) {
    public static PlanoResponse fromEntity(Plano plano) {
        return new PlanoResponse(
                plano.getId(),
                plano.getNome(),
                plano.getValorMensal(),
                plano.getAtivo(),
                plano.getModalidade().getId(),
                plano.getModalidade().getNome()
        );
    }
}
