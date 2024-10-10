package cl.bootcamp.myapplication9kotlin.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.AlertDialogDefaults.shape
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import cl.bootcamp.myapplication9kotlin.components.GenderSelector
import cl.bootcamp.myapplication9kotlin.components.InputField
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import cl.bootcamp.myapplication9kotlin.model.PatientState
import cl.bootcamp.myapplication9kotlin.viewmodel.IMCViewModel
import cl.bootcamp.myapplication9kotlin.viewmodel.PatientsViewModel
import cl.bootcamp.myapplication9kotlin.viewmodel.SharedViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImcInputView(navController: NavController, patientId: Int) {
    val sharedViewModel: SharedViewModel = viewModel()
    val patientState by sharedViewModel.patient.observeAsState(PatientState())

    // Estados para manejar errores y resultados
    var snackbarVisible by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf("") }

    // Ejemplo de UI
    Text("Calcular IMC para el paciente con ID: $patientId")


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
        Text(text = "Calculadora de IMC", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        // Llamar al GenderSelector pasando el estado y la función de actualización
        GenderSelector(
            selectedGender = patientState.gender,
            onGenderSelected = { gender -> sharedViewModel.updatePatient(patientState.copy(gender = gender)) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de edad
        InputField(
            value = patientState.age.toString(),
            onValueChange = { it.toIntOrNull()?.let {
                age -> sharedViewModel.updatePatient(patientState.copy(age = age)) } },
            label = "Edad (años)",
            keyboardType = KeyboardType.Number
        )

        // Campo de peso
        InputField(
            value = patientState.weight.toString(),
            onValueChange = { it.toIntOrNull()?.let {
                weight -> sharedViewModel.updatePatient(patientState.copy(weight = weight)) } },
            label = "Peso (kg)",
            keyboardType = KeyboardType.Number
        )

        // Campo de altura
        InputField(
            value = patientState.height.toString(),
            onValueChange = { it.toIntOrNull()?.let {
                height -> sharedViewModel.updatePatient(patientState.copy(height = height)) } },
            label = "Altura (cm)",
            keyboardType = KeyboardType.Number
        )


        Spacer(modifier = Modifier.height(16.dp))

        // Botón para calcular IMC
        Button(onClick = {
            // Validar campos antes de navegar
            if (patientState.age > 0 && patientState.weight > 0 && patientState.height > 0) {
                sharedViewModel.updatePatientDetails(patientState.age, patientState.height, patientState.weight)
                navController.navigate("data")
            } else {
                snackbarVisible = true // Mostrar snackbar si hay errores
                errorMessage = "Por favor, completa todos los campos."
            }
        }) {
            Text("Calcular IMC")
        }

        Spacer(modifier = Modifier.height(24.dp))



        // Obtener el resultado del IMC, usando un valor predeterminado si es nulo
        val imcResult = IMCViewModel().state.value.imcResult ?: 0f
        val healthStatus = IMCViewModel().healthStatus(imcResult)

        Text(
            text = "Resultado del IMC: ${String.format("%.1f", imcResult)}", // Mostrar con un decimal
            style = MaterialTheme.typography.bodyLarge
        )

        Text(
            text = "Estado de Salud: $healthStatus", // Mostrar estado de salud
            style = MaterialTheme.typography.bodyLarge
        )

        Button(onClick = { /*TODO*/ }) {
            Text("Mostrar Datos Paciente")
        }


        // Mostrar Snackbar si hay un mensaje de error
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