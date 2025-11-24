package com.example.plan_de_ahorro.data.model

data class Plan(
    val _id: String? = null,
    val name: String,
    val members: List<Member>? = null,
    val payments: List<Payment>? = null,
    val totalCollected: Double? = null
)
