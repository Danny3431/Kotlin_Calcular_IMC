package cl.bootcamp.myapplication9kotlin.viewmodel

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import cl.bootcamp.myapplication9kotlin.model.PatientState
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson


class IMCViewModel : ViewModel() {
    val patients: MutableList<PatientState> = mutableListOf()
    val patient: PatientState? = null


    var state = mutableStateOf(PatientState())
        private set

    var errorMessage = mutableStateOf("") // Para almacenar mensajes de error
        private set

    fun updateWeight(weight: Int) {
        state.value = state.value.copy(weight = weight) // No necesita toIntOrNull()
        errorMessage.value = "" // Limpiar mensaje de error al actualizar
    }

    fun updateHeight(height: Int) {
        state.value = state.value.copy(height = height) // No necesita toIntOrNull()
        errorMessage.value = "" // Limpiar mensaje de error al actualizar
    }

    fun updateAge(age: Int) {
        state.value = state.value.copy(age = age) // No necesita toIntOrNull()
        errorMessage.value = "" // Limpiar mensaje de error al actualizar
    }

    fun updateGender(gender: String) {
        state.value = state.value.copy(gender = gender)
        errorMessage.value = "" // Limpiar mensaje de error al actualizar
    }

    fun healthStatus(imc: Float): String {
        return when {
            imc < 18.5 -> "Bajo peso"
            imc < 24.9 -> "Peso normal"
            imc < 29.9 -> "Sobrepeso"
            imc < 34.9 -> "Obesidad grado I"
            imc < 39.9 -> "Obesidad grado II"
            else -> {
                "Obesidad grado III "
            }
        }
    }

    fun validateFields(): Boolean {
        errorMessage.value = "" // Limpiar errores anteriores

        val errors = mutableListOf<String>()


        if ( state.value.weight <= 0) {
            errors.add("Por favor, ingrese un peso válido.")
        }
        if ( state.value.height <= 0 || state.value.height > 270) {
            errors.add("Por favor, ingrese una altura válida (máx. 270 cm).") // Altura máxima de 270 cm
        }
        if (state.value.age <= 0 || state.value.age > 125) { // edad maxima de 125  años es una edad razonable
            errors.add("Por favor, ingrese una edad válida.")
        }
        if (state.value.gender.isEmpty()) {
            errors.add("Por favor, seleccione un género.")
        }

        if (errors.isNotEmpty()) {
            errorMessage.value = errors.joinToString("\n")
            return false
        }

        return true
    }

    fun calculateIMC(): Float? {
        val weightValue = state.value.weight
        val heightValue = state.value.height / 100f // Convertir cm a metros

        if (state.value.age <= 0 || state.value.age > 125) {
            errorMessage.value = "Por favor, ingrese una edad válida."
            return null
        }

        if (weightValue <= 0) {
            errorMessage.value = "Por favor, ingrese un peso válido."
            return null
        }
        if (heightValue <= 0) {
            errorMessage.value = "Por favor, ingrese una altura válida."
            return null
        }

        // Calcular el IMC
        val imcResult = weightValue / (heightValue * heightValue)

        // Actualizar el estado con el resultado del IMC
        state.value = state.value.copy(imcResult = imcResult)

        // Limpiar mensaje de error al calcular
        errorMessage.value = ""
        return imcResult
    }

    fun addPatient(patient: PatientState) {
        patients.add(patient)
    }

    fun getPatient(index: Int): PatientState? {
        return if (index in patients.indices) {
            patients[index]
        } else {
            null
        }
    }

    // Métodos para guardar y cargar pacientes en DataStore
    suspend fun savePatientsToDataStore(dataStore: DataStore<Preferences>) {
        val patientJson = Gson().toJson(patients)
        dataStore.edit { preferences ->
            preferences[stringPreferencesKey("patients")] = patientJson
        }
    }

    suspend fun loadPatientsFromDataStore(dataStore: DataStore<Preferences>) {
        dataStore.data.collect { preferences ->
            val patientJson = preferences[stringPreferencesKey("patients")] ?: return@collect
            val patientListType = object : TypeToken<List<PatientState>>() {}.type
            patients.clear()
            patients.addAll(Gson().fromJson(patientJson, patientListType))
        }
    }

    fun saveCurrentPatient() {
        if (validateFields()) {
            addPatient(state.value)
        }
    }
}