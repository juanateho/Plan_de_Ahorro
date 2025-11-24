package com.example.plan_de_ahorro.data.model

data class Payment(
    val _id: String? = null,
    val amount: Double,
    val memberId: String,
    val planId: String,
    val date: String? = null
)
