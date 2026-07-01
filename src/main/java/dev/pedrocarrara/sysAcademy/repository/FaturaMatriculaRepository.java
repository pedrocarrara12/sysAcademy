package dev.pedrocarrara.sysAcademy.repository;

import dev.pedrocarrara.sysAcademy.entity.FaturaMatricula;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface FaturaMatriculaRepository extends JpaRepository<FaturaMatricula, Long> {
    List<FaturaMatricula> findByMatriculaId(Long matriculaId);

    List<FaturaMatricula> findByMatriculaAlunoId(Long alunoId);

    List<FaturaMatricula> findByStatus(String status);

    List<FaturaMatricula> findByDataVencimentoBetween(LocalDate inicio, LocalDate fim);

    List<FaturaMatricula> findByStatusAndDataVencimentoBefore(String status, LocalDate data);

    Optional<FaturaMatricula> findByMatriculaIdAndDataVencimento(Long matriculaId, LocalDate dataVencimento);
}
