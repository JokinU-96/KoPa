package com.example.kopa.modelo

import androidx.lifecycle.ViewModel

class VM: ViewModel() {
    var bebidas : MutableList<Bebida> = mutableListOf()
    var usuario : Usuario? = null

    fun insertarBebidas(){
        bebidas.add(Bebida("Cerveza", 0.33, 4, 6, 8, 12, 18))
        bebidas.add(Bebida("Vino", 0.15, 4, 6, 8, 12, 18))
        bebidas.add(Bebida("Cerveza", 0.50, 3, 5, 7, 10, 15))
        bebidas.add(Bebida("Pacharán", 0.10, 5, 7, 9, 14, 20))
        bebidas.add(Bebida("GinTonic", 0.50, 2, 3, 4, 6, 9))
        bebidas.add(Bebida("RonCola", 0.50, 2, 3, 4, 6, 9))
        bebidas.add(Bebida("Tequila", 0.05, 6, 8, 10, 15, 22))
        bebidas.add(Bebida("Vermouth", 0.10, 4, 6, 8, 12, 18))
    }
}