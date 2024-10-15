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
import cl.bootcamp.myapplication9kotlin.components.GenderSelector



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatientsView(navController: NavController, sharedViewModel: SharedViewModel, modifier: Modifier = Modifier) {

    val sharedViewModel: SharedViewModel = viewModel()
    val patientsList by sharedViewModel.patientsList.observeAsState(emptyList())

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
            // Mostrar el listado de pacientes
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .weight(1f)
            ) {
                items(patientsList) { patient ->
                    PatientListCard(
                        patient = patient,
                        navController = navController,
                        sharedViewModel = sharedViewModel,
                                onClick = {
                              navController.navigate(
                                "imcInput/${patient.id}"
                            ) // Navegar con el ID
                        }
                    )
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
                                        id = newId, // Asignar ID automáticamente
                                        name = newPatientName,
                                        age = 0, // Asignar 0 o manejar más adelante
                                        height = 0, // Asignar 0 o manejar más adelante
                                        weight = 0, // Asignar 0 o manejar más adelante
                                        gender = "", //  Manejar más adelante
                                        imcResult = 0f // Asignar 0 o manejar más adelante

                                    )
                                )

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
                    .clickable { onClick() } // Acción al hacer clic en el Card
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
                            Text("ID: ${patient.id}")
                            Text("Nombre: ${patient.name}")
                           /* Text("Edad: ${patient.age} años")
                            Text("Género: ${patient.gender}")

                            Spacer(modifier = Modifier.width(8.dp)) // Espacio entre las dos columnas

                            // Segunda columna
                            Column(
                                modifier = Modifier.weight(1f),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text("Peso: ${patient.weight} kg")
                                Text("Altura: ${patient.height} cm")
                                Text("IMC: ${String.format("%.1f", patient.imcResult)}")
                                Text("Estado de Salud: ${patient.healthStatus}")


                            }*/
                        }

                    }
                }
                Button(onClick = {
                    navController.navigate("imcInput/${patient.id}") // Navega a HomeView con el ID del paciente
                }) {
                    Text("Calcular IMC")
                }

            }
        }
