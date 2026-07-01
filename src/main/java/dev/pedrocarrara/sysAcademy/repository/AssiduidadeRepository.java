package dev.pedrocarrara.sysAcademy.repository;

import dev.pedrocarrara.sysAcademy.entity.Assiduidade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AssiduidadeRepository extends JpaRepository<Assiduidade, Long> {
    List<Assiduidade> findByMatriculaId(Long matriculaId);

    List<Assiduidade> findByMatriculaAlunoId(Long alunoId);

    List<Assiduidade> findByDataEntradaBetween(LocalDateTime inicio, LocalDateTime fim);

    List<Assiduidade> findByMatriculaIdAndDataEntradaBetween(Long matriculaId, LocalDateTime inicio, LocalDateTime fim);
}
