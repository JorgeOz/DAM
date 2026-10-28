package com.example.restaurantedam.modelo

class Hamburguesa(
    nombre: String,
    imagen: String,
    val queso: Boolean,
    val carne: String
) : Producto(nombre, imagen) {

}
