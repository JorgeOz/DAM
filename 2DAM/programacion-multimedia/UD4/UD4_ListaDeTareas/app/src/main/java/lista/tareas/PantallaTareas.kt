package lista.tareas

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import lista.tareas.modelo.Tarea
import lista.tareas.ui.TareasViewModel

private val ColorUrgente = Color(0xFFFFCDD2)
private val ColorNormal = Color(0xFFE8F5E9)

@Composable
fun PantallaTareas(viewModel: TareasViewModel = viewModel()) {

    val uiState by viewModel.uiState.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {

        Text(
            text = "Lista de Tareas",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = viewModel.titulo,
            onValueChange = { viewModel.actualizarTitulo(it) },
            label = { Text("Título") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = viewModel.descripcion,
            onValueChange = { viewModel.actualizarDescripcion(it) },
            label = { Text("Descripción") },
            modifier = Modifier.fillMaxWidth()
        )

        Row {
            viewModel.modulos.forEach { m ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = viewModel.modulo == m,
                        onClick = { viewModel.actualizarModulo(m) }
                    )
                    Text(m)
                }
            }
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = viewModel.urgente,
                onCheckedChange = { viewModel.actualizarUrgente(it) }
            )
            Text("Urgente")
        }

        Button(onClick = { viewModel.anadirTarea() }) {
            Text("Añadir tarea")
        }

        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

        LazyColumn {
            items(uiState.tareas) { tarea ->
                TareaItem(tarea)
            }
        }
    }
}

@Composable
fun TareaItem(tarea: Tarea) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (tarea.urgente) ColorUrgente else ColorNormal
        )
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(tarea.titulo, fontWeight = FontWeight.Bold)
            Text(tarea.descripcion)
            Text("Módulo: ${tarea.modulo}")
            if (tarea.urgente) {
                Text("¡URGENTE!", color = Color.Red, fontWeight = FontWeight.Bold)
            }
        }
    }
}