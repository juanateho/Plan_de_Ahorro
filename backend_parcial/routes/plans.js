const express = require('express');
const router = express.Router();
const Plan = require('../models/Plan');

/**
 * @openapi
 * tags:
 *   name: Planes
 *   description: Endpoints para gestión de planes de ahorro
 */

/**
 * @openapi
 * /api/plans:
 *   post:
 *     summary: Crear un nuevo plan de ahorro
 *     tags: [Planes]
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             type: object
 *             required:
 *               - name
 *               - motive
 *               - targetAmount
 *               - months
 *             properties:
 *               name:
 *                 type: string
 *                 description: Nombre del plan
 *               motive:
 *                 type: string
 *                 description: Motivo o propósito del ahorro
 *               targetAmount:
 *                 type: number
 *                 description: Monto objetivo del plan
 *               months:
 *                 type: number
 *                 description: Cantidad de meses para cumplir la meta
 *           example:
 *             name: "Ahorro para viaje"
 *             motive: "Vacaciones fin de año"
 *             targetAmount: 3000000
 *             months: 6
 *     responses:
 *       201:
 *         description: Plan creado exitosamente
 *       400:
 *         description: Datos inválidos
 *       500:
 *         description: Error del servidor
 */
router.post('/', async (req, res) => {
  try {
    const plan = await Plan.create(req.body);
    res.status(201).json(plan);
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
});

/**
 * @openapi
 * /api/plans:
 *   get:
 *     summary: Obtener todos los planes existentes
 *     tags: [Planes]
 *     responses:
 *       200:
 *         description: Lista de planes creados
 *         content:
 *           application/json:
 *             example:
 *               - _id: "6738d9a19bd72f03fd1a2c41"
 *                 name: "Ahorro para vehículo"
 *                 motive: "Comprar carro usado"
 *                 targetAmount: 15000000
 *                 months: 12
 *                 createdAt: "2025-11-16T17:00:00.000Z"
 *               - _id: "6738e5f19bd72f03fd1a2f11"
 *                 name: "Ahorro para vacaciones"
 *                 motive: "Viaje familiar"
 *                 targetAmount: 5000000
 *                 months: 8
 *                 createdAt: "2025-11-15T17:00:00.000Z"
 *       500:
 *         description: Error del servidor
 */
router.get('/', async (req, res) => {
  try {
    const plans = await Plan.find();
    res.json(plans);
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
});

/**
 * @openapi
 * /api/plans/{id}:
 *   get:
 *     summary: Obtener un plan por ID
 *     tags: [Planes]
 *     parameters:
 *       - in: path
 *         name: id
 *         required: true
 *         schema:
 *           type: string
 *         description: ID del plan a consultar
 *     responses:
 *       200:
 *         description: Plan encontrado
 *         content:
 *           application/json:
 *             example:
 *               _id: "6738d9a19bd72f03fd1a2c41"
 *               name: "Ahorro para vehículo"
 *               motive: "Comprar carro usado"
 *               targetAmount: 15000000
 *               months: 12
 *               createdAt: "2025-11-16T17:00:00.000Z"
 *       404:
 *         description: Plan no encontrado
 *       500:
 *         description: Error del servidor
 */
router.get('/:id', async (req, res) => {
  try {
    const plan = await Plan.findById(req.params.id);
    if (!plan) return res.status(404).json({ error: "Plan no encontrado" });
    res.json(plan);
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
});

module.exports = router;
