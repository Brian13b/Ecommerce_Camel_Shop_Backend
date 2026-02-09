# Camel Shop API

Backend RESTful desarrollado con **Java 21** y **Spring Boot 3** para la gestión integral de un E-commerce de indumentaria.  
El sistema centraliza la lógica de negocio, la seguridad, la persistencia de datos y la gestión de pedidos de la plataforma.

---

## 📌 Descripción General

Camel Shop API provee una arquitectura robusta y escalable para un comercio electrónico, permitiendo:

- Administración de productos con variantes (talle y color)
- Control de stock en tiempo real
- Gestión transaccional de pedidos
- Seguridad basada en JWT
- Almacenamiento de imágenes en la nube

El proyecto está orientado a entornos productivos y preparado para despliegue continuo.

---

## 🚀 Funcionalidades Principales

- **Inventario Matricial**  
  Manejo avanzado de productos con múltiples variantes y control granular de stock.

- **Autenticación y Autorización**  
  Seguridad implementada con Spring Security y JWT para proteger rutas administrativas.

- **Gestión de Pedidos**  
  Creación de pedidos con lógica transaccional y descuento automático de stock.

- **Persistencia en la Nube**  
  Integración con Cloudinary para la carga y optimización de imágenes de productos y comprobantes.

- **Base de Datos Relacional**  
  Modelo normalizado en PostgreSQL con relaciones One-to-Many entre productos, variantes y pedidos.

---

## 🛠️ Tecnologías Utilizadas

- **Lenguaje:** Java 21 (OpenJDK)
- **Framework:** Spring Boot 3.5.x
- **ORM:** Hibernate / Spring Data JPA
- **Seguridad:** Spring Security 6 + JWT
- **Base de Datos:** PostgreSQL (Supabase)
- **Almacenamiento de Archivos:** Cloudinary
- **Validaciones:** Jakarta Bean Validation
- **Contenedores:** Docker

---

## ⚙️ Configuración del Entorno

Para ejecutar el proyecto de manera local, es necesario definir las siguientes variables de entorno:

### Base de Datos

```env
SPRING_DATASOURCE_URL=jdbc:postgresql://<HOST>:<PORT>/postgres
SPRING_DATASOURCE_USERNAME=<USER>
SPRING_DATASOURCE_PASSWORD=<PASSWORD>
```

### Seguridad

```env
JWT_SECRET=<TU_CLAVE_BASE64_SEGURA>
JWT_EXPIRATION=86400000
```

### Cloudinary

```env
CLOUDINARY_CLOUD_NAME=<TU_CLOUD_NAME>
CLOUDINARY_API_KEY=<TU_API_KEY>
CLOUDINARY_API_SECRET=<TU_API_SECRET>
```

### CORS

```env
CORS_ALLOWED_ORIGINS=http://localhost:5173,https://camelmodashop.vercel.app
```
---

## 🔌 Endpoints Principales

| Módulo    | Método | Endpoint                 | Descripción                     | Acceso  |
| --------- | ------ | ------------------------ | ------------------------------- | ------- |
| Auth      | POST   | `/api/auth/login`        | Autenticación de administrador  | Público |
| Productos | GET    | `/api/productos/publico` | Obtener catálogo activo         | Público |
| Productos | POST   | `/api/productos/admin`   | Crear producto con variantes    | Admin   |
| Pedidos   | POST   | `/api/pedidos/publico`   | Crear pedido (Checkout)         | Público |
| Uploads   | POST   | `/api/uploads`           | Subida de imágenes a Cloudinary | Admin   |

---

## 📦 Despliegue

La aplicación está preparada para despliegue continuo en Railway, utilizando contenedores Docker y variables de entorno para la configuración segura del entorno productivo.

---

## 📄 Licencia

Este proyecto se distribuye bajo licencia MIT.
Puede utilizarse, modificarse y distribuirse libremente.