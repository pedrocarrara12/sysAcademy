package dev.pedrocarrara.sysAcademy.dto;

import dev.pedrocarrara.sysAcademy.entity.Graduacao;

public record GraduacaoResponse(
        Long id,
        String nome,
        Long modalidadeId,
        String modalidadeNome
) {
    public static GraduacaoResponse fromEntity(Graduacao graduacao) {
        return new GraduacaoResponse(
                graduacao.getId(),
                graduacao.getNome(),
                graduacao.getModalidade().getId(),
                graduacao.getModalidade().getNome()
        );
    }
}
