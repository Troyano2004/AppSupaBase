# AppSupaBase - UTEQ Aplicaciones Móviles

Aplicación Android desarrollada en Kotlin que consume dos fuentes de datos:
1. **Supabase** — listado de alumnos por semestre y materia
2. **API REST UTEQ** — listado de los 10 videos de resúmenes semanales más recientes

---

## Autor

**Erwin Daniel Bueno Troya**  
Universidad Técnica Estatal de Quevedo — UTEQ  
Sexto Semestre — Aplicaciones Móviles

---

## Repositorio

[https://github.com/Troyano2004/AppSupaBase](https://github.com/Troyano2004/AppSupaBase)

---

## Funcionalidades

### Pantalla 1 — Alumnos por Materia (Supabase)
- Dropdown de selección de semestre
- Dropdown de selección de materia (filtrado por semestre)
- ListView con alumnos ordenados alfabéticamente
- Cada item muestra: foto circular, nombre completo, correo y teléfono

### Pantalla 2 — Videos Resúmenes Semanales (API REST)
- Consume la API REST de la UTEQ con autenticación Bearer token
- Muestra los 10 videos más recientes ordenados por fecha
- Cada item muestra: título, imagen de portada, fecha de publicación y enlace de YouTube

---

## Tecnologías utilizadas

- **Kotlin** — lenguaje principal
- **Supabase SDK** — acceso a base de datos (postgrest-kt)
- **Volley** — peticiones HTTP a la API REST de la UTEQ
- **Glide** — carga de imágenes con transformación circular
- **ListView + ArrayAdapter personalizado** — visualización de listas
- **ConstraintLayout / LinearLayout** — estructura de interfaces

---

## Estructura del proyecto

```
app/src/main/java/com/example/appsupabase/
├── adapters/
│   ├── AlumnoAdapter.kt       # Adapter del ListView de alumnos
│   └── VideoAdapter.kt        # Adapter del ListView de videos
├── models/
│   ├── Alumno.kt              # Modelo de datos del alumno
│   ├── Materia.kt             # Modelo de datos de la materia
│   └── Video.kt               # Modelo de datos del video
├── services/
│   ├── SupabaseManager.kt     # Singleton de conexión a Supabase
│   └── SupabaseErrorHandler.kt
├── maincontenedor1.kt         # Activity de alumnos
└── mainVideos.kt              # Activity de videos
```

---

## Instalación y configuración

### Requisitos
- Android Studio Hedgehog o superior
- Android SDK mínimo API 26
- Cuenta en Supabase con las tablas `alumnos` y `materias`

### Pasos

1. Clona el repositorio:
```bash
git clone https://github.com/Troyano2004/AppSupaBase.git
```

2. Abre el proyecto en Android Studio

3. Crea o edita el archivo `local.properties` en la raíz del proyecto y agrega:
```properties
SUPABASE_URL=https://tu-proyecto.supabase.co
SUPABASE_KEY=tu-api-key-de-supabase
API_TOKEN=tu-bearer-token-de-la-api-uteq
```

4. Haz **Sync Gradle** en Android Studio

5. Ejecuta la app en un dispositivo o emulador con API 26+

---

## Modelo de datos

### Alumno (Supabase)
```kotlin
data class Alumno(
    val id: Int,
    val nombres: String? = null,
    val correo: String? = null,
    val telefono: String? = null,
    val paralelo: String? = null,
    val foto: String? = null
)
```

### Video (API REST UTEQ)
```kotlin
data class Video(
    val titulo: String,
    val fechapub: String,
    val urlvideo1: String,
    val portadaVideo: String
)
```

---

## API de videos

- **Endpoint:** `https://apiws.uteq.edu.ec/h6RPoSoRaah0Y4Bah28eew/functions/information/entity/3`
- **Método:** GET
- **Autenticación:** Bearer token en header `Authorization`
- **Imagen de portada:** `https://uteq.edu.ec/assets/images/videos/res-sem/` + campo `portadaVideo`

---

## Componentes utilizados

| Permitido | Usado |
|-----------|-------|
| ListView | ✅ |
| ArrayAdapter personalizado | ✅ |
| Supabase SDK | ✅ |
| Glide | ✅ |
| Spinner / AutoCompleteTextView | ✅ |
| ImageView | ✅ |
| TextView | ✅ |
| LinearLayout | ✅ |
| ConstraintLayout | ✅ |

| No permitido | Usado |
|--------------|-------|
| RecyclerView | ❌ |
| Jetpack Compose | ❌ |
| Retrofit | ❌ |
| Firebase | ❌ |
