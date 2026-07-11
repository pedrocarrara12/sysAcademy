package dev.pedrocarrara.sysAcademy.repository;

import dev.pedrocarrara.sysAcademy.entity.Aluno;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AlunoRepository extends JpaRepository<Aluno, Long>, JpaSpecificationExecutor<Aluno> {
    Optional<Aluno> findByCpf(String cpf);

    Optional<Aluno> findByEmail(String email);

    List<Aluno> findByNomeContainingIgnoreCase(String nome);

    boolean existsByEmail(String email);
}
