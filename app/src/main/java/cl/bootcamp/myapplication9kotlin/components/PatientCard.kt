package cl.bootcamp.myapplication9kotlin.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import cl.bootcamp.myapplication9kotlin.R
import cl.bootcamp.myapplication9kotlin.model.PatientState



@Composable
fun PatientCard(PatientState: PatientState, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Nombre: ${PatientState.name}")
            Text(text = "Edad: ${PatientState.age} años")

            // Mostrar el icono de género
            val genderIcon = painterResource(id = if (PatientState.gender == "Hombre") R.drawable.ic_male else R.drawable.ic_female)
            val iconColor = if (PatientState.gender == "Hombre") Color.Blue else Color.Red
            Image(painter = genderIcon, contentDescription = "Género", colorFilter = ColorFilter.tint(iconColor))
            Spacer(modifier = Modifier.height(8.dp))

            // Cambiar el color del ícono según el género
            /*fun colorResource(id: Int): Color {
                if (PatientState.gender == "Hombre") {Color.Blue}
            }else
                    (PatientState.gender == "Mujer") { Color.Red
            }*/


            Image(painter = genderIcon, contentDescription = "Género", modifier = Modifier.size(24.dp))

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "IMC: ${String.format("%.1f", PatientState.imcResult)}")
        }
    }
}