package dev.pedrocarrara.sysAcademy.service;

import dev.pedrocarrara.sysAcademy.dto.FaturaMatriculaRequest;
import dev.pedrocarrara.sysAcademy.dto.FaturaMatriculaResponse;
import dev.pedrocarrara.sysAcademy.entity.Matricula;
import dev.pedrocarrara.sysAcademy.exception.RegraDeNegocioException;
import dev.pedrocarrara.sysAcademy.repository.FaturaMatriculaRepository;
import dev.pedrocarrara.sysAcademy.repository.MatriculaRepository;
import org.springframework.stereotype.Service;

@Service
public class FaturaMatriculaService {

    private final FaturaMatriculaRepository faturaMatriculaRepository;
    private final MatriculaRepository matriculaRepository;


    public FaturaMatriculaService(FaturaMatriculaRepository faturaMatriculaRepository, MatriculaRepository matriculaRepository) {
        this.faturaMatriculaRepository = faturaMatriculaRepository;
        this.matriculaRepository = matriculaRepository;
    }
//    public FaturaMatriculaResponse criarFaturaMatricula(FaturaMatriculaRequest request) {
//        Matricula matricula = buscarMatricula(request.matriculaId());
//    }

    private Matricula buscarMatricula(Long idMatricula) {
        return this.matriculaRepository.findById(idMatricula).orElseThrow(()->
                new RegraDeNegocioException("Matricula não encontrada"));
    }




}
