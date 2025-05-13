package com.example.kopa.modelo
/*
* Nombre = nombre del tipo de bebida (ej. cerveza, vino, kalimotxo etc.)
* Cantidad = cantidad en l de bebida (ej. vaso normal = 250ml, pinta = 0,5ml etc.)
* Vaso = el tope de consumo hasta tomarte un vaso de agua.
* Comida = el tope de consumo hasta pedir algo de comer.
* Casa = el tope de consumo hasta ir a casa a dormir.
* Aviso = el tope de consumo para que salte una advertencia de sobredosis.
* Muerte = el tope de consumo para que la app deje de funcionar.
*
* consumo = se mide en vasos, botellínes, copas etc.
* */
class Bebida (
    var nombre : String,
    var cantidad : Double,
    var vaso : Int,
    var comida : Int,
    var casa : Int,
    var aviso : Int,
    var muerte : Int,
)