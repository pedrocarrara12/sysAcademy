package dev.pedrocarrara.sysAcademy.controller;

import dev.pedrocarrara.sysAcademy.dto.GraduacaoRequest;
import dev.pedrocarrara.sysAcademy.dto.GraduacaoResponse;
import dev.pedrocarrara.sysAcademy.service.GraduacaoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/graduacoes")
public  class GraduacaoController {
    private final GraduacaoService graduacaoService;

    public GraduacaoController(GraduacaoService graduacaoService) {
        this.graduacaoService = graduacaoService;
    }
    @PostMapping
    public ResponseEntity<GraduacaoResponse> criarGraduacao(@Valid @RequestBody GraduacaoRequest graduacaoRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(graduacaoService.criarGraduacao(graduacaoRequest));
    }

}
