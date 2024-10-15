package cl.bootcamp.myapplication9kotlin.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cl.bootcamp.myapplication9kotlin.components.GenderSelector
import cl.bootcamp.myapplication9kotlin.components.InputField
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import cl.bootcamp.myapplication9kotlin.R
import cl.bootcamp.myapplication9kotlin.model.PatientState
import cl.bootcamp.myapplication9kotlin.viewmodel.IMCViewModel
import cl.bootcamp.myapplication9kotlin.viewmodel.PatientsViewModel
import cl.bootcamp.myapplication9kotlin.viewmodel.SharedViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImcInputView(navController: NavController,sharedViewModel: SharedViewModel, patientId: Int) {
    val sharedViewModel: SharedViewModel = viewModel()
    val patientState by sharedViewModel.patient.observeAsState(PatientState())

    // Estados para manejar errores y resultados
    var snackbarVisible by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf("") }
    var showPatientData by remember { mutableStateOf(false) } // Nuevo estado para mostrar los datos del paciente

    // Actualizar el IMC cuando se ingresa el peso y la altura
    val imcResult by remember(patientState.weight, patientState.height) {
        derivedStateOf {
            if (patientState.height > 0) {
                (patientState.weight / ((patientState.height / 100.0) * (patientState.height / 100.0))).toFloat()
            } else {
                0f
            }
        }
    }
    // Determinar el estado de salud
    val healthStatus = IMCViewModel().healthStatus(imcResult)




    // Mostrar el AlertDialog al iniciar
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            shape = RoundedCornerShape(10.dp),
            title = { Text("¡Atención!") },
            text = { Text("No te olvides de llenar TODOS los campos con los datos solicitados.") },
            confirmButton = {
                Color(0xFF6200EA)
                TextButton(onClick =  { showDialog = false }) {

                    Text("Entendido") // Cambiar el texto del botón
                    Color(0xFFF6F4F8)

                }

            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // Iconos para género
        val genderIcon = if (patientState.gender == "Hombre") R.drawable.ic_male else R.drawable.ic_female

        Icon(
            painter = painterResource(id = genderIcon),
            contentDescription = "Icono de Género",
            modifier = Modifier.size(64.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Mostrar el nombre del paciente
        Text(
            text = "Nombre del Paciente: ${patientState.name} (ID: $patientId)",
            style = MaterialTheme.typography.bodyLarge
        )

        Text(text = "Calculadora de IMC", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        // Llamar al GenderSelector pasando el estado y la función de actualización
        GenderSelector(
            selectedGender = patientState.gender,
            onGenderSelected = { gender -> sharedViewModel.updatePatient(patientState.copy(gender = gender)) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        InputField(
            value = if (patientState.age > 0) patientState.age.toString() else "",
            onValueChange = {
                if (it.isEmpty()) {
                    sharedViewModel.updatePatient(patientState.copy(age = 0))
                } else {
                    it.toIntOrNull()?.let { age ->
                        sharedViewModel.updatePatient(patientState.copy(age = age))
                    }
                }
            },
            label = "Edad (años)",
            keyboardType = KeyboardType.Number
        )

        InputField(
            value = if (patientState.weight > 0) patientState.weight.toString() else "",
            onValueChange = {
                if (it.isEmpty()) {
                    sharedViewModel.updatePatient(patientState.copy(weight = 0))
                } else {
                    it.toIntOrNull()?.let { weight ->
                        sharedViewModel.updatePatient(patientState.copy(weight = weight))
                    }
                }
            },
            label = "Peso (kg)",
            keyboardType = KeyboardType.Number
        )

        InputField(
            value = if (patientState.height > 0) patientState.height.toString() else "",
            onValueChange = {
                if (it.isEmpty()) {
                    sharedViewModel.updatePatient(patientState.copy(height = 0))
                } else {
                    it.toIntOrNull()?.let { height ->
                        sharedViewModel.updatePatient(patientState.copy(height = height))
                    }
                }
            },
            label = "Altura (cm)",
            keyboardType = KeyboardType.Number
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Mostrar IMC y estado de salud sin necesidad de un botón adicional
        Text(
            text = "Resultado del IMC: ${String.format("%.1f", imcResult)}",
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = "Estado de Salud: $healthStatus",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Nuevo botón para mostrar los datos del paciente
        Button(onClick = {
            showPatientData = true // Cambiar el estado a true cuando se haga clic
        }) {
            Text("Mostrar Datos Paciente")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Mostrar la tarjeta con los datos del paciente solo si el estado es true
        if (showPatientData) {
            Card(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        // Primera columna
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(8.dp) // Espaciado entre los textos
                        ) {
                            Text("ID: $patientId")
                            Text("Nombre: ${patientState.name}")
                            Text("Edad: ${patientState.age} años")
                            Text("Género: ${patientState.gender}")



                        }

                        Spacer(modifier = Modifier.width(16.dp)) // Espacio entre las dos columnas

                        // Segunda columna
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text("Peso: ${patientState.weight} kg")
                            Text("Altura: ${patientState.height} cm")
                            Text("IMC: ${String.format("%.1f", imcResult)}")
                            Text("Estado de Salud: $healthStatus")

                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp)) // Espacio antes del botón

                    Button(
                        onClick = {
                            val updatedPatient = patientState.copy(
                                imcResult = imcResult,
                                healthStatus = healthStatus
                            )
                            sharedViewModel.addPatient(updatedPatient) // Agregar a la lista
                            sharedViewModel.savePatientData(updatedPatient) // Guardar en DataStore
                            navController.navigate("patients") // Navegar a la lista de pacientes
                        },
                        modifier = Modifier.align(Alignment.CenterHorizontally) // Centrar el botón
                    ) {
                        Text("Guardar Datos")
                    }
                }
            }
        }

        if (snackbarVisible) {
            Snackbar(
                action = {
                    Button(onClick = { snackbarVisible = false }) {
                        Text("Cerrar")
                    }
                }

            ) {
                Text(errorMessage)
            }
        }
    }
}