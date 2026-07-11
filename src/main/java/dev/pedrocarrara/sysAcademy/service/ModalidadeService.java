package dev.pedrocarrara.sysAcademy.service;

import dev.pedrocarrara.sysAcademy.dto.ModalidadeRequest;
import dev.pedrocarrara.sysAcademy.dto.ModalidadeResponse;
import dev.pedrocarrara.sysAcademy.entity.Modalidade;
import dev.pedrocarrara.sysAcademy.exception.ModalidadeNotFound;
import dev.pedrocarrara.sysAcademy.exception.RegraDeNegocioException;
import dev.pedrocarrara.sysAcademy.repository.ModalidadeRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ModalidadeService {
    private final ModalidadeRepository modalidadeRepository;

    public ModalidadeService(ModalidadeRepository modalidadeRepository) {
        this.modalidadeRepository = modalidadeRepository;
    }
    public ModalidadeResponse criarModalidade(ModalidadeRequest modalidadeRequest) {

        Optional<Modalidade> modalidadeExistente =
                modalidadeRepository.findByNome(modalidadeRequest.nome());

        if (modalidadeExistente.isPresent()) {
            throw new RegraDeNegocioException("Já existe uma modalidade com esse nome.");
        }

        Modalidade modalidade = new Modalidade(
                modalidadeRequest.nome(),
                modalidadeRequest.ativa()
        );

        Modalidade modalidadeSalva = modalidadeRepository.save(modalidade);

        return ModalidadeResponse.fromEntity(modalidadeSalva);
    }
    public ModalidadeResponse buscarPorId(Long id) {
        Modalidade modalidade = modalidadeRepository.findById(id).orElseThrow(()-> new ModalidadeNotFound("" +
                "Não foi encontrado uma modalidade com esse id."));
        return ModalidadeResponse.fromEntity(modalidade);
    }
}
