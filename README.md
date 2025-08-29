## 📚 Biblioteca Virtual - Java Swing + Firebase

Una aplicación de escritorio desarrollada con Java Swing y conectada a Firebase Realtime Database, que simula una biblioteca virtual. Los usuarios pueden registrarse o iniciar sesión, visualizar libros y solicitar préstamos. Los administradores pueden revisar un historial detallado de peticiones.

🖥️ Interfaz gráfica

La interfaz fue creada manualmente utilizando JFrame, JPanel, JLabel, JButton, JTextField, etc., sin utilizar asistentes visuales ni GUI builders. Esto ofrece mayor control sobre la disposición y estilo de los componentes.

## 🔧 Tecnologías utilizadas

- Java Swing (Apache NetBeans)

- Firebase Realtime Database (para autenticación y almacenamiento de datos)

- Librerias de Java

## 🔑 Funcionalidades

👥 Usuario estándar:

- Registro/Login: Si el usuario ya existe, se inicia sesión

- Visualización de libros disponibles con información: título, autor, ISBN, género

- Solicitud de préstamo de libros
  
🛠️ Administrador:

- Acceso con nombre de usuario designado como "admin"

- Visualización del historial completo de préstamos:

- Usuario que solicitó y Título del libro, Fecha y hora exacta de la solicitud

## 📂 Organización del proyecto
/src
  - Main.java                 // Login y registro
  - Library.java              // Pantalla de libros disponibles
  - AdminPanel.java           // Historial de préstamos
  - FirebaseInitializer.java  // Inicialización de conexión con Firebase
  - UserService.java          // Lógica de usuarios
  - Book.java                 // Modelo de libro
  - Request.java              // Modelo de préstamo

## 🚀 Cómo ejecutar el proyecto

- Clona el repositorio o abre en NetBeans

- Asegúrate de tener: JDK 8 o superior

- Descargar librerías necesarias

## ⚙️ Configuración de Firebase

- Crea un proyecto en Firebase y habilita Realtime Database

- Descarga el archivo de claves del servicio (.json). En FirebaseInitializer.java, coloca la direccion del json y cambia la url de tu firebase realtime.

## 💡 Ideas para mejorar

- Validación con contraseña real (actualmente se ignora el campo de contraseña)

- Búsqueda de libros por título o autor

- Devoluciones y fecha límite de préstamo

- Control de disponibilidad de ejemplares

- Exportación del historial a PDF/CSV
