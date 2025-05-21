package com.example.kopa.modelo

import androidx.lifecycle.ViewModel

class VM: ViewModel() {
    var bebidas : MutableList<Bebida> = mutableListOf()
    var colores : MutableList<Color> = mutableListOf()
    var usuario : Usuario? = null
    var progreso : MutableList<Bebida> = mutableListOf()

    fun insertarBebidas(){
        bebidas.add(Bebida("Cerveza", 0.33, 4, 6, 8, 12, 18, "#FFDA63", 0))
        bebidas.add(Bebida("Vino", 0.15, 4, 6, 8, 12, 18, "#8B0000", 0))
        bebidas.add(Bebida("Cerveza", 0.50, 3, 5, 7, 10, 15, "#F0E68C", 0))
        bebidas.add(Bebida("Pacharán", 0.10, 5, 7, 9, 14, 20, "#DC143C", 0))
        bebidas.add(Bebida("GinTonic", 0.50, 2, 3, 4, 6, 9, "#E0FFFF", 0))
        bebidas.add(Bebida("RonCola", 0.50, 2, 3, 4, 6, 9, "#8B4513", 0))
        bebidas.add(Bebida("Tequila", 0.05, 6, 8, 10, 15, 22, "#F4A460", 0))
        bebidas.add(Bebida("Vermouth", 0.10, 4, 6, 8, 12, 18, "#A0522D", 0))
    }


    //La idea es habilitar la opción de añadir bebidas. De modo que el usuario debería
    //elegir el color de la bebida desde una paleta sin tener que escribir el código.
    fun insertarColores(){
        colores.add(Color("#E0FFFF"))
        colores.add(Color("#FFA07A"))
        colores.add(Color("#98FB98"))
        colores.add(Color("#F08080"))
        colores.add(Color("#ADD8E6"))
        colores.add(Color("#DDA0DD"))
        colores.add(Color("#FAF0E6"))
        colores.add(Color("#87CEEB"))
        colores.add(Color("#90EE90"))
        colores.add(Color("#FFDAB9"))
    }
}