package com.ecommerce.backend.service;

import com.ecommerce.backend.dto.PedidoCreateDTO;
import com.ecommerce.backend.model.*;
import com.ecommerce.backend.repository.PedidoRepository;
import com.ecommerce.backend.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProductoRepository productoRepository;

    @Transactional
    public Pedido crearPedido(PedidoCreateDTO dto) {
        Pedido pedido = new Pedido();
        pedido.setNombreCliente(dto.getNombreCliente());
        pedido.setTelefono(dto.getTelefono());
        pedido.setDireccionEnvio(dto.getDireccionEnvio());
        pedido.setMetodoPago(dto.getMetodoPago());
        pedido.setEstado(EstadoPedido.PENDIENTE);

        List<DetallePedido> detalles = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (PedidoCreateDTO.ItemPedidoDTO itemDto : dto.getItems()) {
            Producto producto = productoRepository.findById(itemDto.getProductoId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + itemDto.getProductoId()));

            if (producto.getStock() < itemDto.getCantidad()) {
                throw new RuntimeException("No hay stock suficiente para: " + producto.getNombre());
            }

            producto.setStock(producto.getStock() - itemDto.getCantidad());
            productoRepository.save(producto);

            BigDecimal precio = producto.getPrecio();
            BigDecimal cantidad = new BigDecimal(itemDto.getCantidad());
            BigDecimal subtotal = precio.multiply(cantidad);

            DetallePedido detalle = DetallePedido.builder()
                    .producto(producto)
                    .cantidad(itemDto.getCantidad())
                    .talleSeleccionado(itemDto.getTalle())
                    .precioUnitario(precio)
                    .subtotal(subtotal)
                    .build();
            
            detalles.add(detalle);
            total = total.add(subtotal);
        }

        pedido.setDetalles(detalles);
        pedido.setTotal(total);

        return pedidoRepository.save(pedido);
    }

    public List<Pedido> obtenerTodos() {
        return pedidoRepository.findAllByOrderByFechaDesc();
    }
    
    public Pedido cambiarEstado(Long id, EstadoPedido nuevoEstado) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        pedido.setEstado(nuevoEstado);
        
        if (nuevoEstado == EstadoPedido.CANCELADO) {
            devolverStock(pedido);
        }
        
        return pedidoRepository.save(pedido);
    }

    private void devolverStock(Pedido pedido) {
        for (DetallePedido detalle : pedido.getDetalles()) {
            Producto p = detalle.getProducto();
            p.setStock(p.getStock() + detalle.getCantidad());
            productoRepository.save(p);
        }
    }

    public Pedido getPedidoById(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con ID: " + id));
    }

    public Pedido actualizarPedido(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }


}