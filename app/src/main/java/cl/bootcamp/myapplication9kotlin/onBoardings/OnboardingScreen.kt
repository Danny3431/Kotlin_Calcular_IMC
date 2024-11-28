package cl.bootcamp.myapplication9kotlin.onBoardings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.navigation.NavController
import cl.bootcamp.myapplication9kotlin.R
import cl.bootcamp.myapplication9kotlin.ui.theme.PrimaryColor
import cl.bootcamp.myapplication9kotlin.ui.theme.PrimaryTextColor
import cl.bootcamp.myapplication9kotlin.ui.theme.SecondaryColor
import cl.bootcamp.myapplication9kotlin.ui.theme.SecondaryTextColor
import cl.bootcamp.myapplication9kotlin.ui.theme.TertiaryColor
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import cl.bootcamp.myapplication9kotlin.onBoardings.OnboardingPage
import cl.bootcamp.myapplication9kotlin.onBoardings.PageData
import com.airbnb.lottie.compose.LottieConstants


@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnboardingScreen(navController: NavController, dataStore: DataStore<Preferences>) {
    val coroutineScope = rememberCoroutineScope()
    val pages = listOf(
        OnboardingPage(
            title = "LISTADO DE PACIENTES",
            description = "Crea un listado de todos tus pacientes, para tener un mejor control",
            animation = R.raw.start
        ),
        OnboardingPage(
            title = "FÁCIL DE USAR!",
            description = "Facilita cargar datos de tus pacientes, de una forma sencilla",
            animation = R.raw.successful
        ),
        OnboardingPage(
            title = "CALCULADORA IMC!!",
            description = "Cuentas con una calculadora de IMC para registrar los datos de tu paciente",
            animation = R.raw.online_diagnosis
        )
    )




    val pagerState = rememberPagerState()
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            HorizontalPager(
                count = pages.size,
                state = pagerState,
                modifier = Modifier.weight(1f)
            ) { page ->
                OnboardingContent(pages[page])
            }

            if (pagerState.currentPage == pages.size - 1) {
                Button(
                    onClick = {
                        coroutineScope.launch {
                            dataStore.edit { prefs -> prefs[PreferencesKeys.SHOW_ONBOARDING] = false }
                        }
                        navController.navigate("patients") {
                            popUpTo("onboarding") { inclusive = true }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = SecondaryColor),
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    Text("EMPEZAR", color = PrimaryTextColor)
                }
            }
        }
    }
}

@Composable
fun OnboardingContent(page: PageData) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = when (page.title) {
                "LISTADO DE PACIENTES" -> SecondaryColor
                "FÁCIL DE USAR!" -> PrimaryColor
                else -> TertiaryColor
            }),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(page.animation))
        LottieAnimation(
            composition,
            iterations = LottieConstants.IterateForever,
            modifier = Modifier.size(300.dp)
        )
        Text(page.title, fontSize = 24.sp, color = PrimaryTextColor)
        Text(page.description, fontSize = 16.sp, color = SecondaryTextColor, textAlign = TextAlign.Center)
    }
}
