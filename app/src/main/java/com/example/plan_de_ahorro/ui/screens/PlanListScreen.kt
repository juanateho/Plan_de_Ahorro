package com.example.plan_de_ahorro.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.plan_de_ahorro.di.Injection
import com.example.plan_de_ahorro.ui.viewmodel.PlanViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanListScreen(
    navController: NavController,
    viewModel: PlanViewModel = viewModel(factory = Injection.provideViewModelFactory())
) {
    val plans by viewModel.plans.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadPlans()
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Plan Familiar de Ahorro") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("createPlan") }) {
                Text("Crear plan")
            }
        }
    ) { paddingValues ->
        LazyColumn(modifier = Modifier.padding(paddingValues).padding(16.dp)) {
            items(plans) { plan ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable { navController.navigate("planDetail/${plan.id}") }
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = plan.name, style = MaterialTheme.typography.titleMedium)
                        Text(text = "${plan.months} meses")
                        Text(text = "$${plan.targetAmount}")
                    }
                }
            }
        }
    }
}
