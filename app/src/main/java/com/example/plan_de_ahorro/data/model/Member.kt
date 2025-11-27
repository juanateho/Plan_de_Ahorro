package com.example.plan_de_ahorro.data.model

import com.google.gson.annotations.SerializedName

data class Member(
    @SerializedName("_id") val id: String? = null,
    val name: String,
    val planId: String,
    val contributionPerMonth: Double,
    val joinedAt: String? = null
)
