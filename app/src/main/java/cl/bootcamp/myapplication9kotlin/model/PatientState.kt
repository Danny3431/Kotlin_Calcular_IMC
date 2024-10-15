package cl.bootcamp.myapplication9kotlin.model

data class PatientState(
    val id: Int = 0,
    val name: String= "",
    var age:Int = 0,
    var weight: Int = 0,  // Peso en kg
    var height: Int = 0,  // Altura en cm
    var gender: String = "", // "Hombre" o "Mujer"
    val imcResult: Float = 0f,  //Índice de masa corporal (IMC)
    var healthStatus: String = "", // Estado de salud según la OMS
    val showModal: Boolean = false,
    val patientCompleted: Boolean = false


)

