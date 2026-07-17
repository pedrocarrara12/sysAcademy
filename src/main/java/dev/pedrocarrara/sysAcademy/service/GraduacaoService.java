package dev.pedrocarrara.sysAcademy.service;

import dev.pedrocarrara.sysAcademy.dto.GraduacaoRequest;
import dev.pedrocarrara.sysAcademy.dto.GraduacaoResponse;
import dev.pedrocarrara.sysAcademy.entity.Graduacao;
import dev.pedrocarrara.sysAcademy.entity.Modalidade;
import dev.pedrocarrara.sysAcademy.exception.RegraDeNegocioException;
import dev.pedrocarrara.sysAcademy.repository.GraduacaoRepository;
import dev.pedrocarrara.sysAcademy.repository.ModalidadeRepository;
import dev.pedrocarrara.sysAcademy.specification.GraduacaoSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        Modalidade modalidade = buscarModalidadePorId(graduacaoRequest.modalidadeId());
        validarNomeDuplicadoNaModalidade(graduacaoRequest.modalidadeId(), graduacaoRequest.nome(), null);

        Graduacao graduacao = new Graduacao();
        graduacao.setModalidade(modalidade);
        graduacao.setNome(graduacaoRequest.nome());

        Graduacao graduacaoSalva = graduacaoRepository.save(graduacao);
        return GraduacaoResponse.fromEntity(graduacaoSalva);
    }

    @Transactional(readOnly = true)
    public Page<GraduacaoResponse> listarGraduacoes(Pageable pageable, Long idModalidade, String nome) {
        Specification<Graduacao> specification = Specification.where(
                GraduacaoSpecification.modalidadeIdIgual(idModalidade)
        ).and(GraduacaoSpecification.nomeContem(nome));

        return graduacaoRepository.findAll(specification, pageable)
                .map(GraduacaoResponse::fromEntity);
    }

    @Transactional(readOnly = true)
    public GraduacaoResponse buscarPorId(Long id) {
        Graduacao graduacao = buscarGraduacaoPorId(id);
        return GraduacaoResponse.fromEntity(graduacao);
    }

    @Transactional
    public GraduacaoResponse atualizarGraduacao(Long id, GraduacaoRequest graduacaoRequest) {
        Graduacao graduacao = buscarGraduacaoPorId(id);
        Modalidade modalidade = buscarModalidadePorId(graduacaoRequest.modalidadeId());

        validarNomeDuplicadoNaModalidade(graduacaoRequest.modalidadeId(), graduacaoRequest.nome(), id);

        graduacao.setNome(graduacaoRequest.nome());
        graduacao.setModalidade(modalidade);

        return GraduacaoResponse.fromEntity(graduacao);
    }

    @Transactional
    public void excluirGraduacao(Long id) {
        Graduacao graduacao = buscarGraduacaoPorId(id);
        graduacaoRepository.delete(graduacao);
    }

    private Modalidade buscarModalidadePorId(Long modalidadeId) {
        return modalidadeRepository.findById(modalidadeId)
                .orElseThrow(() -> new RegraDeNegocioException("Modalidade nao encontrada."));
    }

    private Graduacao buscarGraduacaoPorId(Long id) {
        return graduacaoRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Graduacao nao encontrada."));
    }

    private void validarNomeDuplicadoNaModalidade(Long modalidadeId, String nome, Long graduacaoIdIgnorada) {
        graduacaoRepository.findByModalidadeIdAndNomeIgnoreCase(modalidadeId, nome)
                .filter(graduacao -> !graduacao.getId().equals(graduacaoIdIgnorada))
                .ifPresent(graduacao -> {
                    throw new RegraDeNegocioException("Ja existe uma graduacao com esse nome nessa modalidade.");
                });
    }
}
