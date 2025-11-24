package com.example.plan_de_ahorro.data.remote

import com.example.plan_de_ahorro.data.model.Member
import com.example.plan_de_ahorro.data.model.Payment
import com.example.plan_de_ahorro.data.model.Plan
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("api/plans")
    suspend fun createPlan(@Body plan: Plan): Plan

    @GET("api/plans")
    suspend fun getPlans(): List<Plan>

    @GET("api/plans/{id}")
    suspend fun getPlanById(@Path("id") id: String): Plan

    @POST("api/members")
    suspend fun createMember(@Body member: Member): Member

    @GET("api/members/plan/{planId}")
    suspend fun getMembersByPlanId(@Path("planId") planId: String): List<Member>

    @POST("api/payments")
    suspend fun createPayment(@Body payment: Payment): Payment

    @GET("api/payments/member/{memberId}")
    suspend fun getPaymentsByMemberId(@Path("memberId") memberId: String): List<Payment>

    @GET("api/payments/plan/{planId}")
    suspend fun getPaymentsByPlanId(@Path("planId") planId: String): List<Payment>
}
