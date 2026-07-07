package dev.pedrocarrara.sysAcademy.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RegraDeNegocioException.class)
    public ResponseEntity<ErroResponse> tratarRegraDeNegocio(RegraDeNegocioException exception) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErroResponse erro = new ErroResponse(status.value(), status.getReasonPhrase(), exception.getMessage());
        return ResponseEntity.status(status).body(erro);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResponse> tratarErroValidacao(MethodArgumentNotValidException exception) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        List<String> mensagens = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(this::formatarErroCampo)
                .toList();

        ErroResponse erro = new ErroResponse(status.value(), "Erro de validacao", mensagens);
        return ResponseEntity.status(status).body(erro);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErroResponse> tratarJsonInvalido(HttpMessageNotReadableException exception) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErroResponse erro = new ErroResponse(status.value(), status.getReasonPhrase(), "Corpo da requisicao invalido");
        return ResponseEntity.status(status).body(erro);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErroResponse> tratarParametroInvalido(MethodArgumentTypeMismatchException exception) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String mensagem = "Parametro invalido: " + exception.getName();
        ErroResponse erro = new ErroResponse(status.value(), status.getReasonPhrase(), mensagem);
        return ResponseEntity.status(status).body(erro);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErroResponse> tratarRecursoNaoEncontrado(NoResourceFoundException exception) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErroResponse erro = new ErroResponse(status.value(), status.getReasonPhrase(), "Recurso nao encontrado");
        return ResponseEntity.status(status).body(erro);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroResponse> tratarErroInterno(Exception exception) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ErroResponse erro = new ErroResponse(status.value(), status.getReasonPhrase(), "Erro interno do servidor");
        return ResponseEntity.status(status).body(erro);
    }

    private String formatarErroCampo(FieldError erro) {
        return erro.getField() + ": " + erro.getDefaultMessage();
    }
}
