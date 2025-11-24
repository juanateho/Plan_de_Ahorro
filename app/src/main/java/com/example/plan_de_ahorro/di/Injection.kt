package com.example.plan_de_ahorro.di

import androidx.lifecycle.ViewModelProvider
import com.example.plan_de_ahorro.data.remote.RetrofitClient
import com.example.plan_de_ahorro.data.repository.PlanRepository
import com.example.plan_de_ahorro.ui.viewmodel.PlanViewModelFactory

object Injection {

    private fun providePlanRepository(): PlanRepository {
        return PlanRepository(RetrofitClient.instance)
    }

    fun provideViewModelFactory(): ViewModelProvider.Factory {
        return PlanViewModelFactory(providePlanRepository())
    }
}
