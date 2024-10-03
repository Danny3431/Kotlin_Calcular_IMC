package cl.bootcamp.myapplication9kotlin.viewmodel

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue


class IMCViewModel : ViewModel() {
    var age by mutableStateOf("")
    var weight by mutableStateOf("")
    var height by mutableStateOf("")
    var imcResult by mutableFloatStateOf(0f)

    fun calculateIMC() {
        val weightValue = weight.toFloatOrNull() ?: 0f
        val heightValue = height.toFloatOrNull()?.div(100) ?: 0f // Convertir cm a metros
        imcResult = if (heightValue > 0) weightValue / (heightValue * heightValue) else 0f
    }
}