package cl.bootcamp.myapplication9kotlin.viewmodel

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cl.bootcamp.myapplication9kotlin.model.PatientState
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

// Extensión para acceder a DataStore desde el contexto
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "patient_preferences")

class SharedViewModel(application: Application) : AndroidViewModel(application) {
    private val context: Context = application.applicationContext

    // Definición de las llaves de DataStore
    private val ID_KEY = intPreferencesKey("id")
    private val NAME_KEY = stringPreferencesKey("name")
    private val AGE_KEY = intPreferencesKey("age")
    private val HEIGHT_KEY = intPreferencesKey("height")
    private val WEIGHT_KEY = intPreferencesKey("weight")
    private val GENDER_KEY = stringPreferencesKey("gender")
    private val IMC_KEY = floatPreferencesKey("imc")
    private val HEALTH_STATUS_KEY = stringPreferencesKey("health_status")

    // LiveData para el paciente actual
    private val _patient = MutableLiveData<PatientState>()
    val patient: LiveData<PatientState> = _patient

    // LiveData para la lista de pacientes (ahora con List en lugar de MutableList)
    private val _patientsList = MutableLiveData<List<PatientState>>(emptyList())
    val patientsList: LiveData<List<PatientState>> = _patientsList

    // ID para generar pacientes únicos
    private var currentId = 1

    init {
        _patient.value = PatientState() // Inicializamos con un estado vacío
        loadPatientData() // Cargar datos del DataStore al iniciar
    }

    // Función para cargar datos del paciente desde DataStore
    private fun loadPatientData() {
        viewModelScope.launch {
            val preferences = context.dataStore.data.first()
            val patient = PatientState(
                id = preferences[ID_KEY] ?: 0,
                name = preferences[NAME_KEY] ?: "",
                age = preferences[AGE_KEY] ?: 0,
                height = preferences[HEIGHT_KEY] ?: 0,
                weight = preferences[WEIGHT_KEY] ?: 0,
                gender = preferences[GENDER_KEY] ?: "",
                imcResult = preferences[IMC_KEY] ?: 0f,
                healthStatus = preferences[HEALTH_STATUS_KEY] ?: ""
            )
            _patient.value = patient
        }
    }


    // Función para guardar datos del paciente en DataStore
    internal fun savePatientData(patient: PatientState) {
        viewModelScope.launch {
            context.dataStore.edit { preferences ->
                preferences[ID_KEY] = patient.id
                preferences[NAME_KEY] = patient.name
                preferences[AGE_KEY] = patient.age
                preferences[HEIGHT_KEY] = patient.height
                preferences[WEIGHT_KEY] = patient.weight
                preferences[GENDER_KEY] = patient.gender
                preferences[IMC_KEY] = patient.imcResult
                preferences[HEALTH_STATUS_KEY] = patient.healthStatus
            }
        }
    }
    // Función para actualizar el paciente completo
    fun updatePatient(patient: PatientState) {
         _patient.value = patient
        savePatientData(patient)
    }

    fun addPatient( patient: PatientState) {
        _patientsList.value = _patientsList.value?.toMutableList()?.apply {
            add(patient)
        }
        savePatientData(patient)
    }

    fun clearPatientData() {
        _patient.value = PatientState() // Reinicia a un nuevo PatientState vacío
    }




    // Función para agregar pacientes a la lista y guardar en DataStore
    fun addPatient(name: String, age: Int, height: Int, weight: Int, gender: String) {
        val imcResult = calculateIMC(weight, height)
        val healthStatus = determineHealthStatus(imcResult)

        val newPatient = PatientState(
            id = currentId++, // Incrementamos el ID
            name = name,
            age = age,
            height = height,
            weight = weight,
            gender = gender,
            imcResult = imcResult,
            healthStatus = healthStatus
        )

        // Agregamos el paciente a la lista actual
        _patientsList.value = _patientsList.value?.toMutableList()?.apply {
            add(newPatient) // Agregar el nuevo paciente
        }

        // Guardar los datos del nuevo paciente en DataStore
        savePatientData(newPatient)
    }

    // Calcular el IMC
    private fun calculateIMC(weight: Int, height: Int): Float {
        return if (height > 0) (weight / (height * height)) * 10000f else 0f
    }

    // Determinar el estado de salud basado en el IMC
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

    // Funciones para actualizar datos específicos del paciente actual
    fun updateName(name: String) {
        val currentPatient = _patient.value ?: PatientState()
        _patient.value = currentPatient.copy(name = name)
    }

    fun updateGender(gender: String) {
        val currentPatient = _patient.value ?: PatientState()
        _patient.value = currentPatient.copy(gender = gender)
    }

    fun updatePatientDetails(age: Int, height: Int, weight: Int) {
        val imcResult = calculateIMC(weight, height)
        val healthStatus = determineHealthStatus(imcResult)
        val currentPatient = _patient.value ?: PatientState()

        _patient.value = currentPatient.copy(
            age = age,
            height = height,
            weight = weight,
            imcResult = imcResult,
            healthStatus = healthStatus
        )
    }

    // Limpiar los datos del paciente actual
    fun clearPatient() {
        _patient.value = PatientState()
    }

    // Obtener un paciente por ID
    fun getPatientById(id: Int): PatientState? {
        return _patientsList.value?.find { it.id == id }
    }
}
