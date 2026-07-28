package dev.pedrocarrara.sysAcademy.controller;

import dev.pedrocarrara.sysAcademy.dto.MatriculaModalidadeRequest;
import dev.pedrocarrara.sysAcademy.dto.MatriculaModalidadeResponse;
import dev.pedrocarrara.sysAcademy.service.MatriculaModalidadeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/matriculas/{matriculaId}/modalidades")
public class MatriculaModalidadeController {

    private final MatriculaModalidadeService matriculaModalidadeService;

    public MatriculaModalidadeController(
            MatriculaModalidadeService matriculaModalidadeService
    ) {
        this.matriculaModalidadeService = matriculaModalidadeService;
    }

    @PostMapping
    public ResponseEntity<MatriculaModalidadeResponse> adicionarModalidade(
            @PathVariable Long matriculaId,
            @Valid @RequestBody MatriculaModalidadeRequest request
    ) {
        MatriculaModalidadeResponse response =
                matriculaModalidadeService.adicionarModalidade(matriculaId, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<MatriculaModalidadeResponse>> listarPorMatricula(
            @PathVariable Long matriculaId
    ) {
        return ResponseEntity.ok(
                matriculaModalidadeService.listarPorMatricula(matriculaId)
        );
    }

    @GetMapping("/{vinculoId}")
    public ResponseEntity<MatriculaModalidadeResponse> buscarPorId(
            @PathVariable Long matriculaId,
            @PathVariable Long vinculoId
    ) {
        return ResponseEntity.ok(
                matriculaModalidadeService.buscarPorId(matriculaId, vinculoId)
        );
    }
}
