package com.example.plan_de_ahorro.data.model

import com.google.gson.annotations.SerializedName

data class Plan(
    @SerializedName("_id") val id: String? = null,
    val name: String,
    val motive: String,
    val targetAmount: Double,
    val months: Int,
    val createdAt: String? = null,
    val members: List<Member>? = null,
    val payments: List<Payment>? = null,
    val totalCollected: Double? = null
)
