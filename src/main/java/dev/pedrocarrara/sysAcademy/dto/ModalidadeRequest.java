package dev.pedrocarrara.sysAcademy.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ModalidadeRequest(
        @NotBlank
        @Size(min = 1, max = 100)
        String nome,
        Boolean ativa

) {

}
