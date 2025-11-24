package com.example.plan_de_ahorro.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plan_de_ahorro.data.model.Member
import com.example.plan_de_ahorro.data.model.Payment
import com.example.plan_de_ahorro.data.model.Plan
import com.example.plan_de_ahorro.data.repository.PlanRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PlanViewModel(private val repository: PlanRepository) : ViewModel() {

    private val _plans = MutableStateFlow<List<Plan>>(emptyList())
    val plans: StateFlow<List<Plan>> = _plans

    private val _selectedPlan = MutableStateFlow<Plan?>(null)
    val selectedPlan: StateFlow<Plan?> = _selectedPlan

    fun createPlan(name: String) {
        viewModelScope.launch {
            val newPlan = Plan(name = name)
            repository.createPlan(newPlan)
            loadPlans()
        }
    }

    fun loadPlans() {
        viewModelScope.launch {
            _plans.value = repository.getPlans()
        }
    }

    fun loadPlanDetails(planId: String) {
        viewModelScope.launch {
            _selectedPlan.value = repository.getPlanById(planId)
        }
    }

    fun createMember(name: String, planId: String) {
        viewModelScope.launch {
            val newMember = Member(name = name, planId = planId)
            repository.createMember(newMember)
            loadPlanDetails(planId)
        }
    }

    fun createPayment(amount: Double, memberId: String, planId: String) {
        viewModelScope.launch {
            val newPayment = Payment(amount = amount, memberId = memberId, planId = planId)
            repository.createPayment(newPayment)
            loadPlanDetails(planId)
        }
    }
}
