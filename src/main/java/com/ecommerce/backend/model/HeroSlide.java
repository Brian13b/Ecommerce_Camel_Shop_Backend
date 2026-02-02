package com.ecommerce.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "hero_slides")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HeroSlide {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String subtitulo;
    
    @Column(length = 1000)
    private String descripcion;
    
    private String imagenUrl;
    private String botonTexto;
    private String botonLink; 
    
    private String alineacion; 
    
    private Integer orden;
    private Boolean activo;
}