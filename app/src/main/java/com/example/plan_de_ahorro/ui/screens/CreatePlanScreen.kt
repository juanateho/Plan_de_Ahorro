package com.example.plan_de_ahorro.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.plan_de_ahorro.utils.Injection
import com.example.plan_de_ahorro.ui.viewmodel.PlanViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatePlanScreen(
    navController: NavController,
    viewModel: PlanViewModel = viewModel(factory = Injection.provideViewModelFactory())
) {
    var name by remember { mutableStateOf("") }
    var motive by remember { mutableStateOf("") }
    var targetAmount by remember { mutableStateOf("") }
    var months by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Crear Plan") },
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
            OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Nombre del Plan") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = motive, onValueChange = { motive = it }, label = { Text("Motivo") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = targetAmount, onValueChange = { targetAmount = it }, label = { Text("Meta de Ahorro") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = months, onValueChange = { months = it }, label = { Text("Meses") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                viewModel.createPlan(name, motive, targetAmount.toDouble(), months.toInt())
                navController.popBackStack()
            }) {
                Text("Crear")
            }
        }
    }
}
