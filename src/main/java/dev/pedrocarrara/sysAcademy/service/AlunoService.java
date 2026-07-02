package dev.pedrocarrara.sysAcademy.service;

import dev.pedrocarrara.sysAcademy.dto.AlunoRequest;
import dev.pedrocarrara.sysAcademy.dto.AlunoResponse;
import dev.pedrocarrara.sysAcademy.entity.Aluno;
import dev.pedrocarrara.sysAcademy.repository.AlunoRepository;
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
            throw new RuntimeException("Já existe um aluno com esse email");

        }
        Aluno aluno1 = aluno.toEntity();
        Aluno aluno2 = alunoRepository.save(aluno1);
        return AlunoResponse.fromEntity(aluno2);

    }
    public Page<AlunoResponse> listarAlunos(Pageable pageable) {
        return alunoRepository.findAll(pageable).map(AlunoResponse::fromEntity);
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
            throw new RuntimeException("Ja existe um aluno com esse email");
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
                .orElseThrow(() -> new RuntimeException("Aluno nao encontrado"));
    }
}
