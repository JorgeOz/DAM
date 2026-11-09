package com.example.ud4_ejerciciotarea.modelo

data class TareaUIState(
    var tarea: Tarea = Tarea(),
    var listaTareas: List<Tarea> = listOf()
)
