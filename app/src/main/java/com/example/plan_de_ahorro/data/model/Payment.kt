package com.example.plan_de_ahorro.data.model

import com.google.gson.annotations.SerializedName

data class Payment(
    @SerializedName("_id") val id: String? = null,
    val amount: Double,
    val memberId: String,
    val planId: String,
    val date: String? = null
)
