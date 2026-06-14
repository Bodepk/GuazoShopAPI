<div align="center">

# 🛒 GuazoAPI

**API REST para la gestión de una tienda local**  
Construida con Java 21 + Spring Boot 4 + PostgreSQL

![Java](https://img.shields.io/badge/Java-21-orange?style=flat-square&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.6-brightgreen?style=flat-square&logo=springboot)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue?style=flat-square&logo=postgresql)
![Swagger](https://img.shields.io/badge/Swagger-OpenAPI%203-85EA2D?style=flat-square&logo=swagger)

</div>

---

## 📋 Descripción

GuazoAPI es el backend de una tienda pequeña (almacén / bodega). Expone endpoints REST para gestionar el inventario de productos, registrar clientes y procesar compras con control de stock automático.

---

## 🚀 Tecnologías

| Tecnología | Versión | Uso |
|---|---|---|
| Java | 21 | Lenguaje principal |
| Spring Boot | 4.0.6 | Framework web |
| Spring Data JPA | — | Persistencia |
| PostgreSQL | 16+ | Base de datos |
| Springdoc OpenAPI | 2.8.9 | Documentación Swagger |
| Gradle | 8+ | Build tool |

---

## ⚙️ Configuración

### Prerrequisitos
- Java 21+
- PostgreSQL 16+
- Gradle 8+

### Variables de entorno

Crea la base de datos en PostgreSQL:

```sql
CREATE DATABASE guazo;
```

Configura las siguientes variables de entorno antes de correr la aplicación:

| Variable | Descripción | Valor por defecto |
|---|---|---|
| `DB_URL` | URL de conexión JDBC | `jdbc:postgresql://localhost:5432/guazo` |
| `DB_USER` | Usuario de PostgreSQL | `postgres` |
| `DB_PASS` | Contraseña de PostgreSQL | `root123` |

### Correr la aplicación

```bash
# Clonar el repositorio
git clone https://github.com/Bodepk/GuazoAPI.git
cd GuazoAPI

# Con variables de entorno
export DB_URL=jdbc:postgresql://localhost:5432/guazo
export DB_USER=postgres
export DB_PASS=tu_contraseña

# Ejecutar
./gradlew bootRun
```

La API estará disponible en: `http://localhost:9988/apig`

---

## 📖 Documentación

Una vez corriendo, accede a la documentación interactiva de Swagger:

```
http://localhost:9988/apig/swagger-ui.html
```

JSON de la especificación OpenAPI:

```
http://localhost:9988/apig/api-docs
```

---

## 🔌 Endpoints

### Productos — `/apig/producto`

| Método | Ruta | Descripción |
|---|---|---|
| `POST` | `/producto` | Crear producto |
| `GET` | `/producto` | Listar todos los productos |
| `GET` | `/producto/{id}` | Obtener producto por ID |
| `GET` | `/producto/categoria/{categoria}` | Filtrar por categoría |
| `GET` | `/producto/estado/{estado}` | Filtrar por estado |
| `PUT` | `/producto/{id}` | Actualizar producto |
| `DELETE` | `/producto/{id}` | Eliminar producto |

**Categorías válidas:** `ELECTRODOMESTICO` · `BEBIDA` · `GRANO` · `ENLATADO` · `CARNICO` · `CONDIMENTO` · `CONFITURA` · `HERRAMIENTA` · `ELABORADO`

**Estados válidos:** `ABUNDANTE` · `ESCASO` · `VACIO`

> El estado se calcula automáticamente: `VACIO` si cantidad = 0, `ESCASO` si cantidad ≤ 10, `ABUNDANTE` en otro caso.

---

### Clientes — `/apig/cliente`

| Método | Ruta | Descripción |
|---|---|---|
| `POST` | `/cliente` | Crear cliente |
| `GET` | `/cliente` | Listar todos los clientes |
| `GET` | `/cliente/{id}` | Obtener cliente por ID |
| `PUT` | `/cliente/{id}` | Actualizar cliente |
| `DELETE` | `/cliente/{id}` | Eliminar cliente |

---

### Compras — `/apig/compra`

| Método | Ruta | Descripción |
|---|---|---|
| `POST` | `/compra` | Crear compra |
| `GET` | `/compra/cliente/{numero}` | Compras por número de teléfono |
| `GET` | `/compra/reporte?inicio=&fin=` | Reporte entre dos fechas |
| `PATCH` | `/compra/{id}/estado` | Actualizar estado de compra |

**Estados de compra:** `PENDIENTE` · `COMPLETA` · `CANCELADA`

> Al crear una compra: si el cliente no existe se crea automáticamente. El stock se descuenta en el momento. No se puede cancelar una compra ya `COMPLETA`.

---

## 📦 Ejemplos de uso

### Crear un producto

```bash
POST /apig/producto
Content-Type: application/json

{
  "name": "Arroz Diana 1kg",
  "urlImg": "https://example.com/arroz.jpg",
  "description": "Arroz blanco de primera calidad",
  "price": 2.50,
  "quantity": 50,
  "productCategoria": "GRANO"
}
```

### Crear una compra

```bash
POST /apig/compra
Content-Type: application/json

{
  "nombreCliente": "Juan Pérez",
  "numeroCliente": "04141234567",
  "direccion": "Av. Principal #123, Caracas",
  "items": [
    {
      "productoId": 1,
      "cantidad": 2,
      "precioUnitario": 2.50
    }
  ]
}
```

### Reporte entre fechas

```bash
GET /apig/compra/reporte?inicio=2025-01-01T00:00:00&fin=2025-12-31T23:59:59
```

**Respuesta:**
```json
{
  "fechaInicio": "2025-01-01T00:00:00",
  "fechaFin": "2025-12-31T23:59:59",
  "totalCompras": 15,
  "totalGanancias": 342.75,
  "compras": [...],
  "productosMasVendidos": [
    {
      "productoId": 1,
      "nombre": "Arroz Diana 1kg",
      "cantidadVendida": 30,
      "totalGenerado": 75.00
    }
  ]
}
```

---

## 🗂️ Estructura del proyecto

```
src/main/java/com/bode/guazo/
├── config/
│   └── SwaggerConfig.java          # Configuración OpenAPI
├── controller/
│   ├── ClienteController.java      # CRUD clientes
│   ├── CompraController.java       # Gestión de compras
│   └── ProductoController.java     # CRUD productos
├── dto/
│   ├── ClienteDTO.java
│   ├── CompraProductoDTO.java
│   ├── CompraRequestDTO.java
│   ├── ProductoDTO.java
│   └── ReporteComprasDTO.java      # Reporte con estadísticas
├── entity/
│   ├── enums/
│   │   ├── Categoria.java
│   │   ├── CompraEstado.java
│   │   └── ProductoEstado.java
│   ├── Cliente.java
│   ├── Compra.java
│   ├── CompraProductos.java
│   ├── CompraProductosPK.java
│   └── Producto.java
├── exception/
│   ├── BusinessException.java
│   ├── GlobalExceptionHandler.java # Manejo global de errores
│   └── ResourceNotFoundException.java
├── repository/
│   ├── ClienteRepository.java
│   ├── CompraProductoRepository.java
│   ├── CompraRepository.java
│   └── ProductRepository.java
└── service/
    ├── ClienteService.java
    ├── CompraService.java
    └── ProductService.java
```

---

## 🧠 Lógica de negocio

- **Stock automático:** Al crear una compra se valida y descuenta el stock. El estado del producto se recalcula automáticamente (`ABUNDANTE` / `ESCASO` / `VACIO`).
- **Cliente upsert:** Si el número de teléfono ya existe al crear una compra, se reutiliza el cliente en lugar de crear uno duplicado.
- **Subtotal automático:** El subtotal de cada ítem se calcula con `@PrePersist` / `@PreUpdate` para garantizar consistencia.
- **Errores claros:** Todas las excepciones devuelven JSON estructurado con `timestamp`, `status` y `mensaje`.

---

## 📄 Licencia

MIT © [Bode](https://github.com/Bodepk)
