package cl.bootcamp.myapplication9kotlin.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cl.bootcamp.myapplication9kotlin.viewmodel.IMCViewModel
import androidx.lifecycle.viewmodel.compose.viewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImcView(navController: NavController, modifier: Modifier = Modifier, viewModel: IMCViewModel = viewModel()) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Resultado del IMC", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        // Mostrar el resultado del IMC
        Text(
            text = "IMC: ${String.format("%.1f", viewModel.state.value.imcResult)}",
            style = MaterialTheme.typography.headlineSmall
        )

        // Mostrar mensajes de error si los hay
        if (viewModel.errorMessage.value.isNotEmpty()) {
            Text(text = viewModel.errorMessage.value, color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para regresar a HomeView
        Button(onClick = { navController.navigate("Patients") }) {
            Text("Regresar")
        }
    }
}