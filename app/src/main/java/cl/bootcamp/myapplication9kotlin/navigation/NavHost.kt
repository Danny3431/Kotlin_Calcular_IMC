package cl.bootcamp.myapplication9kotlin.navigation


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import cl.bootcamp.myapplication9kotlin.view.HomeView
import cl.bootcamp.myapplication9kotlin.view.ImcView
import cl.bootcamp.myapplication9kotlin.view.PatientsView


@Composable
fun AppNavHost(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "patients") {
        composable("patients") { PatientsView(navController) }
        composable("home/{id}", arguments = listOf(navArgument("id") { type = NavType.IntType })) {
        val id = it.arguments?.getInt("id")?: ""
        HomeView(navController, modifier, id as Int)
    }

        composable("imc") { ImcView(navController) } //va a el imc
    }


}

