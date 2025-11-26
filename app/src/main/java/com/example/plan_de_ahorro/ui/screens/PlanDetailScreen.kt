package com.example.plan_de_ahorro.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.plan_de_ahorro.di.Injection
import com.example.plan_de_ahorro.ui.viewmodel.PlanViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanDetailScreen(
    navController: NavController,
    planId: String,
    viewModel: PlanViewModel = viewModel(factory = Injection.provideViewModelFactory())
) {
    val plan by viewModel.selectedPlan.collectAsState()
    val members by viewModel.members.collectAsState()
    val payments by viewModel.payments.collectAsState()
    val remainingAmount by viewModel.planRemainingAmount.collectAsState(initial = 0.0)
    
    var selectedMonth by remember { mutableStateOf<Int?>(null) } // Null means "All months"

    LaunchedEffect(planId) {
        viewModel.loadPlanDetails(planId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(plan?.name ?: "") },
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
    ) {
        paddingValues ->
        Column(modifier = Modifier.padding(paddingValues).padding(16.dp)) {
            plan?.let { currentPlan ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "Resumen Financiero", style = MaterialTheme.typography.titleMedium)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Motivo: ${currentPlan.motive}")
                        Text("Meta Total: $${currentPlan.targetAmount}")
                        Text("Faltante: $${remainingAmount}", color = MaterialTheme.colorScheme.error)
                        Text("Meses: ${currentPlan.months}")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text("Miembros", style = MaterialTheme.typography.titleMedium)
                
                // We will use a LazyColumn for the whole content that scrolls
                // But wait, we have "Members" AND "History". 
                // Let's structure it so we don't have nested LazyColumns.
                
                val filteredPayments = if (selectedMonth == null) {
                    payments
                } else {
                    payments.filter { 
                        try {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && it.date != null) {
                                // Assuming date format is ISO (YYYY-MM-DD) or similar that LocalDate parses
                                // If the format is different, adjust the formatter.
                                // Let's try basic ISO parsing first.
                                val date = LocalDate.parse(it.date.substring(0, 10))
                                date.monthValue == selectedMonth
                            } else {
                                true // If no date or old android, just show it (or filter out? show is safer)
                            }
                        } catch (e: Exception) {
                           true 
                        }
                    }
                }

                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(members) { member ->
                        Card(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(member.name, style = MaterialTheme.typography.bodyLarge)
                                Text("Aporte mensual: $${member.contributionPerMonth}", style = MaterialTheme.typography.bodySmall)
                            }
                        }
                    }
                    
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Historial de Pagos", style = MaterialTheme.typography.titleMedium)
                            
                            // Filter Dropdown
                            var expanded by remember { mutableStateOf(false) }
                            Box {
                                IconButton(onClick = { expanded = true }) {
                                    Icon(Icons.Default.DateRange, contentDescription = "Filtrar por mes")
                                }
                                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                                    DropdownMenuItem(
                                        text = { Text("Todos") },
                                        onClick = { 
                                            selectedMonth = null 
                                            expanded = false
                                        }
                                    )
                                    // Generate months list (1 to 12)
                                    for (i in 1..12) {
                                        val monthName = java.time.Month.of(i).name.lowercase().capitalize()
                                        DropdownMenuItem(
                                            text = { Text(monthName) },
                                            onClick = {
                                                selectedMonth = i
                                                expanded = false
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }

                    items(filteredPayments) { payment ->
                        val memberName = members.find { it.id == payment.memberId }?.name ?: "Desconocido"
                        val formattedDate = try {
                             if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && payment.date != null) {
                                 payment.date.substring(0, 10) // Display simplificado YYYY-MM-DD
                             } else {
                                 "Sin fecha"
                             }
                        } catch (e: Exception) { "Fecha inválida" }

                        Card(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp).fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column {
                                    Text(memberName)
                                    Text(formattedDate, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                }
                                Text("$${payment.amount}", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.primary)
                            }
                        }
                    }
                    
                    if (filteredPayments.isEmpty()) {
                        item {
                             Text("No hay pagos registrados para este criterio.", 
                                  modifier = Modifier.padding(8.dp).fillMaxWidth(), 
                                  style = MaterialTheme.typography.bodyMedium,
                                  color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                    Button(onClick = { navController.navigate("addMember/$planId") }) {
                        Text("Añadir Miembro")
                    }
                    Button(onClick = { navController.navigate("addPayment/$planId") }) {
                        Text("Registrar Pago")
                    }
                }
            }
        }
    }
}

// Helper extension for capitalization if needed, though simple string manipulation works
private fun String.capitalize() = replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
