package dev.pedrocarrara.sysAcademy.specification;

import dev.pedrocarrara.sysAcademy.entity.Modalidade;
import org.springframework.data.jpa.domain.Specification;

import java.util.Locale;

public final class ModalidadeSpecification {

    private static final String NOME = "nome";
    private static final String ATIVA = "ativa";

    private ModalidadeSpecification() {
    }

    public static Specification<Modalidade> nomeContem(String nome) {
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

    public static Specification<Modalidade> ativaIgual(Boolean ativa) {
        return (root, query, criteriaBuilder) -> {
            if (ativa == null) {
                return semFiltro().toPredicate(root, query, criteriaBuilder);
            }

            return criteriaBuilder.equal(root.get(ATIVA), ativa);
        };
    }

    private static Specification<Modalidade> semFiltro() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
    }

    private static boolean isBlank(String valor) {
        return valor == null || valor.isBlank();
    }

    private static String normalizar(String valor) {
        return valor.trim().toLowerCase(Locale.ROOT);
    }
}
