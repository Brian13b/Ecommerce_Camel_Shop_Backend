package com.ecommerce.backend.repository;

import com.ecommerce.backend.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    
    // Buscar productos activos
    List<Producto> findByActivoTrue();
    
    // Buscar por categoría
    List<Producto> findByCategoriaIdAndActivoTrue(Long categoriaId);
    
    // Buscar por nombre (búsqueda parcial)
    @Query("SELECT p FROM Producto p WHERE LOWER(p.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')) AND p.activo = true")
    List<Producto> buscarPorNombre(@Param("nombre") String nombre);
    
    // Buscar por nombre y categoría
    @Query("SELECT p FROM Producto p WHERE LOWER(p.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')) " +
           "AND p.categoria.id = :categoriaId AND p.activo = true")
    List<Producto> buscarPorNombreYCategoria(@Param("nombre") String nombre, 
                                             @Param("categoriaId") Long categoriaId);
}
