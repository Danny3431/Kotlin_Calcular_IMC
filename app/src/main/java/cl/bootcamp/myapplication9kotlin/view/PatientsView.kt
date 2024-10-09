package cl.bootcamp.myapplication9kotlin.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import cl.bootcamp.myapplication9kotlin.model.PatientState
import cl.bootcamp.myapplication9kotlin.viewmodel.PatientsViewModel
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatientsView(navController: NavController, modifier: Modifier = Modifier) {
    // Usar el ViewModel para obtener la lista de pacientes
    val viewModel: PatientsViewModel = viewModel()
    var showModal by remember { mutableStateOf(false) }
    var id by remember { mutableStateOf(0) }
    var newPatientName by remember { mutableStateOf("") }
    var newPatientAge by remember { mutableStateOf("") } // Cambia a String
    var newPatientHeight by remember { mutableStateOf("") } // Cambia a String
    var newPatientWeight by remember { mutableStateOf("") } // Cambia a String
    var newPatientGender by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Lista de Pacientes") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showModal = true }) {
                Text("+")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyColumn {
                items(viewModel.patients) { patient ->
                    PatientListCard(patient = patient, navController = navController ) {

                        navController.navigate("home/${patient.id}") // Navega hacia donde se piden datos para el IMC
                    }
                }
            }

            // Modal para agregar un nuevo paciente
            if (showModal) {
                AlertDialog(
                    onDismissRequest = { showModal = false },
                    title = { Text("Agregar Paciente") },
                    text = {
                        Column {
                            TextField(
                                value = newPatientName,
                                onValueChange = {
                                    newPatientName = it
                                    errorMessage = "" // Limpiar error al escribir
                                },
                                label = { Text("Nombre del Paciente") },
                                isError = errorMessage.isNotEmpty(),
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    keyboardType = KeyboardType.Text // Cambiar a teclado de texto
                                )
                            )
                            if (errorMessage.isNotEmpty()) {
                                Text(errorMessage, color = MaterialTheme.colorScheme.error)
                            }
                        }
                    },
                    confirmButton = {
                        TextButton(onClick = {
                            if (newPatientName.isBlank()) {
                                errorMessage = "El nombre no puede estar en blanco."
                            } else if (viewModel.patients.any { it.name == newPatientName }) {
                                errorMessage = "Ya existe un paciente con este nombre."
                            } else {
                                viewModel.addPatient(newPatientName, 0, 0, 0, "", 0f, "")
                                newPatientName = ""
                                showModal = false
                            }
                        }) {
                            Text("Agregar")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showModal = false }) {
                            Text("Cancelar")

                        }
                    }
                )
            }
        }
    }
}




@Composable
fun PatientListCard(patient: PatientState, navController: NavController, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth() // Usar el ancho completo de la pantalla
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = patient.id.toString(), style = MaterialTheme.typography.bodyMedium)
            Text(text =  patient.name, style = MaterialTheme.typography.bodyLarge)
            Button(onClick = {
                navController.navigate("home/${patient.id}") // Navega a HomeView con el ID del paciente
            }) {
                Text("Calcular IMC")
            }
        }
    }

}