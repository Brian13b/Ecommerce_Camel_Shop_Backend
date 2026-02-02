package com.ecommerce.backend.dto;

import lombok.Data;
import java.util.List;

@Data
public class PedidoCreateDTO {
    private String nombreCliente;
    private String telefono;
    private String direccionEnvio;
    private String metodoPago;
    private List<ItemPedidoDTO> items;

    @Data
    public static class ItemPedidoDTO {
        private Long productoId;
        private Integer cantidad;
        private String talle;
    }
}