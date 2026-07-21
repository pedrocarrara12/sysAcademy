package dev.pedrocarrara.sysAcademy.specification;

import dev.pedrocarrara.sysAcademy.dto.PlanoFiltro;
import dev.pedrocarrara.sysAcademy.entity.Plano;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.Locale;

public final class PlanoSpecification {

    private static final String MODALIDADE = "modalidade";
    private static final String ID = "id";
    private static final String NOME = "nome";
    private static final String VALOR_MENSAL = "valorMensal";
    private static final String ATIVO = "ativo";

    private PlanoSpecification() {
    }

    public static Specification<Plano> comFiltros(PlanoFiltro filtro) {
        if (filtro == null) {
            return semFiltro();
        }

        return modalidadeIdIgual(filtro.modalidadeId())
                .and(nomeContem(filtro.nome()))
                .and(valorMensalMaiorOuIgual(filtro.valorMensalDe()))
                .and(valorMensalMenorOuIgual(filtro.valorMensalAte()))
                .and(ativoIgual(filtro.ativo()));
    }

    public static Specification<Plano> modalidadeIdIgual(Long modalidadeId) {
        return (root, query, criteriaBuilder) -> {
            if (modalidadeId == null) {
                return semFiltro().toPredicate(root, query, criteriaBuilder);
            }

            return criteriaBuilder.equal(root.get(MODALIDADE).get(ID), modalidadeId);
        };
    }

    public static Specification<Plano> nomeContem(String nome) {
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

    public static Specification<Plano> valorMensalMaiorOuIgual(BigDecimal valor) {
        return (root, query, criteriaBuilder) -> {
            if (valor == null) {
                return semFiltro().toPredicate(root, query, criteriaBuilder);
            }

            return criteriaBuilder.greaterThanOrEqualTo(root.get(VALOR_MENSAL), valor);
        };
    }

    public static Specification<Plano> valorMensalMenorOuIgual(BigDecimal valor) {
        return (root, query, criteriaBuilder) -> {
            if (valor == null) {
                return semFiltro().toPredicate(root, query, criteriaBuilder);
            }

            return criteriaBuilder.lessThanOrEqualTo(root.get(VALOR_MENSAL), valor);
        };
    }

    public static Specification<Plano> ativoIgual(Boolean ativo) {
        return (root, query, criteriaBuilder) -> {
            if (ativo == null) {
                return semFiltro().toPredicate(root, query, criteriaBuilder);
            }

            return criteriaBuilder.equal(root.get(ATIVO), ativo);
        };
    }

    private static Specification<Plano> semFiltro() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
    }

    private static boolean isBlank(String valor) {
        return valor == null || valor.isBlank();
    }

    private static String normalizar(String valor) {
        return valor.trim().toLowerCase(Locale.ROOT);
    }
}
