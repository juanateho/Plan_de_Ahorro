package com.example.plan_de_ahorro.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plan_de_ahorro.data.model.Member
import com.example.plan_de_ahorro.data.model.Payment
import com.example.plan_de_ahorro.data.model.Plan
import com.example.plan_de_ahorro.data.repository.PlanRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class PlanViewModel(private val repository: PlanRepository) : ViewModel() {

    private val _plans = MutableStateFlow<List<Plan>>(emptyList())
    val plans: StateFlow<List<Plan>> = _plans

    private val _selectedPlan = MutableStateFlow<Plan?>(null)
    val selectedPlan: StateFlow<Plan?> = _selectedPlan

    private val _members = MutableStateFlow<List<Member>>(emptyList())
    val members: StateFlow<List<Member>> = _members

    private val _payments = MutableStateFlow<List<Payment>>(emptyList())
    val payments: StateFlow<List<Payment>> = _payments

    // Calculated state for the selected plan's remaining amount
    val planRemainingAmount = combine(_selectedPlan, _payments) { plan, payments ->
        if (plan == null) {
            0.0
        } else {
            val totalPaid = payments.sumOf { it.amount }
            val remaining = plan.targetAmount - totalPaid
            if (remaining < 0) 0.0 else remaining
        }
    }

    // Map to store total payments per planId for the list view
    private val _plansProgress = MutableStateFlow<Map<String, Double>>(emptyMap())
    val plansProgress: StateFlow<Map<String, Double>> = _plansProgress


    fun createPlan(name: String, motive: String, targetAmount: Double, months: Int) {
        viewModelScope.launch {
            try {
                val newPlan = Plan(name = name, motive = motive, targetAmount = targetAmount, months = months)
                repository.createPlan(newPlan)
                loadPlans()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun loadPlans() {
        viewModelScope.launch {
            try {
                val plansList = repository.getPlans()
                _plans.value = plansList
                
                val progressMap = mutableMapOf<String, Double>()
                plansList.forEach { plan ->
                    val planId = plan.id
                    if (planId != null) {
                        try {
                            val planPayments = repository.getPaymentsByPlanId(planId)
                            val totalPaid = planPayments.sumOf { it.amount }
                            progressMap[planId] = totalPaid
                        } catch (e: Exception) {
                            progressMap[planId] = 0.0
                        }
                    }
                }
                _plansProgress.value = progressMap
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun loadPlanDetails(planId: String) {
        viewModelScope.launch {
            try {
                _selectedPlan.value = repository.getPlanById(planId)
                _members.value = repository.getMembersByPlanId(planId)
                _payments.value = repository.getPaymentsByPlanId(planId)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun createMember(name: String, planId: String, contributionPerMonth: Double) {
        viewModelScope.launch {
            try {
                val newMember = Member(name = name, planId = planId, contributionPerMonth = contributionPerMonth)
                repository.createMember(newMember)
                loadPlanDetails(planId) // Refresh plan details
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun createPayment(amount: Double, memberId: String, planId: String) {
        viewModelScope.launch {
            try {
                val newPayment = Payment(amount = amount, memberId = memberId, planId = planId)
                repository.createPayment(newPayment)
                loadPlanDetails(planId) // Refresh plan details
                loadPlans() // Refresh list progress as well if needed later
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
