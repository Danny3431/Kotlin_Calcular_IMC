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
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import cl.bootcamp.myapplication9kotlin.onBoardings.OnboardingScreen
import cl.bootcamp.myapplication9kotlin.onBoardings.PreferencesKeys
import cl.bootcamp.myapplication9kotlin.onBoardings.OnboardingScreen
import cl.bootcamp.myapplication9kotlin.view.SplashScreen


@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    sharedViewModel: SharedViewModel,
    dataStore: androidx.datastore.core.DataStore<Preferences>
) {
    val navController = rememberNavController()

    // Check if onboarding should be shown
    val showOnboarding = runBlocking {
        dataStore.data.first()[PreferencesKeys.SHOW_ONBOARDING] ?: true
    }

    NavHost(
        navController = navController,
        startDestination = if (showOnboarding) "splash" else "patients"
    ) {
        composable("splash") { SplashScreen(navController) }
        composable("onboarding") { OnboardingScreen(navController, dataStore) }
        composable("home") { HomeView(navController) }
        composable("patients") { PatientsView(navController, sharedViewModel) }
        composable("imcInput/{patientId}") { backStackEntry ->
            val patientId = backStackEntry.arguments?.getString("patientId")?.toIntOrNull() ?: 0
            ImcInputView(navController, sharedViewModel, patientId)
        }
    }
}