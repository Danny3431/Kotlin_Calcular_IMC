package cl.bootcamp.myapplication9kotlin.view

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
fun PatientsView(navController: NavController, sharedViewModel: SharedViewModel, modifier: Modifier = Modifier) {

    val sharedViewModel: SharedViewModel = viewModel()
    val patientsList by sharedViewModel.patientsList.observeAsState(emptyList())

    // Cargar el estado del paciente actual
    val patientState by sharedViewModel.patient.observeAsState(PatientState())
    val patientId = patientState.id

    // Al cambiar de paciente, restablecer los datos previos
    LaunchedEffect(patientId) {
        sharedViewModel.clearPatientData()
        sharedViewModel.loadPatientById(patientId) // Cargar los datos del paciente seleccionado
    }

    var showModal by remember { mutableStateOf(false) }
    var newPatientName by remember { mutableStateOf("") }

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
            // Mostrar el listado de pacientes con todos sus datos
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .weight(1f)
            ) {
                items(patientsList) { patient ->
                    // Aquí mostramos la nueva tarjeta con los detalles completos del paciente
                    PatientDetailsCard(patient = patient)
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
                            // Validar los campos
                            if (newPatientName.isBlank()) {
                                errorMessage = "El nombre no puede estar en blanco."
                            } else if (patientsList.any { it.name == newPatientName }) {
                                errorMessage = "Ya existe un paciente con este nombre."
                            } else {
                                // Crear nuevo paciente con ID automático
                                val newId = patientsList.size + 1 // Generar ID automático

                                sharedViewModel.addPatient(
                                    PatientState(
                                        id = sharedViewModel.nextId, // Utiliza el ID generado automáticamente
                                        name = newPatientName,
                                        age = 0,
                                        height = 0,
                                        weight = 0,
                                        gender = "",
                                        imcResult = 0f
                                    )
                                )
                                sharedViewModel.incrementId() // Incrementar el ID después de agregar el paciente

                                // Limpiar los campos
                                newPatientName = ""
                                showModal = false
                                sharedViewModel.clearPatientData() // Limpiar el estado del paciente
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
fun PatientListCard(
    patient: PatientState,
    navController: NavController,
    sharedViewModel: SharedViewModel,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("ID: ${patient.id}")
            Text("Nombre: ${patient.name}")
            Button(onClick = {
                navController.navigate("imcInput/${patient.id}")
            }) {
                Text("Completar Datos")
            }
        }
    }
}

@Composable
fun PatientDetailsCard(
    patient: PatientState,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("ID: ${patient.id}")
            Text("Nombre: ${patient.name}")
            Text("Edad: ${patient.age}")
            Text("Altura: ${patient.height}")
            Text("Peso: ${patient.weight}")
            Text("Género: ${patient.gender}")
            Text("IMC: ${patient.imcResult}")
        }
    }
}