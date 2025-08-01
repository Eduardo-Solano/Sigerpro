# SIGERPRO: Sistema Integral de Gestión de Residencias Profesionales

**SIGERPRO** es un sistema integral desarrollado en Java para la gestión y automatización de residencias profesionales en instituciones educativas. Permite controlar estudiantes, docentes, empresas, proyectos y expedientes documentales con un enfoque moderno y seguro.

## 💻 Tecnologías utilizadas

- **Lenguaje:** Java (JDK 21+ recomendado)
- **Interfaz gráfica:** Java Swing
- **Persistencia:** JPA (Java Persistence API)
- **Base de datos:** PostgreSQL (scripts DDL incluidos)
- **Compilación y dependencias:** Maven

## 📁 Estructura del proyecto

- `/src/` - Código fuente Java (organizado en controladores, entidades, vistas, servicios, etc.)
- `/esquema/` - Scripts DDL para crear la base de datos en PostgreSQL
- `/lib/` - Dependencias externas (si existen)
- `README.md` - Este archivo

## ⚙️ Funcionalidades principales

- Registro, edición y gestión de estudiantes, docentes y empresas
- Asignación de estudiantes y docentes a proyectos
- Gestión y seguimiento de proyectos de residencia profesional
- Manejo completo de expedientes y documentos asociados a estudiantes y proyectos
- Importación masiva de datos desde archivos CSV
- Generación de reportes en PDF con formato profesional
- Control de acceso por usuarios con autenticación
- Exportación e impresión de tablas y reportes

## 📦 Instalación y configuración

### 1. Clonar el repositorio

 ```bash
git clone https://github.com/tu-usuario/Sigerpro.git
cd Sigerpro
```


### 2. Crear la base de datos
Entra a tu gestor de PostgreSQL y ejecuta el script que se encuentra en ``Schema_Postgres/Sigerpro_DDL_v2.sql`` para crear el esquema y tablas necesarias.

### 3. Configurar la conexión a la base de datos

Modifica el archivo de configuración de conexión a la base de datos (``persistence.xml`` o el archivo correspondiente dentro de ``/src/main/resources/META-INF/``), agregando los datos de tu host, puerto, base de datos, usuario y contraseña.

### 4. Compilar el proyecto
```bash
mvn clean install
```
### 5. Ejecutar la aplicación

Puedes ejecutar el sistema desde tu IDE favorito o mediante línea de comandos:

```bash
mvn exec:java -Dexec.mainClass="vista.IniciarSesion"
```

(Asegúrate de que el nombre del paquete y clase principal coincidan con la ubicación real del proyecto)

### 6. Pruebas Unitarias

Ejecuta las pruebas unitarias con:
```bash
mvn test
```

### 7. Desarrolladores

El proyecto SIGERPRO fue desarrollado por alumnos del Instituto Tecnologico de Oaxaca comó solución para la gestión integral de residencias profesionales en el ámbito educativo. Puedes contribuir mediante ``pull requests.``
- Solano Ramos Eduardo 
- Mendoza Chavez Jesus Abraham
- Perez Perez Jose Francisco
- Velasquez Montes Ignacio Luis
- Miranda Virgen Sabas Mijail
