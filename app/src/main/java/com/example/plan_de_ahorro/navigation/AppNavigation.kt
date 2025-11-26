package com.example.plan_de_ahorro.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.plan_de_ahorro.ui.screens.AddMemberScreen
import com.example.plan_de_ahorro.ui.screens.AddPaymentScreen
import com.example.plan_de_ahorro.ui.screens.CreatePlanScreen
import com.example.plan_de_ahorro.ui.screens.PlanDetailScreen
import com.example.plan_de_ahorro.ui.screens.PlanListScreen
import com.example.plan_de_ahorro.ui.screens.PlanStatsScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "planList") {
        composable("planList") {
            PlanListScreen(navController = navController)
        }
        composable("createPlan") {
            CreatePlanScreen(navController = navController)
        }
        composable("planDetail/{planId}") { backStackEntry ->
            val planId = backStackEntry.arguments?.getString("planId")
            planId?.let {
                PlanDetailScreen(navController = navController, planId = it)
            }
        }
        composable("addMember/{planId}") { backStackEntry ->
            val planId = backStackEntry.arguments?.getString("planId")
            planId?.let {
                AddMemberScreen(navController = navController, planId = it)
            }
        }
        composable("addPayment/{planId}") { backStackEntry ->
            val planId = backStackEntry.arguments?.getString("planId")
            planId?.let {
                AddPaymentScreen(navController = navController, planId = it)
            }
        }
        composable("planStats/{planId}") { backStackEntry ->
            val planId = backStackEntry.arguments?.getString("planId")
            planId?.let {
                PlanStatsScreen(navController = navController, planId = it)
            }
        }
    }
}
