package cl.bootcamp.myapplication9kotlin.navigation


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cl.bootcamp.myapplication9kotlin.view.HomeView
import cl.bootcamp.myapplication9kotlin.view.PatientsView
import cl.bootcamp.myapplication9kotlin.view.ImcInputView
import cl.bootcamp.myapplication9kotlin.viewmodel.SharedViewModel

@Composable
fun AppNavHost(modifier: Modifier = Modifier, sharedViewModel: SharedViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeView(navController) }
        composable("patients") { PatientsView(navController, sharedViewModel) }
        composable("imcInput/{patientId}") { backStackEntry ->
            val patientId = backStackEntry.arguments?.getString("patientId")?.toIntOrNull() ?: 0
            ImcInputView(navController, sharedViewModel, patientId)
        }
    }

}