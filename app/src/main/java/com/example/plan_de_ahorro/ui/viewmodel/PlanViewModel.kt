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

    private val _members = MutableStateFlow<List<Member>>(emptyList())
    val members: StateFlow<List<Member>> = _members

    fun createPlan(name: String, motive: String, targetAmount: Double, months: Int) {
        viewModelScope.launch {
            val newPlan = Plan(name = name, motive = motive, targetAmount = targetAmount, months = months)
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
            _members.value = repository.getMembersByPlanId(planId)
        }
    }

    fun createMember(name: String, planId: String, contributionPerMonth: Double) {
        viewModelScope.launch {
            val newMember = Member(name = name, planId = planId, contributionPerMonth = contributionPerMonth)
            repository.createMember(newMember)
            loadPlanDetails(planId) // Refresh plan details to show the new member
        }
    }

    fun createPayment(amount: Double, memberId: String, planId: String) {
        viewModelScope.launch {
            val newPayment = Payment(amount = amount, memberId = memberId, planId = planId)
            repository.createPayment(newPayment)
            loadPlanDetails(planId) // Refresh plan details
        }
    }
}
