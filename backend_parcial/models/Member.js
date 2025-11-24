const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const MemberSchema = new Schema({
  name: { type: String, required: true },
  planId: { type: Schema.Types.ObjectId, ref: 'Plan', required: true },
  contributionPerMonth: { type: Number, required: true },
  joinedAt: { type: Date, default: Date.now }
});

module.exports = mongoose.model('Member', MemberSchema);
