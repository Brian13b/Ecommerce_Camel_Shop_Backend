package com.ecommerce.backend.config;

import com.ecommerce.backend.model.*;
import com.ecommerce.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder; // <--- Necesario para la password
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    private final UsuarioRepository usuarioRepository; 
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        
        // -----------------------------------------------------------
        // 1. CREAR USUARIO ADMIN (Si no existe)
        // -----------------------------------------------------------
        if (usuarioRepository.findByUsername("admin").isEmpty()) {
            Usuario admin = Usuario.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin123")) // Contraseña: admin123
                    .nombreCompleto("Administrador Principal")
                    .email("admin@camelshop.com")
                    .rol(RolUsuario.ADMIN)
                    .activo(true)
                    .build();
            usuarioRepository.save(admin);
            System.out.println("👤 USUARIO ADMIN CREADO: admin / admin123");
        }

        // -----------------------------------------------------------
        // 2. CARGAR PRODUCTOS Y CATEGORÍAS (Si la base está vacía)
        // -----------------------------------------------------------
        if (categoriaRepository.count() == 0) {
            System.out.println("🔄 Iniciando carga de catálogo...");

            // --- Categorías ---
            Categoria remeras = categoriaRepository.save(Categoria.builder().nombre("Remeras").descripcion("Urbano y casual").build());
            Categoria pantalones = categoriaRepository.save(Categoria.builder().nombre("Pantalones").descripcion("Denim y Cargo").build());
            Categoria calzado = categoriaRepository.save(Categoria.builder().nombre("Calzado").descripcion("Sneakers y Botas").build());
            Categoria accesorios = categoriaRepository.save(Categoria.builder().nombre("Accesorios").descripcion("Complementos").build());

            // --- PRODUCTOS (15 Items con Galería) ---

            // REMERAS
            crearProducto("Remera Oversize Black", "Algodón 100% heavyweight.", new BigDecimal("28000"), 50, remeras, "ROPA", Set.of("S", "M", "L", "XL"), Arrays.asList(
                "https://images.unsplash.com/photo-1583743814966-8936f5b7be1a?w=800&q=80",
                "https://images.unsplash.com/photo-1576566588028-4147f3842f27?w=800&q=80", 
                "https://images.unsplash.com/photo-1503341504253-dff4815485f1?w=800&q=80"
            ));

            crearProducto("Remera Graphic White", "Estampado minimalista.", new BigDecimal("25000"), 40, remeras, "ROPA", Set.of("S", "M", "L"), Arrays.asList(
                "https://images.unsplash.com/photo-1521572163474-6864f9cf17ab?w=800&q=80",
                "https://images.unsplash.com/photo-1581655353564-df123a1eb820?w=800&q=80",
                "https://images.unsplash.com/photo-1562157873-818bc0726f68?w=800&q=80"
            ));

            crearProducto("Musculosa Basic Grey", "Tela transpirable.", new BigDecimal("18000"), 30, remeras, "ROPA", Set.of("M", "L", "XL"), Arrays.asList(
                "https://images.unsplash.com/photo-1618354691373-d851c5c3a990?w=800&q=80",
                "https://images.unsplash.com/photo-1596482163626-444f9c5d1370?w=800&q=80",
                "https://images.unsplash.com/photo-1571217621419-4ba615174006?w=800&q=80"
            ));
            
            crearProducto("Chomba Piqué Navy", "Estilo clásico.", new BigDecimal("32000"), 25, remeras, "ROPA", Set.of("M", "L", "XL", "XXL"), Arrays.asList(
                "https://images.unsplash.com/photo-1626557981101-aae6f84aa6ff?w=800&q=80",
                "https://images.unsplash.com/photo-1586363104862-3a5e2ab60d99?w=800&q=80",
                "https://images.unsplash.com/photo-1554568218-0f1715e72254?w=800&q=80"
            ));

            // PANTALONES
            crearProducto("Jean Slim Fit Blue", "Denim con elastano.", new BigDecimal("45000"), 40, pantalones, "ROPA", Set.of("38", "40", "42", "44"), Arrays.asList(
                "https://images.unsplash.com/photo-1542272617-08f08630329f?w=800&q=80",
                "https://images.unsplash.com/photo-1541099649105-f69ad21f3246?w=800&q=80",
                "https://images.unsplash.com/photo-1475178626620-a4d074967452?w=800&q=80"
            ));

            crearProducto("Cargo Pant Black", "6 bolsillos, ripstop.", new BigDecimal("52000"), 35, pantalones, "ROPA", Set.of("40", "42", "44", "46"), Arrays.asList(
                "https://images.unsplash.com/photo-1624378439575-d8705ad7ae80?w=800&q=80",
                "https://images.unsplash.com/photo-1552902865-b72c031ac5ea?w=800&q=80",
                "https://images.unsplash.com/photo-1517438476312-10d79c077509?w=800&q=80"
            ));
            
            crearProducto("Jogger Streetwear", "Algodón rústico.", new BigDecimal("38000"), 50, pantalones, "ROPA", Set.of("S", "M", "L", "XL"), Arrays.asList(
                "https://images.unsplash.com/photo-1555689502-c4b22d76c56f?w=800&q=80",
                "https://images.unsplash.com/photo-1483985988355-763728e1935b?w=800&q=80",
                "https://images.unsplash.com/photo-1515886657613-9f3515b0c78f?w=800&q=80"
            ));
            
            crearProducto("Bermuda Denim", "Corte clásico.", new BigDecimal("35000"), 30, pantalones, "ROPA", Set.of("40", "42", "44"), Arrays.asList(
                "https://images.unsplash.com/photo-1565545284242-4d2a106f376f?w=800&q=80",
                "https://images.unsplash.com/photo-1591195853828-11db59a44f6b?w=800&q=80",
                "https://images.unsplash.com/photo-1563630423918-b58f07336ac9?w=800&q=80"
            ));

            // CALZADO
            crearProducto("Sneakers High Retro", "Cuero sintético.", new BigDecimal("85000"), 20, calzado, "CALZADO", Set.of("40", "41", "42", "43"), Arrays.asList(
                "https://images.unsplash.com/photo-1549298916-b41d501d3772?w=800&q=80",
                "https://images.unsplash.com/photo-1512374382149-233c42b6a83b?w=800&q=80",
                "https://images.unsplash.com/photo-1595950653106-6c9ebd614d3a?w=800&q=80"
            ));

            crearProducto("Running Sport X", "Suela ultraliviana.", new BigDecimal("75000"), 25, calzado, "CALZADO", Set.of("39", "40", "41", "42", "44"), Arrays.asList(
                "https://images.unsplash.com/photo-1542291026-7eec264c27ff?w=800&q=80",
                "https://images.unsplash.com/photo-1606107557195-0e29a4b5b4aa?w=800&q=80",
                "https://images.unsplash.com/photo-1608231387042-66d1773070a5?w=800&q=80"
            ));
            
            crearProducto("Borcegos Cuero", "100% Cuero vacuno.", new BigDecimal("120000"), 15, calzado, "CALZADO", Set.of("40", "41", "42", "43", "44"), Arrays.asList(
                "https://images.unsplash.com/photo-1638247025967-b4e38f787b76?w=800&q=80",
                "https://images.unsplash.com/photo-1520639888713-7851133b1ed0?w=800&q=80",
                "https://images.unsplash.com/photo-1449505278894-297fdb3edbc1?w=800&q=80"
            ));
            
            crearProducto("Zapatillas Canvas", "Lona reforzada.", new BigDecimal("48000"), 50, calzado, "CALZADO", Set.of("36", "37", "38", "39", "40"), Arrays.asList(
                "https://images.unsplash.com/photo-1525966222134-fcfa99b8ae77?w=800&q=80",
                "https://images.unsplash.com/photo-1560769629-975ec94e6a86?w=800&q=80",
                "https://images.unsplash.com/photo-1600185365926-3a2ce3cdb9eb?w=800&q=80"
            ));

            // ACCESORIOS
            crearProducto("Gorra Trucker Camel", "Visera curva.", new BigDecimal("15000"), 30, accesorios, "UNICO", Set.of(), Arrays.asList(
                "https://images.unsplash.com/photo-1588850561407-ed78c282e89b?w=800&q=80",
                "https://images.unsplash.com/photo-1556306535-0f09a537f0a3?w=800&q=80",
                "https://images.unsplash.com/photo-1534215754734-18e55d13e346?w=800&q=80"
            ));

            crearProducto("Mochila Urbana Tech", "Porta notebook.", new BigDecimal("55000"), 20, accesorios, "UNICO", Set.of(), Arrays.asList(
                "https://images.unsplash.com/photo-1553062407-98eeb64c6a62?w=800&q=80",
                "https://images.unsplash.com/photo-1581605405669-fcdf81165afa?w=800&q=80",
                "https://images.unsplash.com/photo-1491637639811-60e2756cc1c7?w=800&q=80"
            ));

            crearProducto("Pack Medias x3", "Algodón peinado.", new BigDecimal("9500"), 100, accesorios, "UNICO", Set.of(), Arrays.asList(
                "https://images.unsplash.com/photo-1586350977771-b3b0abd50c82?w=800&q=80",
                "https://images.unsplash.com/photo-1595358052062-87349666c04f?w=800&q=80",
                "https://images.unsplash.com/photo-1582966772680-860e372bb558?w=800&q=80"
            ));

            System.out.println("✅ --- CATÁLOGO CARGADO CON ÉXITO ---");
        }
    }

    private void crearProducto(String nombre, String descripcion, BigDecimal precio, Integer stock, Categoria cat, String tipoTalle, Set<String> talles, List<String> imagenes) {
        Producto p = Producto.builder()
                .nombre(nombre)
                .descripcion(descripcion)
                .precio(precio)
                .stock(stock)
                .categoria(cat)
                .activo(true)
                .tipoTalle(tipoTalle)
                .talles(talles)
                .imagenes(imagenes)
                .build();
        productoRepository.save(p);
    }
}