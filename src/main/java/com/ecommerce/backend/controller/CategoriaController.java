package com.ecommerce.backend.controller;

import com.ecommerce.backend.dto.CategoriaDTO;
import com.ecommerce.backend.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/categorias")
@CrossOrigin(origins = "*")
public class CategoriaController {
    
    @Autowired
    private CategoriaService categoriaService;
    
    // ENDPOINT PÚBLICO
    @GetMapping("/publico")
    public ResponseEntity<List<CategoriaDTO>> obtenerCategorias() {
        return ResponseEntity.ok(categoriaService.obtenerTodasLasCategorias());
    }
    
    // ENDPOINTS DE ADMINISTRACIÓN
    @GetMapping("/admin")
    public ResponseEntity<List<CategoriaDTO>> obtenerCategoriasAdmin() {
        return ResponseEntity.ok(categoriaService.obtenerTodasLasCategorias());
    }
    
    @PostMapping("/admin")
    public ResponseEntity<CategoriaDTO> crearCategoria(@RequestBody Map<String, String> request) {
        try {
            String nombre = request.get("nombre");
            String descripcion = request.get("descripcion");
            CategoriaDTO creada = categoriaService.crearCategoria(nombre, descripcion);
            return ResponseEntity.status(HttpStatus.CREATED).body(creada);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/admin/{id}")
    public ResponseEntity<CategoriaDTO> actualizarCategoria(
            @PathVariable Long id, 
            @RequestBody Map<String, String> request) {
        try {
            String nombre = request.get("nombre");
            String descripcion = request.get("descripcion");
            CategoriaDTO actualizada = categoriaService.actualizarCategoria(id, nombre, descripcion);
            return ResponseEntity.ok(actualizada);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Void> eliminarCategoria(@PathVariable Long id) {
        try {
            categoriaService.eliminarCategoria(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
