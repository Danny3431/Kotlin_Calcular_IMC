package cl.bootcamp.myapplication9kotlin.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.text.input.KeyboardType
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
import cl.bootcamp.myapplication9kotlin.viewmodel.SharedViewModel



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatientsView(navController: NavController, sharedViewModel: SharedViewModel, modifier: Modifier = Modifier ) {

    // Usar el ViewModel para obtener la lista de pacientes
    val sharedViewModel: SharedViewModel = viewModel()
    val patientsList by sharedViewModel.patientsList.observeAsState(emptyList())




    var showModal by remember { mutableStateOf(false) }
    var newPatientName by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    var state by remember { mutableStateOf(PatientState()) }


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
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .weight(1f)
            ) {
                items(patientsList) { patient ->
                    PatientListCard(  patient = patient,  navController = navController,sharedViewModel = sharedViewModel) {
                        navController.navigate("imcInput/{patientId}") // Navega a HomeView con el ID del paciente
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
                            } else if (patientsList.any { it.name == newPatientName }) {
                                errorMessage = "Ya existe un paciente con este nombre."
                            } else {
                                // Agregar nuevo paciente (ajusta los parámetros según tu modelo)
                                sharedViewModel.addPatientToList( PatientState(name = newPatientName ))
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
fun PatientListCard(patient: PatientState, navController: NavController, sharedViewModel: SharedViewModel, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth() // Usar el ancho completo de la pantalla
            .clickable { navController.navigate("imcInput/${patient.id}") }
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "ID: ${patient.id}", style = MaterialTheme.typography.titleMedium)
            Text(text = " ${patient.name}", style = MaterialTheme.typography.titleLarge)
            Button(onClick = {
                navController.navigate("imcInput/{patientId") // Navega a HomeView con el ID del paciente
            }) {
                Text("Calcular IMC")
            }
        }
    }
}