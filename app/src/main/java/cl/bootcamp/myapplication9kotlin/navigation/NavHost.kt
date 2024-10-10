package cl.bootcamp.myapplication9kotlin.navigation


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import cl.bootcamp.myapplication9kotlin.view.PatientsView
import cl.bootcamp.myapplication9kotlin.view.ImcInputView

import cl.bootcamp.myapplication9kotlin.view.PatientsDataView


@Composable
fun AppNavHost(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "patients") {
        composable("patients") { PatientsView(navController) }
        composable("imcInput/{patientId}") { backStackEntry ->
            val patientId = backStackEntry.arguments?.getString("patientId")?.toIntOrNull() ?: 0
            // Usa toIntOrNull para evitar excepciones
            ImcInputView(navController, patientId)
        }

        composable("data") { PatientsDataView(navController) } //va a el imc
    }


}

