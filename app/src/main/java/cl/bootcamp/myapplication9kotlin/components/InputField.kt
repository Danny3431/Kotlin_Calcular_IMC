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
age: Int,
onAgeChange: (Int) -> Unit,
height: Int,
onHeightChange: (Int) -> Unit,
weight: Int,
onWeightChange: (Int) -> Unit,
gender: String,
onGenderChange: (String) -> Unit
) {

    GenderSelector(selectedGender = gender, onGenderSelected = onGenderChange)

    InputField(value = name, onValueChange = onNameChange, label = "Nombre del Paciente")
    // Campo para la edad
    InputField(
        value = age.toString(),
        onValueChange = {
            it.toIntOrNull()?.let { ageValue ->
                onAgeChange(ageValue)
            }
        },
        label = "Edad",
        keyboardType = KeyboardType.Number
    )

    // Campo para el peso
    InputField(
        value = weight.toString(),
        onValueChange = {
            it.toIntOrNull()?.let { weightValue ->
                onWeightChange(weightValue)
            }
        },
        label = "Peso (kg)",
        keyboardType = KeyboardType.Number
    )

    // Campo para la altura
    InputField(
        value = height.toString(),
        onValueChange = {
            if (it.length <= 3) { // Limitar a 3 dígitos
                it.toIntOrNull()?.let { heightValue ->
                    onHeightChange(heightValue)
                }
            }
        },
        label = "Altura (cm)",
        keyboardType = KeyboardType.Number
    )

    // Campo para el género
    InputField(value = gender, onValueChange = onGenderChange, label = "Género")
}

