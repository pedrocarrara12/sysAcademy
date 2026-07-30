package dev.pedrocarrara.sysAcademy.service;

import dev.pedrocarrara.sysAcademy.dto.MatriculaFiltro;
import dev.pedrocarrara.sysAcademy.dto.MatriculaAtualizacaoRequest;
import dev.pedrocarrara.sysAcademy.dto.MatriculaRequest;
import dev.pedrocarrara.sysAcademy.dto.MatriculaResponse;
import dev.pedrocarrara.sysAcademy.entity.Aluno;
import dev.pedrocarrara.sysAcademy.entity.Matricula;
import dev.pedrocarrara.sysAcademy.entity.MatriculaModalidade;
import dev.pedrocarrara.sysAcademy.enums.StatusMatricula;
import dev.pedrocarrara.sysAcademy.exception.MatriculaNotFound;
import dev.pedrocarrara.sysAcademy.exception.RegraDeNegocioException;
import dev.pedrocarrara.sysAcademy.repository.AlunoRepository;
import dev.pedrocarrara.sysAcademy.repository.MatriculaModalidadeRepository;
import dev.pedrocarrara.sysAcademy.repository.MatriculaRepository;
import dev.pedrocarrara.sysAcademy.specification.MatriculaSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class MatriculaService {

    private final MatriculaRepository matriculaRepository;
    private final AlunoRepository alunoRepository;
    private final MatriculaModalidadeRepository matriculaModalidadeRepository;

    public MatriculaService(
            MatriculaRepository matriculaRepository,
            AlunoRepository alunoRepository,
            MatriculaModalidadeRepository matriculaModalidadeRepository
    ) {
        this.matriculaRepository = matriculaRepository;
        this.alunoRepository = alunoRepository;
        this.matriculaModalidadeRepository = matriculaModalidadeRepository;
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
    public Page<MatriculaResponse> listarMatriculas(MatriculaFiltro filtro, Pageable pageable) {
        if (filtro == null) {
            return listarMatriculas(null, null, null, null, null, null, null, null, pageable);
        }

        return listarMatriculas(
                filtro.alunoId(),
                filtro.alunoNome(),
                filtro.dataMatriculaDe(),
                filtro.dataMatriculaAte(),
                filtro.diaVencimento(),
                filtro.dataEncerramentoDe(),
                filtro.dataEncerramentoAte(),
                filtro.status(),
                pageable
        );
    }

    @Transactional(readOnly = true)
    public Page<MatriculaResponse> listarMatriculas(
            Long alunoId,
            String alunoNome,
            LocalDate dataMatriculaDe,
            LocalDate dataMatriculaAte,
            Integer diaVencimento,
            LocalDate dataEncerramentoDe,
            LocalDate dataEncerramentoAte,
            StatusMatricula status,
            Pageable pageable
    ) {
        Specification<Matricula> matriculaSpecification =
                Specification.where(MatriculaSpecification.alunoIdIgual(alunoId))
                        .and(MatriculaSpecification.alunoNomeContem(alunoNome))
                        .and(MatriculaSpecification.dataMatriculaMaiorOuIgual(dataMatriculaDe))
                        .and(MatriculaSpecification.dataMatriculaMenorOuIgual(dataMatriculaAte))
                        .and(MatriculaSpecification.diaVencimentoIgual(diaVencimento))
                        .and(MatriculaSpecification.dataEncerramentoMaiorOuIgual(dataEncerramentoDe))
                        .and(MatriculaSpecification.dataEncerramentoMenorOuIgual(dataEncerramentoAte))
                        .and(MatriculaSpecification.statusIgual(status));

        return matriculaRepository.findAll(matriculaSpecification, pageable)
                .map(MatriculaResponse::fromEntity);
    }

    @Transactional(readOnly = true)
    public MatriculaResponse buscarPorId(Long id) {
        return MatriculaResponse.fromEntity(buscarEntidadePorId(id));
    }

    @Transactional
    public MatriculaResponse atualizarMatricula(
            Long id,
            MatriculaAtualizacaoRequest matriculaRequest
    ) {
        Matricula matricula = buscarEntidadePorId(id);
        validarMatriculaAtiva(matricula);

        matricula.setDiaVencimento(matriculaRequest.diaVencimento());

        return MatriculaResponse.fromEntity(matricula);
    }

    @Transactional
    public void encerrarMatricula(Long id) {
        Matricula matricula = buscarEntidadePorId(id);
        validarMatriculaAtiva(matricula);
        finalizarMatricula(matricula, StatusMatricula.ENCERRADA);
    }

    @Transactional
    public void cancelarMatricula(Long id) {
        Matricula matricula = buscarEntidadePorId(id);
        validarMatriculaAtiva(matricula);
        finalizarMatricula(matricula, StatusMatricula.CANCELADA);
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

    private void finalizarMatricula(Matricula matricula, StatusMatricula novoStatus) {
        LocalDate dataFinalizacao = LocalDate.now();

        matricula.setStatus(novoStatus);
        matricula.setDataEncerramento(dataFinalizacao);

        for (MatriculaModalidade vinculo :
                matriculaModalidadeRepository.findByMatriculaIdAndDataFimIsNull(matricula.getId())) {
            vinculo.setDataFim(dataFinalizacao);
        }
    }

    private void validarMatriculaAtiva(Matricula matricula) {
        if (matricula.getStatus() != StatusMatricula.ATIVA) {
            throw new RegraDeNegocioException(
                    "A matricula precisa estar ativa para realizar esta operacao."
            );
        }
    }

    private Matricula buscarEntidadePorId(Long id) {
        return matriculaRepository.findById(id)
                .orElseThrow(() -> new MatriculaNotFound("Matricula nao encontrada."));
    }
}
