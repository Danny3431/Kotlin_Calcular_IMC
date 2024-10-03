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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Calculadora de IMC", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

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
            onValueChange = { viewModel.updateHeight(it) },
            label = "Altura (cm)",
            keyboardType = KeyboardType.Number
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { viewModel.calculateIMC() }) {
            Text("Calcular IMC")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Mostrar el resultado del IMC
        Text(
            text = "IMC: ${String.format("%.1f", viewModel.state.value.imcResult)}",
            style = TextStyle(
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
        )
    }
}