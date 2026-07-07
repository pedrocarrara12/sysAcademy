package dev.pedrocarrara.sysAcademy.exception;

import java.time.LocalDateTime;
import java.util.List;

public record ErroResponse(
        LocalDateTime timestamp,
        Integer status,
        String erro,
        List<String> mensagens
) {
    public ErroResponse(Integer status, String erro, List<String> mensagens) {
        this(LocalDateTime.now(), status, erro, mensagens);
    }

    public ErroResponse(Integer status, String erro, String mensagem) {
        this(LocalDateTime.now(), status, erro, List.of(mensagem));
    }
}
