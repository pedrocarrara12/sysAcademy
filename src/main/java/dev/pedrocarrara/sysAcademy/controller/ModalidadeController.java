package dev.pedrocarrara.sysAcademy.controller;

import dev.pedrocarrara.sysAcademy.dto.ModalidadeRequest;
import dev.pedrocarrara.sysAcademy.dto.ModalidadeResponse;
import dev.pedrocarrara.sysAcademy.service.ModalidadeService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/modalidades")
public class ModalidadeController {

    private final ModalidadeService modalidadeService;

    public ModalidadeController(ModalidadeService modalidadeService) {
        this.modalidadeService = modalidadeService;
    }

    @PostMapping
    public ResponseEntity<ModalidadeResponse> criar(
            @RequestBody @Valid ModalidadeRequest request
    ) {
        ModalidadeResponse response = modalidadeService.criarModalidade(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @GetMapping
    public ResponseEntity<Page<ModalidadeResponse>> listar(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) Boolean ativa,
            Pageable pageable
    ) {
        Page<ModalidadeResponse> modalidades =
                modalidadeService.buscarTodos(nome, ativa, pageable);

        return ResponseEntity.ok(modalidades);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ModalidadeResponse> buscarPorId(@PathVariable Long id) {
        ModalidadeResponse response = modalidadeService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ModalidadeResponse> atualizar(@PathVariable Long id, @RequestBody @Valid ModalidadeRequest request){
        ModalidadeResponse response = modalidadeService.atualizarModalidade(id, request);
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id){
        modalidadeService.desativarModalidade(id);
        return ResponseEntity.noContent().build();
    }

}
