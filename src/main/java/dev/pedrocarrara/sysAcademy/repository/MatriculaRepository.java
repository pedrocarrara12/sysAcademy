package dev.pedrocarrara.sysAcademy.repository;

import dev.pedrocarrara.sysAcademy.entity.Matricula;
import dev.pedrocarrara.sysAcademy.enums.StatusMatricula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface MatriculaRepository extends JpaRepository<Matricula, Long>, JpaSpecificationExecutor<Matricula> {
    List<Matricula> findByAlunoId(Long alunoId);

    List<Matricula> findByStatus(StatusMatricula status);

    List<Matricula> findByAlunoIdAndStatus(Long alunoId, StatusMatricula status);

    boolean existsByAlunoIdAndStatus(Long alunoId, StatusMatricula statusMatricula);
}
