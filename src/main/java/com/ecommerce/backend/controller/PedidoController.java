package com.ecommerce.backend.controller;

import com.ecommerce.backend.dto.PedidoCreateDTO;
import com.ecommerce.backend.model.EstadoPedido;
import com.ecommerce.backend.model.Pedido;
import com.ecommerce.backend.service.PedidoService;
import com.ecommerce.backend.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;
    private final FileStorageService fileService;

    // Crear pedido
    @PostMapping("/publico")
    public ResponseEntity<Pedido> crearPedido(@RequestBody PedidoCreateDTO dto) {
        return ResponseEntity.ok(pedidoService.crearPedido(dto));
    }

    // Listar pedidos
    @GetMapping("/admin")
    public ResponseEntity<List<Pedido>> listarPedidos() {
        return ResponseEntity.ok(pedidoService.obtenerTodos());
    }

    // Cambiar estado
    @PatchMapping("/admin/{id}/estado")
    public ResponseEntity<Pedido> cambiarEstado(@PathVariable Long id, @RequestParam EstadoPedido estado) {
        return ResponseEntity.ok(pedidoService.cambiarEstado(id, estado));
    }

    @PostMapping("/admin/{id}/factura")
    public ResponseEntity<Pedido> subirFactura(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        String nombreArchivo = fileService.storeFile(file);
        
        Pedido pedido = pedidoService.getPedidoById(id); 
        pedido.setFacturaUrl(nombreArchivo);

        Pedido actualizado = pedidoService.actualizarPedido(pedido); 
        
        return ResponseEntity.ok(actualizado);
    }
}