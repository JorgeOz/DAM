package lista.tareas.modelo

data class Tarea(
    val titulo: String,
    val descripcion: String,
    val modulo: String,
    val urgente: Boolean
)