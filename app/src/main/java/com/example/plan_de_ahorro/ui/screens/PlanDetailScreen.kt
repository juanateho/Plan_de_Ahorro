package com.example.plan_de_ahorro.ui.screens

import androidx.compose.foundation.layout.*
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
fun PlanDetailScreen(
    navController: NavController,
    planId: String,
    viewModel: PlanViewModel = viewModel(factory = Injection.provideViewModelFactory())
) {
    val plan by viewModel.selectedPlan.collectAsState()
    val members by viewModel.members.collectAsState()

    LaunchedEffect(planId) {
        viewModel.loadPlanDetails(planId)
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text(plan?.name ?: "") }) }
    ) {
        paddingValues ->
        Column(modifier = Modifier.padding(paddingValues).padding(16.dp)) {
            plan?.let {
                Text("Motivo: ${it.motive}")
                Text("Meta: $${it.targetAmount}")
                Text("Meses: ${it.months}")
                Spacer(modifier = Modifier.height(16.dp))
                Text("Miembros", style = MaterialTheme.typography.titleMedium)
                LazyColumn {
                    items(members) {
                        member ->
                        Text(member.name)
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row {
                    Button(onClick = { navController.navigate("addMember/$planId") }) {
                        Text("Añadir Miembro")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = { navController.navigate("addPayment/$planId") }) {
                        Text("Registrar Pago")
                    }
                }
            }
        }
    }
}
