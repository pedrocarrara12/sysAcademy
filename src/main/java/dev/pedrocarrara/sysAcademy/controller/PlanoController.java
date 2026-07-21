package dev.pedrocarrara.sysAcademy.controller;

import dev.pedrocarrara.sysAcademy.dto.PlanoRequest;
import dev.pedrocarrara.sysAcademy.dto.PlanoResponse;
import dev.pedrocarrara.sysAcademy.entity.Plano;
import dev.pedrocarrara.sysAcademy.service.PlanoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return  ResponseEntity.ok(planoResponse);
    }
    @PutMapping
    public ResponseEntity<PlanoResponse> atualizarPlano(@RequestBody @Valid PlanoRequest plano,Long id) {
        PlanoResponse planoResponse = planoService.atualizarPlano(id, plano);
        return  ResponseEntity.ok(planoResponse);
    }

}
