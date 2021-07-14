package com.dioJavaProject3.model;
import java.io.Serializable;
import lombok.*;
@Getter @Setter @AllArgsConstructor
@NoArgsConstructor @EqualsAndHashCode
@Builder @Entity
public class BancoHoras{
    @AllArgsConstructor @NoArgsConstrutor
    @EqualsAndHashCode @Embeddable
    public class BancoHoras implements Seriazable{
        private long idBancoHoras;
        private long idMovimentacao;
        private long idUsuario;
    }
    @Embeddable @Id
    private BancoHorasId id;
    private LocalDateTime dataTrabalhada;
    private BigDecimal quantidadeHoras;
    private BigDecimal saldoHoras;
}