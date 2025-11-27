package com.example.plan_de_ahorro

import com.example.plan_de_ahorro.data.repository.PlanRepository
import com.example.plan_de_ahorro.ui.viewmodel.PlanViewModel
import com.example.plan_de_ahorro.ui.viewmodel.PlanViewModelFactory
import org.junit.Assert.*
import org.junit.Test
import org.mockito.kotlin.mock

class UnitTest {

    @Test
    fun `progress calculation`() {
        // 1: targetAmount > 0 y totalPaid < targetAmount
        var targetAmount = 1000.0
        var totalPaid = 500.0
        var percentage = if (targetAmount > 0) (totalPaid * 100) / targetAmount else 0.0
        assertEquals(50.0, percentage, 0.0)

        // 2: targetAmount > 0 y totalPaid == targetAmount
        targetAmount = 1000.0
        totalPaid = 1000.0
        percentage = if (targetAmount > 0) (totalPaid * 100) / targetAmount else 0.0
        assertEquals(100.0, percentage, 0.0)

        // 3: targetAmount > 0 y totalPaid is 0
        targetAmount = 1000.0
        totalPaid = 0.0
        percentage = if (targetAmount > 0) (totalPaid * 100) / targetAmount else 0.0
        assertEquals(0.0, percentage, 0.0)
    }

    @Test
    fun `Mock API`() {
        val repository = mock<PlanRepository>()
        val factory = PlanViewModelFactory(repository)
        val viewModel = factory.create(PlanViewModel::class.java)

        assertNotNull("ViewModel should not be null", viewModel)
        assertTrue("ViewModel should be an instance of PlanViewModel", viewModel is PlanViewModel)
    }
}