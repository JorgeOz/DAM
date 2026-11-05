package lista.tareas.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import lista.tareas.modelo.Tarea
import lista.tareas.modelo.TareasUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TareasViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(TareasUIState())
    val uiState: StateFlow<TareasUIState> = _uiState.asStateFlow()

    val modulos = listOf("AD", "DI", "PMDM")

    var titulo by mutableStateOf("")
        private set

    var descripcion by mutableStateOf("")
        private set

    var modulo by mutableStateOf("AD")
        private set

    var urgente by mutableStateOf(false)
        private set

    fun actualizarTitulo(nuevoTitulo: String) {
        titulo = nuevoTitulo
    }

    fun actualizarDescripcion(nuevaDescripcion: String) {
        descripcion = nuevaDescripcion
    }

    fun actualizarModulo(nuevoModulo: String) {
        modulo = nuevoModulo
    }

    fun actualizarUrgente(esUrgente: Boolean) {
        urgente = esUrgente
    }

    fun anadirTarea() {

        if (titulo.isBlank()) return

        val nuevaTarea = Tarea(titulo, descripcion, modulo, urgente)

        _uiState.update { estadoActual ->
            estadoActual.copy(tareas = estadoActual.tareas + nuevaTarea)
        }

        limpiarFormulario()
    }

    private fun limpiarFormulario() {
        actualizarTitulo("")
        actualizarDescripcion("")
        actualizarModulo("AD")
        actualizarUrgente(false)
    }
}