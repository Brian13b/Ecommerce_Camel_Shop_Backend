package com.ecommerce.backend.config;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.ecommerce.backend.model.Categoria;
import com.ecommerce.backend.model.Producto;
import com.ecommerce.backend.model.ProductoVariantes;
import com.ecommerce.backend.model.RolUsuario;
import com.ecommerce.backend.model.Usuario;
import com.ecommerce.backend.repository.CategoriaRepository;
import com.ecommerce.backend.repository.ProductoRepository;
import com.ecommerce.backend.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    private final UsuarioRepository usuarioRepository; 
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        
        // 1. CREAR USUARIO ADMIN
        if (usuarioRepository.findByUsername("admin").isEmpty()) {
            Usuario admin = Usuario.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin123"))
                    .nombreCompleto("Administrador Principal")
                    .email("admin@camelshop.com")
                    .rol(RolUsuario.ADMIN)
                    .activo(true)
                    .build();
            usuarioRepository.save(admin);
            System.out.println("👤 USUARIO ADMIN CREADO: admin / admin123");
        }

        // 2. CARGAR PRODUCTOS Y CATEGORÍAS
        if (categoriaRepository.count() == 0) {
        System.out.println("🔄 Cargando categorías seleccionadas...");

            Categoria remeras = categoriaRepository.save(Categoria.builder().nombre("Remeras").build());
            Categoria vestidos = categoriaRepository.save(Categoria.builder().nombre("Vestidos").build());
            Categoria pantalones = categoriaRepository.save(Categoria.builder().nombre("Pantalones").build());
            Categoria camisas = categoriaRepository.save(Categoria.builder().nombre("Camisas").build());
            Categoria buzos = categoriaRepository.save(Categoria.builder().nombre("Buzos").build());

            // Ejemplo: Creación de un producto por categoría
            crearProductoConVariantes("Remera Oversize", "100% Algodón", new BigDecimal("25000"), remeras, 
                List.of("remera.jpg"), "Negro", Map.of("S", 10, "M", 10, "L", 10));

            crearProductoConVariantes("Vestido Noche", "Saten premium", new BigDecimal("45000"), vestidos, 
                List.of("vestido.jpg"), "Rojo", Map.of("S", 5, "M", 5));

            crearProductoConVariantes("Jean Cargo", "Denim reforzado", new BigDecimal("52000"), pantalones, 
                List.of("pantalones.jpg"), "Gris", Map.of("42", 10, "44", 10));

            crearProductoConVariantes("Camisa Linno", "Corte slim", new BigDecimal("38000"), camisas, 
                List.of("camisa.jpg"), "Blanco", Map.of("M", 8, "L", 8));

            crearProductoConVariantes("Buzo Hoodie", "Frisa pesada", new BigDecimal("42000"), buzos, 
                List.of("buzo.jpg"), "Beige", Map.of("L", 15, "XL", 15));

            System.out.println("✅ Catálogo simplificado cargado.");
        }
    }

    private void crearProductoConVariantes(String nombre, String descripcion, BigDecimal precio, Categoria cat, List<String> imagenes, String color, Map<String, Integer> stockTalles) {
        // Calculamos el stock total sumando los valores del mapa
        int stockTotal = stockTalles.values().stream().mapToInt(Integer::intValue).sum();

        Producto p = Producto.builder()
                .nombre(nombre)
                .descripcion(descripcion)
                .precio(precio)
                .stock(stockTotal)
                .categoria(cat)
                .activo(true)
                .imagenes(imagenes)
                .build();

        // Creamos la variante asociada
        ProductoVariantes variante = ProductoVariantes.builder()
                .color(color)
                .stockPorTalle(stockTalles)
                .producto(p)
                .build();

        p.setVariantes(new ArrayList<>(List.of(variante)));
        
        productoRepository.save(p);
    }
}