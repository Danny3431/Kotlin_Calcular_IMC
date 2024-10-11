package cl.bootcamp.myapplication9kotlin.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import cl.bootcamp.myapplication9kotlin.R
import cl.bootcamp.myapplication9kotlin.model.PatientState
import cl.bootcamp.myapplication9kotlin.viewmodel.IMCViewModel
import cl.bootcamp.myapplication9kotlin.viewmodel.PatientsViewModel
import cl.bootcamp.myapplication9kotlin.viewmodel.SharedViewModel

@Composable
fun PatientsDataView(navController: NavController, modifier: Modifier = Modifier) {
    // Aquí va la lógica de tu vista
    Text(text = "Detalles del paciente")
    var state by remember { mutableStateOf(PatientState()) }

    // Puedes acceder a los datos del paciente desde el ViewModel compartido
    // Utilizar el ViewModel compartido
    var viewModel: PatientsViewModel = viewModel()
    var imcViewModel: IMCViewModel = viewModel()

    val sharedViewModel: SharedViewModel = viewModel()
    val patient = sharedViewModel.patient.value

    // Estado para el nombre del nuevo paciente
    var newPatientName by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Detalles del Paciente", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        // Mostrar los detalles del paciente
        patient?.let {
            Text("Nombre: ${it.name}")

            // Mostrar el icono según el género
            val genderIcon = if (it.gender == "Hombre") {
                painterResource(id = R.drawable.baseline_male_24) // Reemplaza con tu recurso
            } else {
                painterResource(id = R.drawable.baseline_female_24) // Reemplaza con tu recurso
            }
            Image(painter = genderIcon, contentDescription = "Género", modifier = Modifier.size(24.dp))
            Text("Género: ${it.gender}")
            Text("Edad: ${it.age}")
            Text("Altura: ${it.height} cm")
            Text("Peso: ${it.weight} kg")
            Text("IMC: ${String.format("%.1f", it.imcResult ?: 0f)}")
            Text("Estado de Salud: ${it.healthStatus}")
        } ?: Text("No hay datos disponibles")

        Spacer(modifier = Modifier.height(24.dp))



        if (errorMessage.isNotEmpty()) {
            Text(errorMessage, color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(16.dp))


        Button(onClick = {
            sharedViewModel.addPatientToList(PatientState()) // Agregar paciente a la lista
            navController.navigate("patients") // Navegar a la pantalla de lista de pacientes
        }) {
            Text("Guardar Paciente")
        }
    }
}