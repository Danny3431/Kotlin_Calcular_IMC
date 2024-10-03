package cl.bootcamp.myapplication9kotlin.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cl.bootcamp.myapplication9kotlin.components.GenderSelector
import cl.bootcamp.myapplication9kotlin.components.InputField
import cl.bootcamp.myapplication9kotlin.viewmodel.IMCViewModel
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun HomeView(modifier: Modifier = Modifier) {
    val viewModel: IMCViewModel = viewModel() // Instanciar el ViewModel
    var snackbarVisible by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(true) } // Controlar la visibilidad del AlertDialog

    // Mostrar el AlertDialog al iniciar
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("¡CUIDADO!") },
            text = { Text("No te olvides de llenar todos los campos con los datos solicitados.") },
            confirmButton = {
                TextButton(onClick =  { showDialog = false }) {
                    Text("Entendido")
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

        // Mostrar el selector de género
        GenderSelector()

        Spacer(modifier = Modifier.height(16.dp))

        InputField(
            value = viewModel.state.value.age,
            onValueChange = { viewModel.updateAge(it) },
            label = "Edad (años)",
            keyboardType = KeyboardType.Number
        )

        InputField(
            value = viewModel.state.value.weight,
            onValueChange = { viewModel.updateWeight(it) },
            label = "Peso (kg)",
            keyboardType = KeyboardType.Number
        )

        InputField(
            value = viewModel.state.value.height,
            onValueChange = {
                if (it.length <= 3) { // Limitar a 3 dígitos
                    viewModel.updateHeight(it)
                }
            },
            label = "Altura (cm)",
            keyboardType = KeyboardType.Number
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            viewModel.validateFields() // Validar campos
            if (viewModel.errorMessage.value.isEmpty()) {
                viewModel.calculateIMC() // Calcular IMC si no hay errores
            } else {
                snackbarVisible = true // Mostrar snackbar si hay errores
            }
        }) {
            Text("Calcular IMC")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Mostrar el resultado del IMC
        Text(
            text = "IMC: ${String.format("%.1f", viewModel.state.value.imcResult)}",
            style = TextStyle(fontSize = 40.sp, fontWeight = FontWeight.Bold)
        )

        // Mostrar Snackbar si hay un mensaje de error
        if (snackbarVisible) {
            Snackbar(
                action = {
                    Button(onClick = { snackbarVisible = false }) {
                        Text("Cerrar")
                    }
                }
            ) {
                Text(viewModel.errorMessage.value)
            }
        }
    }
}