package com.dioJavaProject3.model;
import lombok.*;
@Getter @Setter @AllArgsConstructor
@NoArgsConstructor @EqualsAndHashCode
@Builder @Entity @Audited
public class jornadaTrabalho{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String descricao;
}