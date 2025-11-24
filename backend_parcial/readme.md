# Plan Familiar de Ahorro - Backend (Node.js + Express + MongoDB)

Este backend está pensado para el parcial: Plan Familiar de Ahorro (Android - MVVM). Está listo para que los estudiantes consuman los endpoints desde la app.

## Endpoints principales

POST /api/plans
GET /api/plans
GET /api/plans/:id   -> incluye members, payments y totalCollected

POST /api/members
GET /api/members/plan/:planId

POST /api/payments
GET /api/payments/member/:memberId
GET /api/payments/plan/:planId

## Requisitos

- Node.js v16+  
- Instalar MongoDB (local o MongoDB Atlas)  
- npm

## Rápido: cómo ejecutar localmente

1. Clonar o copiar el proyecto.
2. Crear archivo `.env` (en base del contenido .env.example)
3. Instalar dependencias con el siguiente comando:
   npm install

4. (Opcional) Cargar datos de ejemplo, ejecutar en una terminal:
   npm run seed

5. Iniciar servidor:
   npm start

Servidor: http://localhost:3000

doc: http://localhost:3000/api-docs/#/