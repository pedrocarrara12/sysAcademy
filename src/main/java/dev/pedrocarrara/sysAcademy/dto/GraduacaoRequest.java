package dev.pedrocarrara.sysAcademy.dto;

import jakarta.validation.constraints.*;

public record GraduacaoRequest(@NotNull Long modalidadeId,
                               @NotBlank @Size(min = 3,max = 100,message = "o nome deve conter no minimo 3 caracteres" +
                                       "e no maximo 100") String nome) {
}
