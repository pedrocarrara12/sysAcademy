package dev.pedrocarrara.sysAcademy.service;

import dev.pedrocarrara.sysAcademy.dto.GraduacaoRequest;
import dev.pedrocarrara.sysAcademy.dto.GraduacaoResponse;
import dev.pedrocarrara.sysAcademy.entity.Graduacao;
import dev.pedrocarrara.sysAcademy.entity.Modalidade;
import dev.pedrocarrara.sysAcademy.exception.RegraDeNegocioException;
import dev.pedrocarrara.sysAcademy.repository.GraduacaoRepository;
import dev.pedrocarrara.sysAcademy.repository.ModalidadeRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        if (graduacaoRepository.existsByNomeIgnoreCase(graduacaoRequest.nome())){
            throw new RegraDeNegocioException("Já existe uma graduacao com esse nome.");
        }
        //Graduacao graduacao = new Graduacao();
        Modalidade modalidade = modalidadeRepository.findById(graduacaoRequest.modalidadeId())
                .orElseThrow(() -> new RegraDeNegocioException("Modalidade não encontrada."));
        Graduacao graduacao = new Graduacao();
        graduacao.setModalidade(modalidade);
        graduacao.setNome(graduacaoRequest.nome());
        return GraduacaoResponse.fromEntity(graduacaoRepository.save(graduacao));

    }
    public Page<GraduacaoResponse> listarGraduacoes(Pageable pageable, Long idModalidade, String nome) {


    }

}
