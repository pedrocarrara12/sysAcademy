package dev.pedrocarrara.sysAcademy.repository;

import dev.pedrocarrara.sysAcademy.entity.Plano;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface PlanoRepository extends JpaRepository<Plano, Long>, JpaSpecificationExecutor<Plano> {
    List<Plano> findByModalidadeId(Long modalidadeId);

    List<Plano> findByAtivoTrue();

    List<Plano> findByModalidadeIdAndAtivoTrue(Long modalidadeId);

    Optional<Plano> findByModalidadeIdAndNomeIgnoreCase(Long modalidadeId, String nome);

    boolean existsByNomeIgnoreCase(String nome);
}
