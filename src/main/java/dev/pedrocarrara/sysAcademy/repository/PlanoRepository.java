package dev.pedrocarrara.sysAcademy.repository;

import dev.pedrocarrara.sysAcademy.entity.Plano;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlanoRepository extends JpaRepository<Plano, Long> {
    List<Plano> findByModalidadeId(Long modalidadeId);

    List<Plano> findByAtivoTrue();

    List<Plano> findByModalidadeIdAndAtivoTrue(Long modalidadeId);

    Optional<Plano> findByModalidadeIdAndNomeIgnoreCase(Long modalidadeId, String nome);
}
