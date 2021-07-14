package com.dioJavaProject3.model;
import lombok.*;
@Getter @Setter @AllArgsConstructor
@NoArgsConstructor @EqualsAndHashCode
@Builder @Entity
public class Ocorrencia{
    @Id
    private long id;
    private String nome;
    private String descricao;
}