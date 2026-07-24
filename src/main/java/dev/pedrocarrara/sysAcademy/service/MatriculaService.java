package dev.pedrocarrara.sysAcademy.service;

import dev.pedrocarrara.sysAcademy.dto.MatriculaRequest;
import dev.pedrocarrara.sysAcademy.dto.MatriculaResponse;
import dev.pedrocarrara.sysAcademy.entity.Aluno;
import dev.pedrocarrara.sysAcademy.entity.Matricula;
import dev.pedrocarrara.sysAcademy.enums.StatusMatricula;
import dev.pedrocarrara.sysAcademy.exception.MatriculaNotFound;
import dev.pedrocarrara.sysAcademy.exception.RegraDeNegocioException;
import dev.pedrocarrara.sysAcademy.repository.AlunoRepository;
import dev.pedrocarrara.sysAcademy.repository.MatriculaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class MatriculaService {

    private final MatriculaRepository matriculaRepository;
    private final AlunoRepository alunoRepository;

    public MatriculaService(MatriculaRepository matriculaRepository, AlunoRepository alunoRepository) {
        this.matriculaRepository = matriculaRepository;
        this.alunoRepository = alunoRepository;
    }

    @Transactional
    public MatriculaResponse criarMatricula(MatriculaRequest matriculaRequest) {
        Aluno aluno = alunoRepository.findById(matriculaRequest.idAluno())
                .orElseThrow(() -> new RegraDeNegocioException("Aluno nao encontrado"));

        validarAlunoSemMatriculaAtiva(matriculaRequest.idAluno());

        Matricula matricula = new Matricula();
        matricula.setAluno(aluno);
        matricula.setDataMatricula(LocalDate.now());
        matricula.setDiaVencimento(matriculaRequest.diaVencimento());
        matricula.setDataEncerramento(null);
        matricula.setStatus(StatusMatricula.ATIVA);

        return MatriculaResponse.fromEntity(matriculaRepository.save(matricula));
    }

    @Transactional(readOnly = true)
    public Page<MatriculaResponse> listarMatriculas(Pageable pageable) {
        return matriculaRepository.findAll(pageable)
                .map(MatriculaResponse::fromEntity);
    }

    @Transactional(readOnly = true)
    public MatriculaResponse buscarPorId(Long id) {
        return MatriculaResponse.fromEntity(buscarEntidadePorId(id));
    }

    private void validarAlunoSemMatriculaAtiva(Long alunoId) {
        boolean possuiMatriculaAtiva = matriculaRepository.existsByAlunoIdAndStatus(
                alunoId,
                StatusMatricula.ATIVA
        );

        if (possuiMatriculaAtiva) {
            throw new RegraDeNegocioException("O aluno ja possui uma matricula ativa.");
        }
    }

    private Matricula buscarEntidadePorId(Long id) {
        return matriculaRepository.findById(id)
                .orElseThrow(() -> new MatriculaNotFound("Matricula nao encontrada."));
    }
}
