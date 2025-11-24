const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const PlanSchema = new Schema({
  name: { type: String, required: true },
  motive: { type: String },
  targetAmount: { type: Number, required: true },
  months: { type: Number, required: true },
  createdAt: { type: Date, default: Date.now }
});

module.exports = mongoose.model('Plan', PlanSchema);
