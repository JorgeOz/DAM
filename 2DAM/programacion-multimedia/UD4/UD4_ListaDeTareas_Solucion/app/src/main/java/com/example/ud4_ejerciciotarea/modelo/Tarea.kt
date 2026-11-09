package com.example.ud4_ejerciciotarea.modelo

data class Tarea(
    var titulo: String = "",
    var descripcion: String = "",
    var modulo: String = "",
    var urgente: Boolean = false
)
