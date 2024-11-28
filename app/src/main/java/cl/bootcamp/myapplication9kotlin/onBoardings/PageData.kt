package cl.bootcamp.myapplication9kotlin.onBoardings

import androidx.annotation.RawRes

// Clase para representar las páginas del Onboarding
data class PageData(
    val title: String,
    val description: String,
    @RawRes val animation: Int // Referencia al archivo Lottie en res/raw
)

// Alias para simplificar la referencia (opcional)
typealias OnboardingPage = PageData