const express = require('express');
const router = express.Router();
const Payment = require('../models/Payment');

/**
 * @openapi
 * tags:
 *   name: Pagos
 *   description: Endpoints para registrar y consultar pagos asociados a planes de ahorro
 */

/**
 * @openapi
 * /api/payments:
 *   post:
 *     summary: Registrar un pago para un plan de ahorro
 *     tags: [Pagos]
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             type: object
 *             required:
 *               - planId
 *               - memberId
 *               - amount
 *             properties:
 *               planId:
 *                 type: string
 *                 description: ID del plan asociado
 *               memberId:
 *                 type: string
 *                 description: ID del miembro que paga
 *               amount:
 *                 type: number
 *                 description: Valor pagado en pesos
 *           example:
 *             planId: "6738d9a19bd72f03fd1a2c41"
 *             memberId: "6738d9f29bd72f03fd1a2c88"
 *             amount: 50000
 *     responses:
 *       201:
 *         description: Pago registrado exitosamente
 *       400:
 *         description: Datos inválidos
 *       500:
 *         description: Error registrando pago
 */
router.post('/', async (req, res) => {
  try {
    const payment = await Payment.create(req.body);
    res.status(201).json(payment);
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
});

/**
 * @openapi
 * /api/payments/plan/{planId}:
 *   get:
 *     summary: Obtener todos los pagos registrados para un plan
 *     tags: [Pagos]
 *     parameters:
 *       - in: path
 *         name: planId
 *         required: true
 *         schema:
 *           type: string
 *         description: ID del plan a consultar
 *     responses:
 *       200:
 *         description: Lista de pagos del plan
 *         content:
 *           application/json:
 *             example:
 *               - _id: "6751ab21a9df53a11db651e4"
 *                 memberId: "6738d9f29bd72f03fd1a2c88"
 *                 planId: "6738d9a19bd72f03fd1a2c41"
 *                 amount: 80000
 *                 date: "2025-11-16T14:00:00.000Z"
 *               - _id: "6751ab99a9df53a11db651ff"
 *                 memberId: "6738d9f29bd72f03fd1a2c88"
 *                 planId: "6738d9a19bd72f03fd1a2c41"
 *                 amount: 50000
 *                 date: "2025-11-11T14:00:00.000Z"
 *       404:
 *         description: No se encontraron pagos
 *       500:
 *         description: Error consultando pagos
 */
router.get('/plan/:planId', async (req, res) => {
  try {
    const payments = await Payment.find({ planId: req.params.planId });
    if (!payments || payments.length === 0) {
      return res.status(404).json({ message: "No se encontraron pagos para este plan." });
    }
    res.json(payments);
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
});

module.exports = router;
