package com.dioJavaProject3.model;
import lombok.*;
@Getter @Setter @AllArgsConstructor
@NoArgsConstructor @EqualsAndHashCode
@Builder @Entity
public class Empresa{
    @Id
    private long id;
    private String descricao;
    private String cnpj;
    private String endereco;
    private String bairro;
    private String cidade;
    private String estado;
    private String telefone;
}