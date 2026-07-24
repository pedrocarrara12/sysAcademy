package dev.pedrocarrara.sysAcademy.specification;

import dev.pedrocarrara.sysAcademy.entity.Matricula;
import dev.pedrocarrara.sysAcademy.enums.StatusMatricula;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.Locale;

public final class MatriculaSpecification {

    private static final String ALUNO = "aluno";
    private static final String ID = "id";
    private static final String NOME = "nome";
    private static final String DATA_MATRICULA = "dataMatricula";
    private static final String DIA_VENCIMENTO = "diaVencimento";
    private static final String DATA_ENCERRAMENTO = "dataEncerramento";
    private static final String STATUS = "status";

    private MatriculaSpecification() {
    }

    public static Specification<Matricula> alunoIdIgual(Long alunoId) {
        return (root, query, criteriaBuilder) -> {
            if (alunoId == null) {
                return semFiltro().toPredicate(root, query, criteriaBuilder);
            }

            return criteriaBuilder.equal(root.get(ALUNO).get(ID), alunoId);
        };
    }

    public static Specification<Matricula> alunoNomeContem(String nome) {
        return (root, query, criteriaBuilder) -> {
            if (isBlank(nome)) {
                return semFiltro().toPredicate(root, query, criteriaBuilder);
            }

            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get(ALUNO).get(NOME)),
                    "%" + normalizar(nome) + "%"
            );
        };
    }

    public static Specification<Matricula> dataMatriculaMaiorOuIgual(LocalDate data) {
        return (root, query, criteriaBuilder) -> {
            if (data == null) {
                return semFiltro().toPredicate(root, query, criteriaBuilder);
            }

            return criteriaBuilder.greaterThanOrEqualTo(root.get(DATA_MATRICULA), data);
        };
    }

    public static Specification<Matricula> dataMatriculaMenorOuIgual(LocalDate data) {
        return (root, query, criteriaBuilder) -> {
            if (data == null) {
                return semFiltro().toPredicate(root, query, criteriaBuilder);
            }

            return criteriaBuilder.lessThanOrEqualTo(root.get(DATA_MATRICULA), data);
        };
    }

    public static Specification<Matricula> diaVencimentoIgual(Integer diaVencimento) {
        return (root, query, criteriaBuilder) -> {
            if (diaVencimento == null) {
                return semFiltro().toPredicate(root, query, criteriaBuilder);
            }

            return criteriaBuilder.equal(root.get(DIA_VENCIMENTO), diaVencimento);
        };
    }

    public static Specification<Matricula> dataEncerramentoMaiorOuIgual(LocalDate data) {
        return (root, query, criteriaBuilder) -> {
            if (data == null) {
                return semFiltro().toPredicate(root, query, criteriaBuilder);
            }

            return criteriaBuilder.greaterThanOrEqualTo(root.get(DATA_ENCERRAMENTO), data);
        };
    }

    public static Specification<Matricula> dataEncerramentoMenorOuIgual(LocalDate data) {
        return (root, query, criteriaBuilder) -> {
            if (data == null) {
                return semFiltro().toPredicate(root, query, criteriaBuilder);
            }

            return criteriaBuilder.lessThanOrEqualTo(root.get(DATA_ENCERRAMENTO), data);
        };
    }

    public static Specification<Matricula> statusIgual(StatusMatricula status) {
        return (root, query, criteriaBuilder) -> {
            if (status == null) {
                return semFiltro().toPredicate(root, query, criteriaBuilder);
            }

            return criteriaBuilder.equal(root.get(STATUS), status);
        };
    }

    private static Specification<Matricula> semFiltro() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
    }

    private static boolean isBlank(String valor) {
        return valor == null || valor.isBlank();
    }

    private static String normalizar(String valor) {
        return valor.trim().toLowerCase(Locale.ROOT);
    }
}
