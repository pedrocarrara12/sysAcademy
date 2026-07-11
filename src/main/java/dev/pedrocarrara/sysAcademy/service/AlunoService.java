package dev.pedrocarrara.sysAcademy.service;

import dev.pedrocarrara.sysAcademy.dto.AlunoFiltro;
import dev.pedrocarrara.sysAcademy.dto.AlunoRequest;
import dev.pedrocarrara.sysAcademy.dto.AlunoResponse;
import dev.pedrocarrara.sysAcademy.entity.Aluno;
import dev.pedrocarrara.sysAcademy.exception.RegraDeNegocioException;
import dev.pedrocarrara.sysAcademy.repository.AlunoRepository;
import dev.pedrocarrara.sysAcademy.specification.AlunoSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AlunoService {

    private final AlunoRepository alunoRepository;

    public AlunoService(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    public AlunoResponse cadastroAluno(AlunoRequest aluno) {
        if (aluno.email() != null && alunoRepository.existsByEmail(aluno.email())) {
            throw new RegraDeNegocioException("Ja existe um aluno com esse email");
        }

        Aluno alunoSalvo = alunoRepository.save(aluno.toEntity());
        return AlunoResponse.fromEntity(alunoSalvo);
    }

    public Page<AlunoResponse> listarAlunos(AlunoFiltro filtro, Pageable pageable) {
        return alunoRepository.findAll(AlunoSpecification.comFiltros(filtro), pageable)
                .map(AlunoResponse::fromEntity);
    }

    public AlunoResponse buscarPorId(Long id) {
        Aluno aluno = buscarEntidadePorId(id);
        return AlunoResponse.fromEntity(aluno);
    }

    public AlunoResponse atualizar(Long id, AlunoRequest request) {
        Aluno aluno = buscarEntidadePorId(id);

        if (request.email() != null
                && !request.email().equals(aluno.getEmail())
                && alunoRepository.existsByEmail(request.email())) {
            throw new RegraDeNegocioException("Ja existe um aluno com esse email");
        }

        request.applyToEntity(aluno);
        Aluno alunoAtualizado = alunoRepository.save(aluno);
        return AlunoResponse.fromEntity(alunoAtualizado);
    }

    public void excluir(Long id) {
        Aluno aluno = buscarEntidadePorId(id);
        alunoRepository.delete(aluno);
    }

    private Aluno buscarEntidadePorId(Long id) {
        return alunoRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Aluno nao encontrado"));
    }
}
