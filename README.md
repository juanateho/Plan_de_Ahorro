# 💰 Plan de Ahorro

**Plan de Ahorro** es una aplicación móvil diseñada para ayudar a los usuarios a gestionar sus metas financieras y realizar un seguimiento de sus planes de ahorro. Este repositorio contiene tanto el código fuente de la aplicación móvil (Android) como el servidor backend.

## 📋 Características

* **Gestión de Metas**: Crea y personaliza tus objetivos de ahorro.
* **Seguimiento**: Visualiza el progreso de tus ahorros en tiempo real.
* **Historial**: Registro de transacciones y aportes.
* **Conectividad**: Sincronización de datos con un servidor backend.

## 🛠️ Tecnologías Utilizadas

Este proyecto utiliza una arquitectura cliente-servidor:

### Móvil (Cliente)
* **Lenguaje**: [Kotlin](https://kotlinlang.org/)
* **Plataforma**: Android
* **Herramienta de Construcción**: Gradle (Kotlin DSL)

### Backend (Servidor)
* **Lenguaje**: JavaScript
* **Entorno**: [Node.js](https://nodejs.org/) (Asumido por la estructura del proyecto)
* **Ubicación**: Carpeta `backend_parcial`

## 📂 Estructura del Proyecto

El repositorio está organizado de la siguiente manera:

```text
Plan_de_Ahorro/
├── app/                # Código fuente de la aplicación Android
├── backend_parcial/    # Código fuente del servidor/API Backend
├── gradle/             # Archivos wrapper de Gradle
├── build.gradle.kts    # Configuración de construcción raíz
└── README.md           # Documentación del proyecto
