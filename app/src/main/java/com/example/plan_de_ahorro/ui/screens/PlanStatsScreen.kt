package com.example.plan_de_ahorro.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.plan_de_ahorro.di.Injection
import com.example.plan_de_ahorro.ui.viewmodel.PlanViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanStatsScreen(
    navController: NavController,
    planId: String,
    viewModel: PlanViewModel = viewModel(factory = Injection.provideViewModelFactory())
) {
    val plan by viewModel.selectedPlan.collectAsState()
    val members by viewModel.members.collectAsState()
    val payments by viewModel.payments.collectAsState()

    LaunchedEffect(planId) {
        viewModel.loadPlanDetails(planId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Estadísticas") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues).padding(16.dp)) {
            plan?.let { currentPlan ->
                Card(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = currentPlan.name,
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "Resumen de aportes por miembro",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                // Table Header
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                    shape = MaterialTheme.shapes.small
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            "Miembro",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.weight(2f)
                        )
                        Text(
                            "Pagos",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            "Total",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.End,
                            modifier = Modifier.weight(1.5f)
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(8.dp))

                LazyColumn {
                    items(members) { member ->
                        val memberPayments = payments.filter { it.memberId == member.id }
                        val count = memberPayments.size
                        val total = memberPayments.sumOf { it.amount }

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    member.name,
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier.weight(2f)
                                )
                                Text(
                                    count.toString(),
                                    style = MaterialTheme.typography.bodyMedium,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.weight(1f)
                                )
                                Text(
                                    "$${String.format("%.2f", total)}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    textAlign = TextAlign.End,
                                    modifier = Modifier.weight(1.5f)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
