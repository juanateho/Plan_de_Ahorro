package com.example.plan_de_ahorro.data.repository

import com.example.plan_de_ahorro.data.model.Member
import com.example.plan_de_ahorro.data.model.Payment
import com.example.plan_de_ahorro.data.model.Plan
import com.example.plan_de_ahorro.data.remote.ApiService

class PlanRepository(private val apiService: ApiService) {

    suspend fun createPlan(plan: Plan): Plan {
        return apiService.createPlan(plan)
    }

    suspend fun getPlans(): List<Plan> {
        return apiService.getPlans()
    }

    suspend fun getPlanById(id: String): Plan {
        return apiService.getPlanById(id)
    }

    suspend fun createMember(member: Member): Member {
        return apiService.createMember(member)
    }

    suspend fun getMembersByPlanId(planId: String): List<Member> {
        return apiService.getMembersByPlanId(planId)
    }

    suspend fun createPayment(payment: Payment): Payment {
        return apiService.createPayment(payment)
    }

    suspend fun getPaymentsByPlanId(planId: String): List<Payment> {
        return apiService.getPaymentsByPlanId(planId)
    }
}
