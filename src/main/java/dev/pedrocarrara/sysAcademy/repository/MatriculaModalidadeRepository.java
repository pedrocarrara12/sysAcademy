package dev.pedrocarrara.sysAcademy.repository;

import dev.pedrocarrara.sysAcademy.entity.MatriculaModalidade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MatriculaModalidadeRepository extends JpaRepository<MatriculaModalidade, Long> {
    List<MatriculaModalidade> findByMatriculaId(Long matriculaId);

    List<MatriculaModalidade> findByModalidadeId(Long modalidadeId);

    List<MatriculaModalidade> findByMatriculaAlunoId(Long alunoId);

    List<MatriculaModalidade> findByDataFimIsNull();

    Optional<MatriculaModalidade> findByMatriculaIdAndModalidadeId(Long matriculaId, Long modalidadeId);

    List<MatriculaModalidade> findByMatriculaIdOrderByDataInicioDesc(Long matriculaId);

    Optional<MatriculaModalidade> findByIdAndMatriculaId(Long id, Long matriculaId);

    boolean existsByMatriculaIdAndModalidadeIdAndDataFimIsNull(Long matriculaId, Long modalidadeId);
}
