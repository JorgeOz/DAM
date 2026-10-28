package com.example.restaurantedam.modelo

class Pizza(
    nombre: String,
    imagen: String,
    val tipo: String,
    val tamaño: String
) : Producto(nombre, imagen) {

}
