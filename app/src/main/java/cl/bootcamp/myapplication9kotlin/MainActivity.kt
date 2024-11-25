package cl.bootcamp.myapplication9kotlin

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.compose.rememberNavController
import cl.bootcamp.myapplication9kotlin.navigation.AppNavHost
import cl.bootcamp.myapplication9kotlin.ui.theme.MyApplication9KotlinTheme
import cl.bootcamp.myapplication9kotlin.viewmodel.SharedViewModel
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.core.Preferences

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "onboarding_prefs")
class MainActivity : ComponentActivity() {
    private val sharedViewModel: SharedViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplication9KotlinTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // Aplica el innerPadding al AppNavHost
                    AppNavHost(
                        modifier = Modifier.padding(innerPadding),
                        sharedViewModel = sharedViewModel,
                        dataStore = applicationContext.dataStore
                    )
                }
            }
        }
    }
}
