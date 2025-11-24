package com.example.plan_de_ahorro.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.plan_de_ahorro.di.Injection
import com.example.plan_de_ahorro.ui.viewmodel.PlanViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPaymentScreen(
    navController: NavController,
    planId: String,
    viewModel: PlanViewModel = viewModel(factory = Injection.provideViewModelFactory())
) {
    val members by viewModel.members.collectAsState()
    var selectedMemberId by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }


    Scaffold(
        topBar = { TopAppBar(title = { Text("Registrar Pago") }) }
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
                viewModel.createPayment(amount.toDouble(), selectedMemberId, planId)
                navController.popBackStack()
            }) {
                Text("Registrar")
            }
        }
    }
}
