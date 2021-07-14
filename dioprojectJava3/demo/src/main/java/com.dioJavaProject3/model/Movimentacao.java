package com.dioJavaProject3.model;
import javax.persistence.Embeddable;
import java.io.Serializable;
import lombok.*;
@Getter @Setter @AllArgsConstructor
@NoArgsConstructor @EqualsAndHashCode
@Builder @Entity
public class Movimentacao{
    @Embeddable @AllArgsConstructo @NoArgsConstructor
    @EqualsAndHashCode
    public class Movimentacao implements Serializable{
        private long idMovimento;
        private long idUsuario;
    }
    @EmbeddedId @Id
    private MovimentadcaoId id;
    private LocalDateTime dataEntrada;
    private LocalDateTime dataSaida;
    private BigDecimal periodo;
    private Ocorrencia ocorrencia;
    private Calendario calendario;
}