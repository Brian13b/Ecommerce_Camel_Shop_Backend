package com.ecommerce.backend.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "detalles_pedido")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetallePedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    @JsonIgnoreProperties({"descripcion", "activo", "fechaCreacion", "fechaActualizacion", "variantes", "categoria"})
    private Producto producto;

    private Integer cantidad;
    
    @Column(name = "talle_seleccionado")
    private String talleSeleccionado;
    
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;
}