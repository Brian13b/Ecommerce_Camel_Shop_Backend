package com.ecommerce.backend.controller;

import com.ecommerce.backend.dto.ProductoCreateDTO;
import com.ecommerce.backend.dto.ProductoDTO;
import com.ecommerce.backend.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/productos")
@CrossOrigin(origins = "*")
public class ProductoController {
    
    @Autowired
    private ProductoService productoService;
    
    // ===== ENDPOINTS PÚBLICOS =====
    
    @GetMapping("/publico")
    public ResponseEntity<List<ProductoDTO>> obtenerProductosActivos() {
        return ResponseEntity.ok(productoService.obtenerProductosActivos());
    }
    
    @GetMapping("/publico/{id}")
    public ResponseEntity<ProductoDTO> obtenerProductoPublico(@PathVariable Long id) {
        try {
            ProductoDTO producto = productoService.obtenerProductoPorId(id);
            if (producto.getActivo()) {
                return ResponseEntity.ok(producto);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/publico/buscar")
    public ResponseEntity<List<ProductoDTO>> buscarProductos(@RequestParam String nombre) {
        return ResponseEntity.ok(productoService.buscarPorNombre(nombre));
    }
    
    @GetMapping("/publico/categoria/{categoriaId}")
    public ResponseEntity<List<ProductoDTO>> obtenerPorCategoria(@PathVariable Long categoriaId) {
        return ResponseEntity.ok(productoService.buscarPorCategoria(categoriaId));
    }
    
    // ===== ENDPOINTS DE ADMINISTRACIÓN =====
    
    @GetMapping("/admin")
    public ResponseEntity<List<ProductoDTO>> obtenerTodosProductos() {
        return ResponseEntity.ok(productoService.obtenerTodosLosProductos());
    }
    
    @GetMapping("/admin/{id}")
    public ResponseEntity<ProductoDTO> obtenerProductoAdmin(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(productoService.obtenerProductoPorId(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping("/admin")
    public ResponseEntity<ProductoDTO> crearProducto(@Valid @RequestBody ProductoCreateDTO dto) {
        try {
            ProductoDTO creado = productoService.crearProducto(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(creado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/admin/{id}")
    public ResponseEntity<ProductoDTO> actualizarProducto(
            @PathVariable Long id, 
            @Valid @RequestBody ProductoCreateDTO dto) {
        try {
            ProductoDTO actualizado = productoService.actualizarProducto(id, dto);
            return ResponseEntity.ok(actualizado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PatchMapping("/admin/{id}/toggle")
    public ResponseEntity<ProductoDTO> toggleEstadoProducto(@PathVariable Long id) {
        try {
            ProductoDTO actualizado = productoService.toggleEstadoProducto(id);
            return ResponseEntity.ok(actualizado);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        try {
            productoService.eliminarProducto(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
