<div align="center">

# 🛒 GuazoAPI

**API REST para la gestión de tienda Guazo**  
Java 21 · Spring Boot 4 · PostgreSQL · Swagger UI

![Java](https://img.shields.io/badge/Java-21-orange?style=flat-square&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.6-brightgreen?style=flat-square&logo=springboot)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue?style=flat-square&logo=postgresql)
![Swagger](https://img.shields.io/badge/Swagger-OpenAPI%203-85EA2D?style=flat-square&logo=swagger)
![Cloudinary](https://img.shields.io/badge/Imágenes-Cloudinary-3448C5?style=flat-square)
![WhatsApp](https://img.shields.io/badge/Notificaciones-WhatsApp-25D366?style=flat-square&logo=whatsapp)

</div>

---

## 📋 Descripción

Backend completo para **Tienda Guazo**, una tienda local. Gestiona inventario de productos, clientes, compras con control de stock automático, promociones del carrusel y notificaciones automáticas de pedidos por WhatsApp.

---

## 🚀 Tecnologías

| Tecnología | Versión | Uso |
|---|---|---|
| Java | 21 | Lenguaje principal |
| Spring Boot | 4.0.6 | Framework web |
| Spring Data JPA | — | Persistencia |
| PostgreSQL | 16+ | Base de datos |
| Springdoc OpenAPI | 2.8.9 | Documentación Swagger |
| Cloudinary | — | Almacenamiento de imágenes |
| CallMeBot | — | Notificaciones WhatsApp gratuitas |
| Gradle | 8+ | Build tool |

---

## ⚙️ Configuración

### Prerrequisitos
- Java 21+
- PostgreSQL 16+
- Gradle 8+

### 1. Base de datos

```sql
CREATE DATABASE guazo;
```

### 2. Variables de entorno

| Variable | Descripción | Ejemplo |
|---|---|---|
| `DB_URL` | URL JDBC de PostgreSQL | `jdbc:postgresql://localhost:5432/guazo` |
| `DB_USER` | Usuario de PostgreSQL | `postgres` |
| `DB_PASS` | Contraseña de PostgreSQL | `tu_contraseña` |
| `WA_PHONE` | Tu número de WhatsApp con código de país | `+58 4141234567` |
| `WA_APIKEY` | API Key de CallMeBot | `123456` |

### 3. Activar notificaciones WhatsApp (CallMeBot)

> Solo hay que hacerlo **una vez**.

1. Agrega el número **+34 644 59 78 89** a tus contactos de WhatsApp como `CallMeBot`
2. Envíale este mensaje exacto: `I allow callmebot to send me messages`
3. En menos de 1 minuto recibirás tu API key por WhatsApp
4. Guarda esa key en `WA_APIKEY`

### 4. Correr la aplicación

```bash
git clone https://github.com/Bodepk/GuazoAPI.git
cd GuazoAPI

export DB_URL=jdbc:postgresql://localhost:5432/guazo
export DB_USER=postgres
export DB_PASS=tu_contraseña
export WA_PHONE=+584141234567
export WA_APIKEY=tu_apikey

./gradlew bootRun
```

La API estará disponible en: **`http://localhost:9988/apig`**

---

## 📖 Documentación

```
http://localhost:9988/apig/swagger-ui.html   ← UI interactiva
http://localhost:9988/apig/api-docs          ← JSON OpenAPI
```

---

## 🔌 Endpoints

### 📦 Productos — `/apig/producto`

| Método | Ruta | Descripción |
|---|---|---|
| `POST` | `/producto` | Crear producto |
| `GET` | `/producto` | Listar todos |
| `GET` | `/producto/{id}` | Obtener por ID |
| `GET` | `/producto/categoria/{categoria}` | Filtrar por categoría |
| `GET` | `/producto/estado/{estado}` | Filtrar por estado |
| `PUT` | `/producto/{id}` | Actualizar producto |
| `DELETE` | `/producto/{id}` | Eliminar producto |

**Categorías:** `ELECTRODOMESTICO` · `BEBIDA` · `GRANO` · `ENLATADO` · `CARNICO` · `CONDIMENTO` · `CONFITURA` · `HERRAMIENTA` · `ELABORADO`

**Estados (automáticos):** `ABUNDANTE` (>10 unidades) · `ESCASO` (≤10) · `VACIO` (0)

---

### 👤 Clientes — `/apig/cliente`

| Método | Ruta | Descripción |
|---|---|---|
| `POST` | `/cliente` | Crear cliente |
| `GET` | `/cliente` | Listar todos |
| `GET` | `/cliente/{id}` | Obtener por ID |
| `PUT` | `/cliente/{id}` | Actualizar |
| `DELETE` | `/cliente/{id}` | Eliminar |

> El número de teléfono es el identificador único. Al crear una compra, si el número ya existe se reutiliza el cliente automáticamente.

---

### 🛍️ Compras — `/apig/compra`

| Método | Ruta | Descripción |
|---|---|---|
| `POST` | `/compra` | Crear compra + notificación WhatsApp |
| `GET` | `/compra/cliente/{numero}` | Compras por teléfono del cliente |
| `GET` | `/compra/reporte?inicio=&fin=` | Reporte entre fechas con estadísticas |
| `PATCH` | `/compra/{id}/estado` | Cambiar estado (`COMPLETA` / `CANCELADA`) |

> Al crear una compra: se valida stock, se descuenta inventario, y se envía notificación al WhatsApp del dueño. El estado inicial siempre es `PENDIENTE`.

---

### 🎯 Promociones — `/apig/promocion`

| Método | Ruta | Uso | Descripción |
|---|---|---|---|
| `GET` | `/promocion/vigentes` | Frontend web | Solo activas y no vencidas |
| `GET` | `/promocion` | Admin | Todas las promociones |
| `GET` | `/promocion/{id}` | Admin | Obtener por ID |
| `POST` | `/promocion` | Admin | Crear promoción |
| `PUT` | `/promocion/{id}` | Admin | Actualizar promoción |
| `PATCH` | `/promocion/{id}/toggle` | Admin | Activar / desactivar |
| `DELETE` | `/promocion/{id}` | Admin | Eliminar |

---

## 📦 Ejemplos

### Crear un producto
```json
POST /apig/producto
{
  "name": "Arroz Diana 1kg",
  "urlImg": "https://res.cloudinary.com/tu-cloud/image/upload/arroz.jpg",
  "description": "Arroz blanco de primera calidad",
  "price": 2.50,
  "quantity": 50,
  "productCategoria": "GRANO"
}
```

### Crear una compra
```json
POST /apig/compra
{
  "nombreCliente": "Juan Pérez",
  "numeroCliente": "04141234567",
  "direccion": "Av. Principal #123, Caracas",
  "items": [
    { "productoId": 1, "cantidad": 2, "precioUnitario": 2.50 },
    { "productoId": 3, "cantidad": 1, "precioUnitario": 5.00 }
  ]
}
```

### Crear una promoción
```json
POST /apig/promocion
{
  "badge": "🔥 OFERTA ESPECIAL",
  "titulo": "50% OFF en Granos Seleccionados",
  "descripcion": "Frijoles, lentejas y garbanzos de primera calidad",
  "urlImagen": "https://res.cloudinary.com/tu-cloud/image/upload/promo-granos.jpg",
  "textoBoton": "Aprovecha ahora →",
  "fechaVencimiento": "2025-12-31T23:59:59",
  "activa": true
}
```

### Reporte entre fechas
```
GET /apig/compra/reporte?inicio=2025-01-01T00:00:00&fin=2025-12-31T23:59:59
```
```json
{
  "totalCompras": 15,
  "totalGanancias": 342.75,
  "productosMasVendidos": [
    { "nombre": "Arroz Diana 1kg", "cantidadVendida": 30, "totalGenerado": 75.00 }
  ],
  "compras": [...]
}
```

---

## 🗂️ Estructura del proyecto

```
src/main/java/com/bode/guazo/
├── config/
│   ├── CorsConfig.java               # CORS para frontend y Electron
│   └── SwaggerConfig.java            # Documentación OpenAPI
├── controller/
│   ├── ClienteController.java
│   ├── CompraController.java
│   ├── ProductoController.java
│   └── PromocionController.java
├── dto/
│   ├── ClienteDTO.java
│   ├── CompraProductoDTO.java
│   ├── CompraRequestDTO.java
│   ├── ProductoDTO.java
│   ├── PromocionDTO.java
│   └── ReporteComprasDTO.java
├── entity/
│   ├── enums/
│   │   ├── Categoria.java
│   │   ├── CompraEstado.java
│   │   └── ProductoEstado.java
│   ├── Cliente.java
│   ├── Compra.java
│   ├── CompraProductos.java
│   ├── CompraProductosPK.java
│   ├── Producto.java
│   └── Promocion.java
├── exception/
│   ├── BusinessException.java
│   ├── GlobalExceptionHandler.java
│   └── ResourceNotFoundException.java
├── repository/
│   ├── ClienteRepository.java
│   ├── CompraProductoRepository.java
│   ├── CompraRepository.java
│   ├── ProductRepository.java
│   └── PromocionRepository.java
└── service/
    ├── ClienteService.java
    ├── CompraService.java
    ├── ProductService.java
    ├── PromocionService.java
    └── WhatsAppService.java          # Notificaciones CallMeBot
```

---

## 🧠 Lógica de negocio

- **Stock automático:** Al crear una compra se valida y descuenta el stock. El estado del producto se recalcula: `VACIO` (0), `ESCASO` (≤10), `ABUNDANTE` (>10).
- **Cliente upsert:** Si el número de teléfono ya existe al crear una compra, se reutiliza el cliente.
- **WhatsApp:** Cada pedido nuevo envía un mensaje con todos los detalles al número del dueño. Si la notificación falla, la compra igual se guarda.
- **Promociones vigentes:** El frontend solo recibe promociones activas y cuya fecha de vencimiento no haya pasado.
- **Errores estructurados:** Todas las excepciones devuelven JSON con `timestamp`, `status` y `mensaje`.

---

## 🖼️ Imágenes con Cloudinary

Las imágenes de productos y promociones se almacenan en **Cloudinary**. El flujo es:

```
App Admin → sube imagen a Cloudinary → obtiene URL → guarda URL en GuazoAPI
```

La API solo almacena la URL en el campo `urlImagen` / `urlImg`. No maneja archivos binarios directamente.

---

## 📄 Licencia

MIT © [Bode](https://github.com/Bodepk)
