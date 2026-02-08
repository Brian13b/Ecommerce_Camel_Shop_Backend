package com.ecommerce.backend.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.backend.dto.ProductoCreateDTO;
import com.ecommerce.backend.dto.ProductoDTO;
import com.ecommerce.backend.model.Categoria;
import com.ecommerce.backend.model.Producto;
import com.ecommerce.backend.model.ProductoVariantes;
import com.ecommerce.backend.repository.CategoriaRepository;
import com.ecommerce.backend.repository.ProductoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductoService {
    
    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    
    // Obtener todos los productos activos
    public List<ProductoDTO> obtenerProductosActivos() {
        return productoRepository.findByActivoTrue()
            .stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }
    
    // Obtener todos los productos (para admin)
    public List<ProductoDTO> obtenerTodosLosProductos() {
        return productoRepository.findAll()
            .stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }
    
    // Buscar productos por nombre
    public List<ProductoDTO> buscarPorNombre(String nombre) {
        return productoRepository.buscarPorNombre(nombre)
            .stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }
    
    // Buscar productos por categoría
    public List<ProductoDTO> buscarPorCategoria(Long categoriaId) {
        return productoRepository.findByCategoriaIdAndActivoTrue(categoriaId)
            .stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }
    
    // Obtener producto por ID
    public ProductoDTO obtenerProductoPorId(Long id) {
        Producto producto = productoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));
        return convertirADTO(producto);
    }
    
    // Crear nuevo producto
    @Transactional
    public ProductoDTO crearProducto(ProductoCreateDTO dto) {
        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
            .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
        
        Producto producto = Producto.builder()
            .nombre(dto.getNombre())
            .descripcion(dto.getDescripcion())
            .precio(dto.getPrecio())
            .imagenes(dto.getImagenes())
            .categoria(categoria)
            .activo(true)
            .build();
        
        // Procesar Variantes y calcular Stock Total
        procesarVariantes(producto, dto.getVariantes());
        
        Producto guardado = productoRepository.save(producto);
        return convertirADTO(guardado);
    }
    
    // Actualizar producto
    @Transactional
    public ProductoDTO actualizarProducto(Long id, ProductoCreateDTO dto) {
        Producto producto = productoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());
        producto.setImagenes(dto.getImagenes());
        
        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
            .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
        producto.setCategoria(categoria);

        producto.getVariantes().clear();
        procesarVariantes(producto, dto.getVariantes());
        
        Producto actualizado = productoRepository.save(producto);
        return convertirADTO(actualizado);
    }

    private void procesarVariantes(Producto producto, List<ProductoCreateDTO.VarianteDTO> variantesDto) {
        if (variantesDto == null || variantesDto.isEmpty()) return;

        int stockTotal = 0;
        for (ProductoCreateDTO.VarianteDTO vDto : variantesDto) {
            Map<String, Integer> stockMapa = vDto.getStockPorTalle() != null 
                                            ? vDto.getStockPorTalle() 
                                            : new HashMap<>();

            ProductoVariantes variante = ProductoVariantes.builder()
                .color(vDto.getColor() != null ? vDto.getColor() : "Único")
                .stockPorTalle(stockMapa)
                .build();
            
            producto.addVariante(variante);
            
            stockTotal += stockMapa.values().stream()
                .filter(Objects::nonNull)
                .mapToInt(Integer::intValue).sum();
        }
        producto.setStock(stockTotal);
    }

    private ProductoDTO convertirADTO(Producto producto) {
        List<ProductoDTO.VarianteDTO> variantesDto = producto.getVariantes().stream()
            .map(v -> ProductoDTO.VarianteDTO.builder()
                .color(v.getColor())
                .stockPorTalle(v.getStockPorTalle())
                .build())
            .collect(Collectors.toList());

        return ProductoDTO.builder()
            .id(producto.getId())
            .nombre(producto.getNombre())
            .descripcion(producto.getDescripcion())
            .precio(producto.getPrecio())
            .stock(producto.getStock())
            .imagenes(producto.getImagenes())
            .activo(producto.getActivo())
            .categoriaId(producto.getCategoria().getId())
            .categoriaNombre(producto.getCategoria().getNombre())
            .variantes(variantesDto)
            .build();
    }
    
    // Pausar/Activar producto 
    @Transactional
    public ProductoDTO toggleEstadoProducto(Long id) {
        Producto producto = productoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        
        boolean estadoActual = Boolean.TRUE.equals(producto.getActivo());
        producto.setActivo(!estadoActual);
        
        Producto actualizado = productoRepository.save(producto);
        return convertirADTO(actualizado);
    }
    
    // Eliminar producto
    @Transactional
    public void eliminarProducto(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new RuntimeException("Producto no encontrado");
        }
        productoRepository.deleteById(id);
    }
}