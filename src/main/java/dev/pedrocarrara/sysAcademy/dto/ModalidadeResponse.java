package dev.pedrocarrara.sysAcademy.dto;

import dev.pedrocarrara.sysAcademy.entity.Modalidade;

public record ModalidadeResponse(
        Long id,
        String nome,
        Boolean ativa
) {
    public static ModalidadeResponse fromEntity(Modalidade modalidade) {
        return new ModalidadeResponse(
                modalidade.getId(),
                modalidade.getNome(),
                modalidade.getAtiva()
        );
    }
}
