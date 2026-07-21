package dev.pedrocarrara.sysAcademy.service;

import dev.pedrocarrara.sysAcademy.dto.PlanoFiltro;
import dev.pedrocarrara.sysAcademy.dto.PlanoRequest;
import dev.pedrocarrara.sysAcademy.dto.PlanoResponse;
import dev.pedrocarrara.sysAcademy.entity.Modalidade;
import dev.pedrocarrara.sysAcademy.entity.Plano;
import dev.pedrocarrara.sysAcademy.exception.RegraDeNegocioException;
import dev.pedrocarrara.sysAcademy.repository.ModalidadeRepository;
import dev.pedrocarrara.sysAcademy.repository.PlanoRepository;
import dev.pedrocarrara.sysAcademy.specification.PlanoSpecification;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public final class PlanoService {
    private final PlanoRepository planoRepository;
    private final ModalidadeRepository modalidadeRepository;

    public PlanoService(PlanoRepository planoRepository, ModalidadeRepository modalidadeRepository) {
        this.planoRepository = planoRepository;
        this.modalidadeRepository = modalidadeRepository;
    }

    @Transactional
    public PlanoResponse criarPlano(PlanoRequest planoRequest) {
        String nome = planoRequest.nome().trim();

        if (planoRepository.existsByNomeIgnoreCase(nome)) {
            throw new RegraDeNegocioException(
                    "Já existe um plano cadastrado com esse nome."
            );
        }

        Plano plano = new Plano();
        plano.setNome(nome);
        plano.setValorMensal(planoRequest.valorMensal());
        plano.setAtivo(planoRequest.ativo());

        Plano planoSalvo = planoRepository.save(plano);

        return PlanoResponse.fromEntity(planoSalvo);
    }
    public Page<PlanoResponse> listarPlanos(Pageable pageable, PlanoFiltro planoFiltro) {
        Specification<Plano> planoSpecification = PlanoSpecification.comFiltros(planoFiltro);

        return planoRepository.findAll(planoSpecification, pageable)
                .map(PlanoResponse::fromEntity);
    }
    private Plano buscarPlanoPorId(Long id) {
        return planoRepository.findById(id).orElseThrow(() -> new RegraDeNegocioException("Nao foi " +
                "encontrado um plano com esse id."));
    }
    private Modalidade buscarModalidadePorId(Long id) {
        return modalidadeRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Nao foi encontrada uma modalidade com esse id."));
    }
    public PlanoResponse  atualizarPlano(Long id, PlanoRequest planoRequest) {
        Plano plano = buscarPlanoPorId(id);
        if (planoRepository.existsByNomeIgnoreCase(plano.getNome())) {
            throw new RegraDeNegocioException("Já existe um plano cadastrado com esse nome.");
        }
        Modalidade modalidade = buscarModalidadePorId(planoRequest.modalidadeId());
        plano.setModalidade(modalidade);
        plano.setNome(plano.getNome());
        plano.setValorMensal(planoRequest.valorMensal());
        plano.setAtivo(planoRequest.ativo());
        planoRepository.save(plano);
        return PlanoResponse.fromEntity(plano);
    }
    public void desativarPlano(Long id) {
        Plano plano = buscarPlanoPorId(id);
        plano.setAtivo(false);
    }
}
