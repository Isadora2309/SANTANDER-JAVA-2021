package com.dioJavaProject3.model;
import lombok.*;
@Getter @Setter @AllArgsConstructor
@NoArgsConstructor @EqualsAndHashCode
@Builder @Entity @Audited
public class NivelAcesso{
    @Id
    private long id;
    private String descricao;
}