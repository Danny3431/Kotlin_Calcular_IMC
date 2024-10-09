package cl.bootcamp.myapplication9kotlin.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.material3.MultiChoiceSegmentedButtonRow
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun GenderSelector(
    selectedGender: String, // Valor del género seleccionado (viene de la vista padre)
    onGenderSelected: (String) -> Unit // Callback para notificar cambios
) {
    MultiChoiceSegmentedButtonRow(
        modifier = Modifier.fillMaxWidth(),
    ) {
        // Botón para Hombre
        SegmentedButton(
            checked = selectedGender == "Hombre",
            onCheckedChange = { if (it) onGenderSelected("Hombre") }, // Notificar cambio de selección
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.weight(1f),
            colors = SegmentedButtonDefaults.colors(
                activeContainerColor = MaterialTheme.colorScheme.primary,
                activeContentColor = Color.White,
                inactiveContainerColor = Color.Transparent,
                inactiveContentColor = Color.Black
            ),
            border = BorderStroke(2.dp,
                if (selectedGender == "Hombre") Color.White else Color.Gray)
        ) {
            Text("Hombre")
        }

        // Botón para Mujer
        SegmentedButton(
            checked = selectedGender == "Mujer",
            onCheckedChange = { if (it) onGenderSelected("Mujer") }, // Notificar cambio de selección
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.weight(1f),
            colors = SegmentedButtonDefaults.colors(
                activeContainerColor = MaterialTheme.colorScheme.primary,
                activeContentColor = Color.White,
                inactiveContainerColor = Color.Transparent,
                inactiveContentColor = Color.Black
            ),
            border = BorderStroke(2.dp,
                if (selectedGender == "Mujer") Color.White else Color.Gray)
        ) {
            Text("Mujer")
        }
    }
}