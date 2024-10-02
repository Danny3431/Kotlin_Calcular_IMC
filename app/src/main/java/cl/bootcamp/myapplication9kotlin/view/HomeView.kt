package cl.bootcamp.myapplication9kotlin.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cl.bootcamp.myapplication9kotlin.components.GenderSelector
import cl.bootcamp.myapplication9kotlin.components.InputField

@Composable
fun HomeView(modifier: Modifier = Modifier) {
    var weight by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var imcResult by remember { mutableFloatStateOf(0f) }


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

        InputField(value = age, onValueChange = { age = it }, label = "Edad (años)", keyboardType = KeyboardType.Number)
        InputField(value = weight, onValueChange = { weight = it }, label = "Peso (kg)", keyboardType = KeyboardType.Number)
        InputField(value = height, onValueChange = { height = it }, label = "Altura (Cm)", keyboardType = KeyboardType.Number)

        Spacer(modifier = Modifier.height(16.dp))

        // Botón de Calcular IMC (sin funcionalidad aún)
        Button(onClick = { /* Aún no se implementa la acción */ }) {
            Text("Calcular IMC")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Lugar para mostrar el resultado del IMC (por ahora vacío)
        Text(text = "IMC: ", style = MaterialTheme.typography.bodyLarge)
    }
}