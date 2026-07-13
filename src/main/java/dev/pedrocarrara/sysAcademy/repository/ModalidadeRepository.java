package dev.pedrocarrara.sysAcademy.repository;

import dev.pedrocarrara.sysAcademy.entity.Modalidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface ModalidadeRepository extends JpaRepository<Modalidade, Long>, JpaSpecificationExecutor<Modalidade> {
    Optional<Modalidade> findByNomeIgnoreCase(String nome);

    List<Modalidade> findByAtivaTrue();

    Optional<Modalidade> findByNome(String nome);
}
