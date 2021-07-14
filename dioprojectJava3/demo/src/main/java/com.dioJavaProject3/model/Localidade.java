package com.dioJavaProject3.model;
import lombok.*;
@Getter @Setter @AllArgsConstructor
@NoArgsConstructor @EqualsAndHashCode
@Builder @Entity
public class Localidade{
    @Id
    private long id;
    @ManyToOne
    private NivelAcesso nivelAcesso;
    private String descricao;
}