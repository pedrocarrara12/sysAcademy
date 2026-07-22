package dev.pedrocarrara.sysAcademy.dto;

import java.math.BigDecimal;

public record PlanoFiltro(
        Long modalidadeId,
        String nome,
        BigDecimal valorMensalDe,
        BigDecimal valorMensalAte,
        Boolean ativo
) {
}
