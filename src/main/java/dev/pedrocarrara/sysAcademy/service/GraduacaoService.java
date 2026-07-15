package dev.pedrocarrara.sysAcademy.service;

import dev.pedrocarrara.sysAcademy.dto.GraduacaoRequest;
import dev.pedrocarrara.sysAcademy.dto.GraduacaoResponse;
import dev.pedrocarrara.sysAcademy.entity.Graduacao;
import dev.pedrocarrara.sysAcademy.entity.Modalidade;
import dev.pedrocarrara.sysAcademy.exception.RegraDeNegocioException;
import dev.pedrocarrara.sysAcademy.repository.GraduacaoRepository;
import dev.pedrocarrara.sysAcademy.repository.ModalidadeRepository;
import dev.pedrocarrara.sysAcademy.specification.GraduacaoSpecification;
import jakarta.transaction.Transactional;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class GraduacaoService {

    private final GraduacaoRepository graduacaoRepository;
    private final ModalidadeRepository modalidadeRepository;

    public GraduacaoService(GraduacaoRepository graduacaoRepository, ModalidadeRepository modalidadeRepository) {
        this.graduacaoRepository = graduacaoRepository;
        this.modalidadeRepository = modalidadeRepository;
    }

    @Transactional
    public GraduacaoResponse criarGraduacao(GraduacaoRequest graduacaoRequest) {
        validarNomeDuplicado(graduacaoRequest.nome());

        Modalidade modalidade = getModalidade(graduacaoRequest);
        Graduacao graduacao = new Graduacao();
        graduacao.setModalidade(modalidade);
        graduacao.setNome(graduacaoRequest.nome());
        return GraduacaoResponse.fromEntity(graduacaoRepository.save(graduacao));
    }

    private @NonNull Modalidade getModalidade(GraduacaoRequest graduacaoRequest) {
        Modalidade modalidade = modalidadeRepository.findById(graduacaoRequest.modalidadeId())
                .orElseThrow(() -> new RegraDeNegocioException("Modalidade nÃ£o encontrada."));
        return modalidade;
    }

    private void validarNomeDuplicado(String nome) {
        if (graduacaoRepository.existsByNomeIgnoreCase(nome)) {
            throw new RegraDeNegocioException("JÃ¡ existe uma graduacao com esse nome.");
        }
    }

    public Page<GraduacaoResponse> listarGraduacoes(Pageable pageable, Long idModalidade, String nome) {
        Specification<Graduacao> specification = Specification.where(
                GraduacaoSpecification.modalidadeIdIgual(idModalidade)
        ).and(GraduacaoSpecification.nomeContem(nome));

        return graduacaoRepository.findAll(specification, pageable)
                .map(GraduacaoResponse::fromEntity);
    }

    public GraduacaoResponse listarGraducoesPorId(Long id) {
        return GraduacaoResponse.fromEntity(graduacaoRepository.findById(id).orElseThrow(() -> new RegraDeNegocioException(
                "Graduacao Id nao encontrada."
        )));
    }

    public void excluirGraduacao(Long id) {
        Graduacao graduacao = graduacaoRepository.findById(id).orElseThrow(() ->
                new RegraDeNegocioException("Graduacao Id nao encontrada."));
        graduacaoRepository.delete(graduacao);
    }

    public GraduacaoResponse atualizarGraduacao(Long id, GraduacaoRequest graduacaoRequest) {


    }
}
