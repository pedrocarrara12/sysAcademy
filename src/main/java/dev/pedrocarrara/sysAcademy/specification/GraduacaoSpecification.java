package dev.pedrocarrara.sysAcademy.specification;

import dev.pedrocarrara.sysAcademy.entity.Graduacao;
import org.springframework.data.jpa.domain.Specification;

import java.util.Locale;

public final class GraduacaoSpecification {

    private static final String NOME = "nome";
    private static final String MODALIDADE = "modalidade";
    private static final String ID = "id";

    private GraduacaoSpecification() {
    }

    public static Specification<Graduacao> nomeContem(String nome) {
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

    public static Specification<Graduacao> modalidadeIdIgual(Long modalidadeId) {
        return (root, query, criteriaBuilder) -> {
            if (modalidadeId == null) {
                return semFiltro().toPredicate(root, query, criteriaBuilder);
            }

            return criteriaBuilder.equal(root.get(MODALIDADE).get(ID), modalidadeId);
        };
    }

    private static Specification<Graduacao> semFiltro() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
    }

    private static boolean isBlank(String valor) {
        return valor == null || valor.isBlank();
    }

    private static String normalizar(String valor) {
        return valor.trim().toLowerCase(Locale.ROOT);
    }
}
