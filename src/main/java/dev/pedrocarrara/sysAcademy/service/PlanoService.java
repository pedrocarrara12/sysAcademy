package dev.pedrocarrara.sysAcademy.service;

import dev.pedrocarrara.sysAcademy.dto.PlanoFiltro;
import dev.pedrocarrara.sysAcademy.dto.PlanoRequest;
import dev.pedrocarrara.sysAcademy.dto.PlanoResponse;
import dev.pedrocarrara.sysAcademy.entity.Modalidade;
import dev.pedrocarrara.sysAcademy.entity.Plano;
import dev.pedrocarrara.sysAcademy.exception.PlanoNotFound;
import dev.pedrocarrara.sysAcademy.exception.RegraDeNegocioException;
import dev.pedrocarrara.sysAcademy.repository.ModalidadeRepository;
import dev.pedrocarrara.sysAcademy.repository.PlanoRepository;
import dev.pedrocarrara.sysAcademy.specification.PlanoSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlanoService {
    private final PlanoRepository planoRepository;
    private final ModalidadeRepository modalidadeRepository;

    public PlanoService(PlanoRepository planoRepository, ModalidadeRepository modalidadeRepository) {
        this.planoRepository = planoRepository;
        this.modalidadeRepository = modalidadeRepository;
    }

    @Transactional
    public PlanoResponse criarPlano(PlanoRequest planoRequest) {
        String nome = planoRequest.nome().trim();
        Modalidade modalidade = buscarModalidadePorId(planoRequest.modalidadeId());

        validarModalidadeAtiva(modalidade);
        validarNomeDuplicadoNaModalidade(modalidade.getId(), nome, null);

        Plano plano = new Plano();
        plano.setModalidade(modalidade);
        plano.setNome(nome);
        plano.setValorMensal(planoRequest.valorMensal());
        plano.setAtivo(planoRequest.ativo());

        Plano planoSalvo = planoRepository.save(plano);
        return PlanoResponse.fromEntity(planoSalvo);
    }

    @Transactional(readOnly = true)
    public Page<PlanoResponse> listarPlanos(Pageable pageable, PlanoFiltro planoFiltro) {
        Specification<Plano> planoSpecification = PlanoSpecification.comFiltros(planoFiltro);

        return planoRepository.findAll(planoSpecification, pageable)
                .map(PlanoResponse::fromEntity);
    }

    @Transactional(readOnly = true)
    public PlanoResponse buscarPorId(Long id) {
        return PlanoResponse.fromEntity(buscarPlanoPorId(id));
    }

    @Transactional
    public PlanoResponse atualizarPlano(Long id, PlanoRequest planoRequest) {
        Plano plano = buscarPlanoPorId(id);
        String nome = planoRequest.nome().trim();
        Modalidade modalidade = buscarModalidadePorId(planoRequest.modalidadeId());

        validarModalidadeAtiva(modalidade);
        validarNomeDuplicadoNaModalidade(modalidade.getId(), nome, id);

        plano.setModalidade(modalidade);
        plano.setNome(nome);
        plano.setValorMensal(planoRequest.valorMensal());
        plano.setAtivo(planoRequest.ativo());

        return PlanoResponse.fromEntity(plano);
    }

    @Transactional
    public void desativarPlano(Long id) {
        Plano plano = buscarPlanoPorId(id);
        plano.setAtivo(false);
    }

    private Plano buscarPlanoPorId(Long id) {
        return planoRepository.findById(id)
                .orElseThrow(() -> new PlanoNotFound("Nao foi encontrado um plano com esse id."));
    }

    private Modalidade buscarModalidadePorId(Long id) {
        return modalidadeRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Nao foi encontrada uma modalidade com esse id."));
    }

    private void validarModalidadeAtiva(Modalidade modalidade) {
        if (!Boolean.TRUE.equals(modalidade.getAtiva())) {
            throw new RegraDeNegocioException(
                    "Nao e permitido cadastrar ou atualizar um plano em uma modalidade inativa."
            );
        }
    }

    private void validarNomeDuplicadoNaModalidade(Long modalidadeId, String nome, Long planoIdIgnorado) {
        planoRepository.findByModalidadeIdAndNomeIgnoreCase(modalidadeId, nome)
                .filter(planoExistente -> !planoExistente.getId().equals(planoIdIgnorado))
                .ifPresent(planoExistente -> {
                    throw new RegraDeNegocioException("Ja existe um plano cadastrado com esse nome nessa modalidade.");
                });
    }
}
