package com.example.plan_de_ahorro.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.plan_de_ahorro.di.Injection
import com.example.plan_de_ahorro.ui.viewmodel.PlanViewModel
import com.example.plan_de_ahorro.utils.FormatUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPaymentScreen(
    navController: NavController,
    planId: String,
    viewModel: PlanViewModel = viewModel(factory = Injection.provideViewModelFactory())
) {
    val members by viewModel.members.collectAsState()
    val selectedPlan by viewModel.selectedPlan.collectAsState()
    val remainingAmount by viewModel.planRemainingAmount.collectAsState(initial = 0.0)
    
    var selectedMemberId by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    
    val context = LocalContext.current

    LaunchedEffect(planId) {
        viewModel.loadPlanDetails(planId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Registrar Pago") },
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
            ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
                OutlinedTextField(
                    value = members.find { it.id == selectedMemberId }?.name ?: "",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Miembro") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier.fillMaxWidth().menuAnchor()
                )
                ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    members.forEach { member ->
                        DropdownMenuItem(
                            text = { Text(member.name) },
                            onClick = {
                                selectedMemberId = member.id!!
                                expanded = false
                            }
                        )
                    }
                }
            }
            OutlinedTextField(value = amount, onValueChange = { amount = it }, label = { Text("Monto") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                val amountValue = amount.toDoubleOrNull()
                val member = members.find { it.id == selectedMemberId }
                
                if (amountValue == null || member == null) {
                    Toast.makeText(context, "Por favor seleccione un miembro e ingrese un monto válido", Toast.LENGTH_SHORT).show()
                    return@Button
                }

                if (amountValue < member.contributionPerMonth) {
                    Toast.makeText(context, "El pago no puede ser menor al monto mínimo acordado (${FormatUtils.formatCurrency(member.contributionPerMonth)})", Toast.LENGTH_LONG).show()
                    return@Button
                }

                if (amountValue > remainingAmount) {
                    Toast.makeText(context, "El pago no puede ser mayor al monto faltante del plan (${FormatUtils.formatCurrency(remainingAmount)})", Toast.LENGTH_LONG).show()
                    return@Button
                }

                viewModel.createPayment(amountValue, selectedMemberId, planId)
                navController.popBackStack()
            }) {
                Text("Registrar")
            }
        }
    }
}
