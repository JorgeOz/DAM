package com.example.examen_1ev_jorge

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
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
import com.example.examen_1ev_jorge.modelo.Reserva
import com.example.examen_1ev_jorge.ui.ReservasViewModel
import lista.tareas.modelo.Tarea
import lista.tareas.ui.TareasViewModel

private val ColorEsApartamento = Color(0xFFFFCDD2)
private val ColorEsHotel = Color(0xFFE8F5E9)

@Composable
fun PantallaReservas(viewModel: ReservasViewModel = viewModel()) {

    val uiState by viewModel.uiState.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {

        Text(
            text = "Reservas DAM",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = "Gestión de reservas de alojamientos",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

        OutlinedTextField(
            value = viewModel.nombreCliente,
            onValueChange = { viewModel.actualizarNombreCliente(it) },
            label = { Text("Nombre del cliente") },
            modifier = Modifier.fillMaxWidth()
        )

        Row {
            viewModel.tipoHabitacion.forEach { h ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = viewModel.habitacion == h,
                        onClick = { viewModel.actualizarHabitacion(h) }
                    )
                    Text(h)
                }
            }
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = viewModel.esApartamento,
                onCheckedChange = { viewModel.actualizarEsApartamento(it) }
            )
            Text("Apartamento")
        }

        Button(onClick = { viewModel.anadirReserva() }) {
            Text("Reservar")
        }

        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

        LazyColumn {
            items(uiState.reservas) { reserva ->
                ReservaItem(reserva)
            }
        }
    }
}

@Composable
fun ReservaItem(reserva: Reserva) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (reserva.esApartamento) ColorEsApartamento else ColorEsHotel
        )
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(reserva.nombreCliente, fontWeight = FontWeight.Bold)
            Text(reserva.habitacion)
            Text("Módulo: ${reserva.habitacion}")
            if (reserva.esApartamento) {
                Text("Apartamento", color = Color.Red, fontWeight = FontWeight.Bold)
            }
        }
    }
}