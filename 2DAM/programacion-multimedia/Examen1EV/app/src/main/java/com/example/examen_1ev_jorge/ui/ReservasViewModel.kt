package com.example.examen_1ev_jorge.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.examen_1ev_jorge.modelo.Reserva
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ReservasViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ReservasUIState())
    val uiState: StateFlow<ReservasUIState> = _uiState.asStateFlow()

    val tipoHabitacion = listOf("Hotel", "Apartamento")

    var nombreCliente by mutableStateOf("")
        private set

    var habitacion by mutableStateOf("Hotel")
        private set

    var esApartamento by mutableStateOf(false)
        private set

    fun actualizarNombreCliente(nuevoNombre: String) {
        nombreCliente = nuevoNombre
    }

    fun actualizarHabitacion(nuevaHabitacion: String) {
        habitacion = nuevaHabitacion
    }

    fun actualizarEsApartamento(esUrgente: Boolean) {
        esApartamento = esApartamento
    }

    fun anadirReserva() {

        if (nombreCliente.isBlank()) return

        val nuevaReserva = Reserva(nombreCliente, habitacion, esApartamento)

        _uiState.update { estadoActual ->
            estadoActual.copy(reservas = estadoActual.reservas + nuevaReserva)
        }

        limpiarFormulario()
    }

    private fun limpiarFormulario() {
        actualizarNombreCliente("")
        actualizarHabitacion("")
        actualizarEsApartamento(false)
    }
}