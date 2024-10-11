package cl.bootcamp.myapplication9kotlin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.bootcamp.myapplication9kotlin.model.PatientState


//private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "patient_preferences")

class SharedViewModel : ViewModel() {

    // LiveData para el paciente actual
    private val _patient = MutableLiveData<PatientState>()
    val patient: LiveData<PatientState> = _patient

    init {
        _patient.value = PatientState()
        //loadPatientData()
    }

    /*
    // Cargar los datos del paciente desde SharedPreferences
    private fun loadPatientData() {
    viewModelScope.launch {
        try {
            val preferences = context.dataStore.data.first()
            _patient.value = PatientState(
                name = preferences[PreferencesKeys.NAME] ?: "",
                age = preferences[PreferencesKeys.AGE] ?: 0,
                height = preferences[PreferencesKeys.HEIGHT] ?: 0,
                weight = preferences[PreferencesKeys.WEIGHT] ?: 0,
                gender = preferences[PreferencesKeys.GENDER] ?: "",
                imcResult = preferences[PreferencesKeys.IMC_RESULT] ?: 0f
            )
        } catch (e: Exception) {
            // Manejar el error (p. ej. log)
        }
    }
}

    // Guardar los datos del paciente en SharedPreferences
    private fun savePatientData(patientState: PatientState) {
    viewModelScope.launch {
        try {
            context.dataStore.edit { preferences ->
                preferences[PreferencesKeys.NAME] = patientState.name
                preferences[PreferencesKeys.AGE] = patientState.age
                preferences[PreferencesKeys.HEIGHT] = patientState.height
                preferences[PreferencesKeys.WEIGHT] = patientState.weight
                preferences[PreferencesKeys.GENDER] = patientState.gender
                preferences[PreferencesKeys.IMC_RESULT] = patientState.imcResult ?: 0f
            }
        } catch (e: Exception) {
            // Manejar el error
        }
    }
}

    // Método para actualizar el paciente actual
    fun updatePatientDetails(newPatient: PatientState) {
        _patient.value = newPatient
        savePatientData(newPatient)
    }
    // Métodos para guardar y recuperar datos
    object PreferencesKeys {
        val NAME = stringPreferencesKey("name")
        val AGE = intPreferencesKey("age")
        val HEIGHT = intPreferencesKey("height")
        val WEIGHT = intPreferencesKey("weight")
        val GENDER = stringPreferencesKey("gender")
        val IMC_RESULT = floatPreferencesKey("imc_result")
    }
     */

    fun clearPatient() {
        _patient.value = PatientState()
    }

    fun updatePatientDetails(newPatient: PatientState) {
        _patient.value = newPatient
    }

    fun getPatientById(id: Int): PatientState? {
        // Lógica para obtener el paciente por ID, si es necesario
        return _patient.value?.takeIf { it.id == id }
    }


    private var currentId = 1  // El ID comienza en 1 y se incrementa


    //lista de pacientes
    private val _patientsList = MutableLiveData<List<PatientState>>(emptyList())
    val patientsList: LiveData<List<PatientState>> = _patientsList


    // Actualizar solo el nombre

    fun updateName(name: String) {
        val currentPatient = _patient.value ?: PatientState()
        _patient.value = currentPatient.copy(name = name)
    }

    // Actualizar el género
    fun updateGender(gender: String) {
        val currentPatient = _patient.value ?: PatientState()
        _patient.value = currentPatient.copy(gender = gender)
    }

    // Actualizar los detalles del paciente (edad, altura, peso)

    fun updatePatientDetails(age: Int, height: Int, weight: Int) {
        val currentPatient = _patient.value ?: PatientState()
        val imc = calculateIMC(weight, height).toFloat()
        val healthStatus = determineHealthStatus(imc)

        _patient.value = currentPatient.copy(age = age,
            height = height,
            weight = weight,
            imcResult = imc,
            healthStatus = healthStatus)
    }

    // Calcular el IMC
    private fun calculateIMC(weight: Int, height: Int): Number {
        return if (weight > 0 && height > 0) {
            weight / (height * height) // Fórmula del IMC
        } else {
            0f
        }
    }


    // Determinar el estado de salud según el IMC y la OMS
    private fun determineHealthStatus(imc: Float): String {
        return when {
            imc < 18.5 -> "Bajo peso"
            imc in 18.5..24.9 -> "Peso normal"
            imc in 25.0..29.9 -> "Pre-obesidad"
            imc in 30.0..34.9 -> "Obesidad grado I"
            imc in 35.0..39.9 -> "Obesidad grado II"
            imc >= 40 -> "Obesidad grado III"
            else -> "Desconocido"
        }
    }
    // Actualizar el paciente actual
    fun updatePatient(patient: PatientState) {

        _patient.value = patient
    }



    // Agregar el paciente a la lista y asignarle un ID único
    fun addPatientToList(patientState: PatientState) {
        val patientWithId = patientState.copy(id = currentId++)  // Asignar ID
        _patientsList.value = _patientsList.value?.plus(patientWithId)
        _patient.value = PatientState()  // Resetear el paciente actual
    }
}
