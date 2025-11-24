
require('dotenv').config();
const mongoose = require('mongoose');
const Plan = require('./models/Plan');
const Member = require('./models/Member');
const Payment = require('./models/Payment');

const MONGO_URI = process.env.MONGO_URI || 'mongodb://localhost:27017/plan_ahorro';

async function seed() {
  await mongoose.connect(MONGO_URI);
  console.log('Connected to MongoDB for seeding');

  // Clear
  await Payment.deleteMany({});
  await Member.deleteMany({});
  await Plan.deleteMany({});

  // Create plan
  const plan = new Plan({
    name: 'Plan Familiar Vacaciones',
    motive: 'Ahorro para vacaciones familiares',
    targetAmount: 2000000,
    months: 10
  });
  await plan.save();

  // Members
  const m1 = new Member({ name: 'Carlos', planId: plan._id, contributionPerMonth: 50000 });
  const m2 = new Member({ name: 'Lucia', planId: plan._id, contributionPerMonth: 50000 });
  await m1.save();
  await m2.save();

  // Payments
  const p1 = new Payment({ memberId: m1._id, planId: plan._id, amount: 50000, date: new Date() });
  const p2 = new Payment({ memberId: m2._id, planId: plan._id, amount: 50000, date: new Date() });
  await p1.save();
  await p2.save();

  console.log('Seed data created. Plan id:', plan._id.toString());
  await mongoose.disconnect();
  console.log('Disconnected');
}

seed().catch(err => {
  console.error(err);
  process.exit(1);
});
