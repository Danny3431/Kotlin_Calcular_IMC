package cl.bootcamp.myapplication9kotlin.viewmodel

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import cl.bootcamp.myapplication9kotlin.Model.StateIMC


class IMCViewModel : ViewModel() {
    var state = mutableStateOf(StateIMC())
        private set

    var errorMessage = mutableStateOf("") // Para almacenar mensajes de error
        private set


    fun updateWeight(weight: String) {
        state.value = state.value.copy(weight = weight)
        errorMessage.value = "" // Limpiar mensaje de error al actualizar
    }

    fun updateHeight(height: String) {
        state.value = state.value.copy(height = height)
        errorMessage.value = "" // Limpiar mensaje de error al actualizar
    }

    fun updateAge(age: String) {
        state.value = state.value.copy(age = age)
        errorMessage.value = "" // Limpiar mensaje de error al actualizar

    }

    fun validateFields() {
        errorMessage.value = "" // Limpiar errores anteriores

        val errors = mutableListOf<String>()

        if (state.value.weight.isEmpty()) {
            errors.add("Por favor, ingrese un peso válido.")
        }
        if (state.value.height.isEmpty() || (state.value.height.toIntOrNull() ?: 0 > 270)) {
            errors.add("Por favor, ingrese una altura válida (máx. 270 cm).") // Limitar a 3 dígitos y máximo 270 cm
        }
        if (state.value.age.isEmpty()) {
            errors.add("Por favor, ingrese una edad válida.")
        }

        // Concatenar los errores en un solo mensaje
        errorMessage.value = errors.joinToString("\n")
    }


    fun calculateIMC() {
        val weightValue = state.value.weight.toFloatOrNull()
        val heightValue = state.value.height.toFloatOrNull()?.div(100) // Convertir cm a metros

        if (weightValue == null || weightValue <= 0) {
            errorMessage.value = "Por favor, ingrese un peso válido."
            return
        }
        if (heightValue == null || heightValue <= 0) {
            errorMessage.value = "Por favor, ingrese una altura válida."
            return
        }

        val imcResult = weightValue / (heightValue * heightValue)
        state.value = state.value.copy(imcResult = imcResult)
        errorMessage.value = "" // Limpiar mensaje de error al calcular
    }
}