package cl.bootcamp.myapplication9kotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import cl.bootcamp.myapplication9kotlin.navigation.AppNavHost
import cl.bootcamp.myapplication9kotlin.ui.theme.MyApplication9KotlinTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplication9KotlinTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // Aplica el innerPadding al AppNavHost
                    AppNavHost(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}