package dev.pedrocarrara.sysAcademy.service;

import dev.pedrocarrara.sysAcademy.dto.MatriculaModalidadeRequest;
import dev.pedrocarrara.sysAcademy.dto.MatriculaModalidadeResponse;
import dev.pedrocarrara.sysAcademy.entity.Graduacao;
import dev.pedrocarrara.sysAcademy.entity.Matricula;
import dev.pedrocarrara.sysAcademy.entity.MatriculaModalidade;
import dev.pedrocarrara.sysAcademy.entity.Modalidade;
import dev.pedrocarrara.sysAcademy.entity.Plano;
import dev.pedrocarrara.sysAcademy.enums.StatusMatricula;
import dev.pedrocarrara.sysAcademy.exception.MatriculaModalidadeNotFound;
import dev.pedrocarrara.sysAcademy.exception.MatriculaNotFound;
import dev.pedrocarrara.sysAcademy.exception.RegraDeNegocioException;
import dev.pedrocarrara.sysAcademy.repository.GraduacaoRepository;
import dev.pedrocarrara.sysAcademy.repository.MatriculaModalidadeRepository;
import dev.pedrocarrara.sysAcademy.repository.MatriculaRepository;
import dev.pedrocarrara.sysAcademy.repository.ModalidadeRepository;
import dev.pedrocarrara.sysAcademy.repository.PlanoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class MatriculaModalidadeService {

    private final MatriculaModalidadeRepository matriculaModalidadeRepository;
    private final MatriculaRepository matriculaRepository;
    private final ModalidadeRepository modalidadeRepository;
    private final GraduacaoRepository graduacaoRepository;
    private final PlanoRepository planoRepository;

    public MatriculaModalidadeService(
            MatriculaModalidadeRepository matriculaModalidadeRepository,
            MatriculaRepository matriculaRepository,
            ModalidadeRepository modalidadeRepository,
            GraduacaoRepository graduacaoRepository,
            PlanoRepository planoRepository
    ) {
        this.matriculaModalidadeRepository = matriculaModalidadeRepository;
        this.matriculaRepository = matriculaRepository;
        this.modalidadeRepository = modalidadeRepository;
        this.graduacaoRepository = graduacaoRepository;
        this.planoRepository = planoRepository;
    }

    @Transactional
    public MatriculaModalidadeResponse adicionarModalidade(
            Long matriculaId,
            MatriculaModalidadeRequest request
    ) {
        Matricula matricula = buscarMatricula(matriculaId);
        validarMatriculaAtiva(matricula);

        Modalidade modalidade = buscarModalidade(request.modalidadeId());
        Graduacao graduacao = buscarGraduacao(request.graduacaoId());
        Plano plano = buscarPlano(request.planoId());

        validarModalidadeAtiva(modalidade);
        validarGraduacaoDaModalidade(graduacao, modalidade);
        validarPlano(plano, modalidade);
        validarModalidadeNaoVinculada(matriculaId, modalidade.getId());

        LocalDate dataInicio = definirDataInicio(request.dataInicio());
        validarDataInicio(matricula, dataInicio);

        MatriculaModalidade vinculo = new MatriculaModalidade();
        vinculo.setMatricula(matricula);
        vinculo.setModalidade(modalidade);
        vinculo.setGraduacao(graduacao);
        vinculo.setPlano(plano);
        vinculo.setDataInicio(dataInicio);
        vinculo.setDataFim(null);

        return MatriculaModalidadeResponse.fromEntity(
                matriculaModalidadeRepository.save(vinculo)
        );
    }

    @Transactional(readOnly = true)
    public List<MatriculaModalidadeResponse> listarPorMatricula(Long matriculaId) {
        buscarMatricula(matriculaId);

        return matriculaModalidadeRepository
                .findByMatriculaIdOrderByDataInicioDesc(matriculaId)
                .stream()
                .map(MatriculaModalidadeResponse::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public MatriculaModalidadeResponse buscarPorId(Long matriculaId, Long vinculoId) {
        return MatriculaModalidadeResponse.fromEntity(
                buscarVinculo(matriculaId, vinculoId)
        );
    }

    private Matricula buscarMatricula(Long matriculaId) {
        return matriculaRepository.findById(matriculaId)
                .orElseThrow(() -> new MatriculaNotFound("Matricula nao encontrada."));
    }

    private Modalidade buscarModalidade(Long modalidadeId) {
        return modalidadeRepository.findById(modalidadeId)
                .orElseThrow(() -> new RegraDeNegocioException("Modalidade nao encontrada."));
    }

    private Graduacao buscarGraduacao(Long graduacaoId) {
        return graduacaoRepository.findById(graduacaoId)
                .orElseThrow(() -> new RegraDeNegocioException("Graduacao nao encontrada."));
    }

    private Plano buscarPlano(Long planoId) {
        return planoRepository.findById(planoId)
                .orElseThrow(() -> new RegraDeNegocioException("Plano nao encontrado."));
    }

    private MatriculaModalidade buscarVinculo(Long matriculaId, Long vinculoId) {
        return matriculaModalidadeRepository.findByIdAndMatriculaId(vinculoId, matriculaId)
                .orElseThrow(() -> new MatriculaModalidadeNotFound(
                        "Modalidade da matricula nao encontrada."
                ));
    }

    private void validarMatriculaAtiva(Matricula matricula) {
        if (matricula.getStatus() != StatusMatricula.ATIVA) {
            throw new RegraDeNegocioException(
                    "Somente matriculas ativas podem receber modalidades."
            );
        }
    }

    private void validarModalidadeAtiva(Modalidade modalidade) {
        if (!Boolean.TRUE.equals(modalidade.getAtiva())) {
            throw new RegraDeNegocioException(
                    "Nao e permitido vincular uma modalidade inativa."
            );
        }
    }

    private void validarGraduacaoDaModalidade(Graduacao graduacao, Modalidade modalidade) {
        if (!graduacao.getModalidade().getId().equals(modalidade.getId())) {
            throw new RegraDeNegocioException(
                    "A graduacao nao pertence a modalidade informada."
            );
        }
    }

    private void validarPlano(Plano plano, Modalidade modalidade) {
        if (!Boolean.TRUE.equals(plano.getAtivo())) {
            throw new RegraDeNegocioException(
                    "Nao e permitido utilizar um plano inativo."
            );
        }

        if (!plano.getModalidade().getId().equals(modalidade.getId())) {
            throw new RegraDeNegocioException(
                    "O plano nao pertence a modalidade informada."
            );
        }
    }

    private void validarModalidadeNaoVinculada(Long matriculaId, Long modalidadeId) {
        boolean modalidadeJaVinculada = matriculaModalidadeRepository
                .existsByMatriculaIdAndModalidadeIdAndDataFimIsNull(
                        matriculaId,
                        modalidadeId
                );

        if (modalidadeJaVinculada) {
            throw new RegraDeNegocioException(
                    "A matricula ja possui essa modalidade ativa."
            );
        }
    }

    private LocalDate definirDataInicio(LocalDate dataInicio) {
        return dataInicio != null ? dataInicio : LocalDate.now();
    }

    private void validarDataInicio(Matricula matricula, LocalDate dataInicio) {
        if (dataInicio.isBefore(matricula.getDataMatricula())) {
            throw new RegraDeNegocioException(
                    "A data de inicio nao pode ser anterior a data da matricula."
            );
        }
    }
}
