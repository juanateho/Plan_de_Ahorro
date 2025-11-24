const express = require('express');
const router = express.Router();
const Member = require('../models/Member');

/**
 * @openapi
 * tags:
 *   name: Miembros
 *   description: Gestión de miembros de un plan de ahorro
 */

/**
 * @openapi
 * /api/members:
 *   post:
 *     summary: Crear un miembro de un plan
 *     tags: [Miembros]
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             type: object
 *             required:
 *               - name
 *               - planId
 *               - contributionPerMonth
 *             properties:
 *               name:
 *                 type: string
 *                 example: "Juan Pérez"
 *               planId:
 *                 type: string
 *                 description: ID del plan al cual pertenece el miembro
 *                 example: "67b75e842cf7bd37d87d9f23"
 *               contributionPerMonth:
 *                 type: number
 *                 description: Monto mensual aportado por el miembro
 *                 example: 150000
 *               joinedAt:
 *                 type: string
 *                 format: date-time
 *                 description: Fecha de ingreso (opcional, se asigna automáticamente)
 *     responses:
 *       201:
 *         description: Miembro creado
 *       500:
 *         description: Error al crear el miembro
 */
router.post('/', async (req, res) => {
  try {
    const member = await Member.create(req.body);
    res.status(201).json(member);
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
});

/**
 * @openapi
 * /api/members:
 *   get:
 *     summary: Obtener todos los miembros
 *     tags: [Miembros]
 *     responses:
 *       200:
 *         description: Lista de miembros
 *       500:
 *         description: Error del servidor
 */
router.get('/', async (req, res) => {
  try {
    const members = await Member.find();
    res.json(members);
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
});

/**
 * @openapi
 * /api/members/plan/{planId}:
 *   get:
 *     summary: Obtener todos los miembros asociados a un plan
 *     tags: [Miembros]
 *     parameters:
 *       - in: path
 *         name: planId
 *         required: true
 *         schema:
 *           type: string
 *         description: ID del plan
 *     responses:
 *       200:
 *         description: Lista de miembros del plan
 *       404:
 *         description: No se encontraron miembros
 *       500:
 *         description: Error del servidor
 */
router.get('/plan/:planId', async (req, res) => {
  try {
    const members = await Member.find({ planId: req.params.planId });
    res.json(members);
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
});

module.exports = router;
