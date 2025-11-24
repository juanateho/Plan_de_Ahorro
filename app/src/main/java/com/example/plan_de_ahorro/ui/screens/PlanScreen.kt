package com.example.plan_de_ahorro.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.plan_de_ahorro.di.Injection
import com.example.plan_de_ahorro.ui.viewmodel.PlanViewModel

@Composable
fun PlanScreen(viewModel: PlanViewModel = viewModel(factory = Injection.provideViewModelFactory())) {
    val plans by viewModel.plans.collectAsState()
    var newPlanName by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.loadPlans()
    }

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = newPlanName,
            onValueChange = { newPlanName = it },
            label = { Text("New Plan Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            if (newPlanName.isNotBlank()) {
                viewModel.createPlan(newPlanName)
                newPlanName = ""
            }
        }) {
            Text("Create Plan")
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(plans) {
                plan ->
                Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = plan.name, style = MaterialTheme.typography.titleMedium)
                        plan.totalCollected?.let {
                            Text(text = "Total Collected: $it")
                        }
                    }
                }
            }
        }
    }
}