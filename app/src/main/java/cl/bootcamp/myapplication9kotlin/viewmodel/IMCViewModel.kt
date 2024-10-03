package cl.bootcamp.myapplication9kotlin.viewmodel

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import cl.bootcamp.myapplication9kotlin.Model.StateIMC


class IMCViewModel : ViewModel() {
    var state = mutableStateOf(StateIMC())
        private set

    fun updateWeight(weight: String) {
        state.value = state.value.copy(weight = weight)
    }

    fun updateHeight(height: String) {
        state.value = state.value.copy(height = height)
    }

    fun updateAge(age: String) {
        state.value = state.value.copy(age = age)
    }

    fun calculateIMC() {
        val weightValue = state.value.weight.toFloatOrNull() ?: 0f
        val heightValue = state.value.height.toFloatOrNull()?.div(100) ?: 0f // Convertir cm a metros
        val imcResult = if (heightValue > 0) weightValue / (heightValue * heightValue) else 0f
        state.value = state.value.copy(imcResult = imcResult)
    }
}