package dev.pedrocarrara.sysAcademy.controller;

import dev.pedrocarrara.sysAcademy.dto.GraduacaoRequest;
import dev.pedrocarrara.sysAcademy.dto.GraduacaoResponse;
import dev.pedrocarrara.sysAcademy.service.GraduacaoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/graduacoes")
public class GraduacaoController {
    private final GraduacaoService graduacaoService;

    public GraduacaoController(GraduacaoService graduacaoService) {
        this.graduacaoService = graduacaoService;
    }

    @PostMapping
    public ResponseEntity<GraduacaoResponse> criarGraduacao(@Valid @RequestBody GraduacaoRequest graduacaoRequest) {
        GraduacaoResponse response = graduacaoService.criarGraduacao(graduacaoRequest);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<GraduacaoResponse>> listarGraduacoes(
            @RequestParam(required = false) Long idModalidade,
            @RequestParam(required = false) String nome,
            Pageable pageable
    ) {
        return ResponseEntity.ok(graduacaoService.listarGraduacoes(pageable, idModalidade, nome));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GraduacaoResponse> listarGraduacaoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(graduacaoService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GraduacaoResponse> atualizarGraduacao(
            @PathVariable Long id,
            @Valid @RequestBody GraduacaoRequest graduacaoRequest
    ) {
        return ResponseEntity.ok(graduacaoService.atualizarGraduacao(id, graduacaoRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirGraduacao(@PathVariable Long id) {
        graduacaoService.excluirGraduacao(id);
        return ResponseEntity.noContent().build();
    }
}
