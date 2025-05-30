package com.example.kopa.bbdd

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

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
@Entity(tableName = "bebidas")
data class Bebida (
    @PrimaryKey(autoGenerate = true) var id : Int = 0,
    @NonNull @ColumnInfo (name = "nombre") val nombre : String = "",
    @NonNull @ColumnInfo (name = "cantidad") var cantidad : Double = 0.0,
    @NonNull @ColumnInfo (name = "vaso") var vaso : Int = 0,
    @NonNull @ColumnInfo (name = "comida") var comida : Int = 0,
    @NonNull @ColumnInfo (name = "casa") var casa : Int = 0,
    @NonNull @ColumnInfo (name = "aviso") var aviso : Int = 0,
    @NonNull @ColumnInfo (name = "muerte") var muerte : Int = 0,
    @NonNull @ColumnInfo (name = "color") var color : String = "",
    @NonNull @ColumnInfo (name = "consumido") var consumido : Double = 0.0
){}