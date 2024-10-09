package cl.bootcamp.myapplication9kotlin.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType


@Composable
fun InputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    keyboardType: KeyboardType = KeyboardType.Number // Por defecto, teclado numérico
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { androidx.compose.material3.Text(label) },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = keyboardType // Establecer tipo de teclado
        )
    )
}
@Composable
fun PatientInputFields(
    name: String,
    onNameChange: (String) -> Unit,
    age: String,
    onAgeChange: (String) -> Unit,
    height: String,
    onHeightChange: (String) -> Unit,
    weight: String,
    onWeightChange: (String) -> Unit,
    gender: String,
    onGenderChange: (String) -> Unit
) {
    InputField(value = name, onValueChange = onNameChange, label = "Nombre del Paciente")
    InputField(value = age, onValueChange = onAgeChange, label = "Edad", keyboardType = KeyboardType.Number)
    InputField(value = weight, onValueChange = onWeightChange, label = "Peso (kg)", keyboardType = KeyboardType.Number)
    InputField(value = height, onValueChange = onHeightChange, label = "Altura (cm)", keyboardType = KeyboardType.Number)
    InputField(value = gender, onValueChange = onGenderChange, label = "Género")
}