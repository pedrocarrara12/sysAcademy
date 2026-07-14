package dev.pedrocarrara.sysAcademy.repository;

import dev.pedrocarrara.sysAcademy.entity.Graduacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GraduacaoRepository extends JpaRepository<Graduacao, Long> {
    List<Graduacao> findByModalidadeId(Long modalidadeId);

    Optional<Graduacao> findByModalidadeIdAndNomeIgnoreCase(Long modalidadeId, String nome);
    Boolean existsByNomeIgnoreCase(String nome);
}
