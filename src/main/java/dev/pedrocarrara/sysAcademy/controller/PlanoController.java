package dev.pedrocarrara.sysAcademy.controller;

import dev.pedrocarrara.sysAcademy.dto.PlanoFiltro;
import dev.pedrocarrara.sysAcademy.dto.PlanoRequest;
import dev.pedrocarrara.sysAcademy.dto.PlanoResponse;
import dev.pedrocarrara.sysAcademy.service.PlanoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/planos")
public final class PlanoController {

    private final PlanoService planoService;


    public PlanoController(PlanoService planoService) {
        this.planoService = planoService;
    }
    @PostMapping
    public ResponseEntity<PlanoResponse> cadastrarPlano(@RequestBody @Valid PlanoRequest plano) {
        PlanoResponse planoResponse = planoService.criarPlano(plano);
        return ResponseEntity.status(HttpStatus.CREATED).body(planoResponse);
    }
    @PutMapping("/{id}")
    public ResponseEntity<PlanoResponse> atualizarPlano(@RequestBody @Valid PlanoRequest plano, @PathVariable
    Long id) {
        PlanoResponse planoResponse = planoService.atualizarPlano(id, plano);
        return  ok(planoResponse);
    }
    @GetMapping
    public ResponseEntity<Page<PlanoResponse>> listarPlanos(Pageable pageable, PlanoFiltro planoFiltro) {

        return ok(planoService.listarPlanos(pageable, planoFiltro));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPlano(@PathVariable Long id) {
        planoService.desativarPlano(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{id}")
    public ResponseEntity<PlanoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(planoService.buscarPorId(id));
    }

}
