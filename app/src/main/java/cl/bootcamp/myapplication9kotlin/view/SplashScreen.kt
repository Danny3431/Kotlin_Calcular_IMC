package cl.bootcamp.myapplication9kotlin.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cl.bootcamp.myapplication9kotlin.R
import cl.bootcamp.myapplication9kotlin.ui.theme.PrimaryColor
import kotlinx.coroutines.delay
import okhttp3.internal.wait
import kotlin.time.Duration

@Composable
fun SplashScreen(navController: NavController) {
    LaunchedEffect(Unit) {
        delay( 5000) // Muestra el logo durante 5 segundos
        navController.navigate("onboarding") {
            popUpTo("splash") { inclusive = true } // Elimina el Splash de la pila
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryColor),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.health),
            contentDescription = "Logo Vida Sana",
            modifier = Modifier.size(200.dp)
        )
    }
}

