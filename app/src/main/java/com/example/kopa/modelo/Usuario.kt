package com.example.kopa.modelo

class Usuario (
    var nombre : String,
    var apellidos : String,
    var edad : Int,
    var bebidas : MutableList<Bebida> = mutableListOf()
)
