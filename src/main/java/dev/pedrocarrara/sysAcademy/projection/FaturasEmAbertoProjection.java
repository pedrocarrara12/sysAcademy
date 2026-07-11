package dev.pedrocarrara.sysAcademy.projection;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface FaturasEmAbertoProjection {

    Long getMatriculaId();
    Long getAlunoNome();
    LocalDate getDataVencimento();
    BigDecimal getValor();

}
