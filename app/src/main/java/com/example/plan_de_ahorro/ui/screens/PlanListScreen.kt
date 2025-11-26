package com.example.plan_de_ahorro.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.plan_de_ahorro.utils.Injection
import com.example.plan_de_ahorro.ui.viewmodel.PlanViewModel
import com.example.plan_de_ahorro.utils.FormatUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanListScreen(
    navController: NavController,
    viewModel: PlanViewModel = viewModel(factory = Injection.provideViewModelFactory())
) {
    val plansSummary by viewModel.plansSummary.collectAsState(initial = emptyList())

    LaunchedEffect(Unit) {
        viewModel.loadPlans()
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Plan Familiar de Ahorro") }) },
        floatingActionButtonPosition = FabPosition.Center, // Posición central
        floatingActionButton = {
            ExtendedFloatingActionButton( // Usamos ExtendedFAB para que se vea mejor centrado
                onClick = { navController.navigate("createPlan") },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Text("Crear plan")
            }
        }
    ) { paddingValues ->
        LazyColumn(modifier = Modifier.padding(paddingValues).padding(16.dp)) {
            items(plansSummary) { summary ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable { navController.navigate("planDetail/${summary.plan.id}") },
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp).fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = summary.plan.name, 
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = "Meta: ${FormatUtils.formatCurrency(summary.plan.targetAmount)}", style = MaterialTheme.typography.bodyMedium)
                            Text(
                                text = "Faltante: ${FormatUtils.formatCurrency(summary.remainingAmount)}", 
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.error
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = "Duración: ${summary.plan.months} meses", style = MaterialTheme.typography.bodySmall)
                        }
                        
                        // Percentage display
                        Text(
                            text = "${String.format("%.1f", summary.progressPercentage)}%",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface // Blanco/TextPrimary según el tema oscuro
                        )
                    }
                }
            }
        }
    }
}
