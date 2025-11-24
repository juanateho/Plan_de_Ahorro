require('dotenv').config();
const express = require('express');
const mongoose = require('mongoose');
const cors = require('cors');
const swaggerUi = require('swagger-ui-express');
const swaggerJsdoc = require('swagger-jsdoc');

const app = express();
app.use(cors());
app.use(express.json());

// Swagger Config
const swaggerOptions = {
  definition: {
    openapi: "3.0.0",
    info: {
      title: "API Plan Familiar de Ahorro",
      version: "1.0.0",
      description: "Backend para gestionar planes de ahorro, miembros y pagos",
    },
  },
  apis: ["./routes/*.js"], // ← Aquí buscará tus anotaciones Swagger
};

const swaggerSpec = swaggerJsdoc(swaggerOptions);

// Swagger endpoint
app.use("/api-docs", swaggerUi.serve, swaggerUi.setup(swaggerSpec));

// Models (required so Mongoose schemas are registered)
const Plan = require('./models/Plan');
const Member = require('./models/Member');
const Payment = require('./models/Payment');

// Routes
const plansRouter = require('./routes/plans');
const membersRouter = require('./routes/members');
const paymentsRouter = require('./routes/payments');

app.use('/api/plans', plansRouter);
app.use('/api/members', membersRouter);
app.use('/api/payments', paymentsRouter);

const PORT = process.env.PORT || 3000;
const MONGO_URI = process.env.MONGO_URI || 'mongodb://localhost:27017/plan_ahorro';

mongoose.connect(MONGO_URI)
  .then(()=> {
    console.log('Connected to MongoDB');
    app.listen(PORT, ()=> console.log('Server running on port', PORT));
  })
  .catch(err => {
    console.error('Error connecting to MongoDB:', err.message);
  });
