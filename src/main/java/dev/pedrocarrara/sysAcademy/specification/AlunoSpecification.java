package dev.pedrocarrara.sysAcademy.specification;

import dev.pedrocarrara.sysAcademy.dto.AlunoFiltro;
import dev.pedrocarrara.sysAcademy.entity.Aluno;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.Locale;

public final class AlunoSpecification {

    private static final String NOME = "nome";
    private static final String CPF = "cpf";
    private static final String EMAIL = "email";
    private static final String CIDADE = "cidade";
    private static final String ESTADO = "estado";
    private static final String SEXO = "sexo";
    private static final String DATA_NASCIMENTO = "dataNascimento";

    private AlunoSpecification() {
    }

    public static Specification<Aluno> comFiltros(AlunoFiltro filtro) {
        if (filtro == null) {
            return semFiltro();
        }

        return nomeContem(filtro.nome())
                .and(cpfIgual(filtro.cpf()))
                .and(emailIgual(filtro.email()))
                .and(cidadeIgual(filtro.cidade()))
                .and(estadoIgual(filtro.estado()))
                .and(sexoIgual(filtro.sexo()))
                .and(dataNascimentoMaiorOuIgual(filtro.nascimentoDe()))
                .and(dataNascimentoMenorOuIgual(filtro.nascimentoAte()));
    }

    public static Specification<Aluno> nomeContem(String nome) {
        return (root, query, criteriaBuilder) -> {
            if (isBlank(nome)) {
                return semFiltro().toPredicate(root, query, criteriaBuilder);
            }

            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get(NOME)),
                    "%" + normalizar(nome) + "%"
            );
        };
    }

    public static Specification<Aluno> cpfIgual(String cpf) {
        return igualdade(CPF, cpf);
    }

    public static Specification<Aluno> emailIgual(String email) {
        return igualdadeIgnoreCase(EMAIL, email);
    }

    public static Specification<Aluno> cidadeIgual(String cidade) {
        return igualdadeIgnoreCase(CIDADE, cidade);
    }

    public static Specification<Aluno> estadoIgual(String estado) {
        return igualdadeIgnoreCase(ESTADO, estado);
    }

    public static Specification<Aluno> sexoIgual(String sexo) {
        return igualdadeIgnoreCase(SEXO, sexo);
    }

    public static Specification<Aluno> dataNascimentoMaiorOuIgual(LocalDate data) {
        return (root, query, criteriaBuilder) -> {
            if (data == null) {
                return semFiltro().toPredicate(root, query, criteriaBuilder);
            }

            return criteriaBuilder.greaterThanOrEqualTo(root.get(DATA_NASCIMENTO), data);
        };
    }

    public static Specification<Aluno> dataNascimentoMenorOuIgual(LocalDate data) {
        return (root, query, criteriaBuilder) -> {
            if (data == null) {
                return semFiltro().toPredicate(root, query, criteriaBuilder);
            }

            return criteriaBuilder.lessThanOrEqualTo(root.get(DATA_NASCIMENTO), data);
        };
    }

    private static Specification<Aluno> igualdade(String campo, String valor) {
        return (root, query, criteriaBuilder) -> {
            if (isBlank(valor)) {
                return semFiltro().toPredicate(root, query, criteriaBuilder);
            }

            return criteriaBuilder.equal(root.get(campo), valor.trim());
        };
    }

    private static Specification<Aluno> igualdadeIgnoreCase(String campo, String valor) {
        return (root, query, criteriaBuilder) -> {
            if (isBlank(valor)) {
                return semFiltro().toPredicate(root, query, criteriaBuilder);
            }

            return criteriaBuilder.equal(
                    criteriaBuilder.lower(root.get(campo)),
                    normalizar(valor)
            );
        };
    }

    private static Specification<Aluno> semFiltro() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
    }

    private static boolean isBlank(String valor) {
        return valor == null || valor.isBlank();
    }

    private static String normalizar(String valor) {
        return valor.trim().toLowerCase(Locale.ROOT);
    }
}
