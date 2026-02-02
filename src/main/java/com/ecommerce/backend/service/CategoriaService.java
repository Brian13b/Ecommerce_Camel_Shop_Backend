package com.ecommerce.backend.service;

import com.ecommerce.backend.dto.CategoriaDTO;
import com.ecommerce.backend.model.Categoria;
import com.ecommerce.backend.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaService {
    
    @Autowired
    private CategoriaRepository categoriaRepository;
    
    public List<CategoriaDTO> obtenerTodasLasCategorias() {
        return categoriaRepository.findAll()
            .stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }
    
    public CategoriaDTO obtenerCategoriaPorId(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
        return convertirADTO(categoria);
    }
    
    @Transactional
    public CategoriaDTO crearCategoria(String nombre, String descripcion) {
        if (categoriaRepository.existsByNombre(nombre)) {
            throw new RuntimeException("Ya existe una categoría con ese nombre");
        }
        
        Categoria categoria = Categoria.builder()
            .nombre(nombre)
            .descripcion(descripcion)
            .build();
        
        Categoria guardada = categoriaRepository.save(categoria);
        return convertirADTO(guardada);
    }
    
    @Transactional
    public CategoriaDTO actualizarCategoria(Long id, String nombre, String descripcion) {
        Categoria categoria = categoriaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
        
        categoria.setNombre(nombre);
        categoria.setDescripcion(descripcion);
        
        Categoria actualizada = categoriaRepository.save(categoria);
        return convertirADTO(actualizada);
    }
    
    @Transactional
    public void eliminarCategoria(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
        
        if (categoria.getProductos() != null && !categoria.getProductos().isEmpty()) {
            throw new RuntimeException("No se puede eliminar una categoría con productos asociados");
        }
        
        categoriaRepository.deleteById(id);
    }
    
    private CategoriaDTO convertirADTO(Categoria categoria) {
        return CategoriaDTO.builder()
            .id(categoria.getId())
            .nombre(categoria.getNombre())
            .descripcion(categoria.getDescripcion())
            .cantidadProductos(categoria.getProductos() != null ? categoria.getProductos().size() : 0)
            .build();
    }
}
