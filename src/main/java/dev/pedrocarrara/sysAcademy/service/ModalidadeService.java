package dev.pedrocarrara.sysAcademy.service;

import dev.pedrocarrara.sysAcademy.dto.ModalidadeRequest;
import dev.pedrocarrara.sysAcademy.dto.ModalidadeResponse;
import dev.pedrocarrara.sysAcademy.entity.Modalidade;
import dev.pedrocarrara.sysAcademy.exception.ModalidadeNotFound;
import dev.pedrocarrara.sysAcademy.exception.RegraDeNegocioException;
import dev.pedrocarrara.sysAcademy.repository.ModalidadeRepository;
import dev.pedrocarrara.sysAcademy.specification.ModalidadeSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ModalidadeService {
    private final ModalidadeRepository modalidadeRepository;

    public ModalidadeService(ModalidadeRepository modalidadeRepository) {
        this.modalidadeRepository = modalidadeRepository;
    }

    @Transactional
    public ModalidadeResponse criarModalidade(ModalidadeRequest modalidadeRequest) {
        Optional<Modalidade> modalidadeExistente =
                modalidadeRepository.findByNomeIgnoreCase(modalidadeRequest.nome());

        if (modalidadeExistente.isPresent()) {
            throw new RegraDeNegocioException("Ja existe uma modalidade com esse nome.");
        }

        Modalidade modalidade = new Modalidade(
                modalidadeRequest.nome(),
                modalidadeRequest.ativa()
        );

        Modalidade modalidadeSalva = modalidadeRepository.save(modalidade);
        return ModalidadeResponse.fromEntity(modalidadeSalva);
    }

    @Transactional(readOnly = true)
    public ModalidadeResponse buscarPorId(Long id) {
        Modalidade modalidade = buscarEntidadeModalidadeId(id);
        return ModalidadeResponse.fromEntity(modalidade);
    }

    @Transactional(readOnly = true)
    public Page<ModalidadeResponse> buscarTodos(String nome, Boolean ativa, Pageable pageable) {
        Specification<Modalidade> specification = Specification.where(ModalidadeSpecification.nomeContem(nome))
                .and(ModalidadeSpecification.ativaIgual(ativa));

        return modalidadeRepository.findAll(specification, pageable)
                .map(ModalidadeResponse::fromEntity);
    }

    @Transactional
    public ModalidadeResponse atualizarModalidade(Long id, ModalidadeRequest modalidadeRequest) {
        Modalidade modalidade = buscarEntidadeModalidadeId(id);

        modalidadeRepository.findByNomeIgnoreCase(modalidadeRequest.nome())
                .filter(modalidadeExistente -> !modalidadeExistente.getId().equals(id))
                .ifPresent(modalidadeExistente -> {
                    throw new RegraDeNegocioException("Ja existe uma modalidade com esse nome.");
                });

        modalidade.setNome(modalidadeRequest.nome());
        modalidade.setAtiva(modalidadeRequest.ativa());
        return ModalidadeResponse.fromEntity(modalidade);
    }

    @Transactional
    public void desativarModalidade(Long id) {
        Modalidade modalidade = buscarEntidadeModalidadeId(id);
        modalidade.setAtiva(false);
    }

    private Modalidade buscarEntidadeModalidadeId(Long id) {
        return modalidadeRepository.findById(id)
                .orElseThrow(() -> new ModalidadeNotFound("Nao foi encontrada uma modalidade com esse id."));
    }
}
