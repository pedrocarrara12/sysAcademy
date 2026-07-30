package dev.pedrocarrara.sysAcademy.controller;

import dev.pedrocarrara.sysAcademy.dto.MatriculaFiltro;
import dev.pedrocarrara.sysAcademy.dto.MatriculaAtualizacaoRequest;
import dev.pedrocarrara.sysAcademy.dto.MatriculaRequest;
import dev.pedrocarrara.sysAcademy.dto.MatriculaResponse;
import dev.pedrocarrara.sysAcademy.service.MatriculaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/matriculas")
public class MatriculaController {

    private final MatriculaService matriculaService;

    public MatriculaController(MatriculaService matriculaService) {
        this.matriculaService = matriculaService;
    }

    @GetMapping
    public ResponseEntity<Page<MatriculaResponse>> listar(@ModelAttribute MatriculaFiltro filtro, Pageable pageable) {
        return ResponseEntity.ok(matriculaService.listarMatriculas(filtro, pageable));
    }
    @PostMapping
    public ResponseEntity<MatriculaResponse> criarMatricula(@Valid @RequestBody MatriculaRequest matriculaRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(matriculaService.criarMatricula(matriculaRequest));
    }
    @GetMapping("/{id}")
    public ResponseEntity<MatriculaResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(matriculaService.buscarPorId(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<MatriculaResponse> atualizarMatricula(
            @PathVariable Long id,
            @Valid @RequestBody MatriculaAtualizacaoRequest matriculaRequest
    ) {
        return ResponseEntity.ok(matriculaService.atualizarMatricula(id, matriculaRequest));
    }

    @PatchMapping("/{id}/encerrar")
    public ResponseEntity<Void> encerrarMatricula(@PathVariable Long id) {
        matriculaService.encerrarMatricula(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<Void> cancelarMatricula(@PathVariable Long id) {
        matriculaService.cancelarMatricula(id);
        return ResponseEntity.noContent().build();
    }
}
