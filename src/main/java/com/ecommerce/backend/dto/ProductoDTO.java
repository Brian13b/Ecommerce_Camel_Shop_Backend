package com.ecommerce.backend.dto;

import lombok.*;
import java.math.BigDecimal;
import java.util.Set;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private Integer stock;
    private List<String> imagenes;
    private Boolean activo;
    private Long categoriaId;
    private String categoriaNombre;
    private String tipoTalle;
    private Set<String> talles;
}
