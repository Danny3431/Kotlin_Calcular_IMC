package cl.bootcamp.myapplication9kotlin.viewmodel


import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import cl.bootcamp.myapplication9kotlin.model.PatientState


class PatientsViewModel: ViewModel() {
    // Lista de pacientes
    private val _patients = mutableStateListOf<PatientState>()
    val patients: List<PatientState> get() = _patients // Getter para la lista de pacientes

    // Variable privada para manejar el id incremental
    private var nextId = 1

    // Función para agregar un nuevo paciente
    fun addPatient(name: String, age: Int, weight: Int, height: Int, gender: String, imcResult: Float, healthStatus: String) {
        val newPatient = PatientState(
            id = nextId++,  // Asignar el siguiente id y luego incrementarlo
            name = name,
            age = age ,
            weight = weight,
            height = height,
            gender = gender,
            imcResult = imcResult,
            healthStatus = healthStatus
        )
        _patients.add(newPatient)
    }
}