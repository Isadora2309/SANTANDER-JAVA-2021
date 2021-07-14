package com.dioJavaProject3.model;
import lombok.*;
@Getter @Setter @AllArgsConstructor
@NoArgsConstructor @EqualsAndHashCode
@Builder @Entity
public class Calendario{
    @Id
    private Long id;
    private TipoData tipoData;
    private String descricao;
    private LocalDateTime dataEspecial;
}