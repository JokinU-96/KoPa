package com.example.kopa.bbdd

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = arrayOf(Bebida::class), version = 1, exportSchema = false)
abstract class bbdd: RoomDatabase() {
    abstract  fun miDAO(): BebidaDAO

    companion object {
        @Volatile
        private var INSTANCE: bbdd? = null
        fun getDatabase(context: Context): bbdd {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    bbdd::class.java,
                    "palabra_dataBase"
                ).build()
                INSTANCE = instance
                instance
            }
        }
        // Callback que se ejecuta cuando se crea la base de datos
        private val roomCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                // Insertar los datos iniciales
                val bebidasIniciales = listOf(
                    Bebida(nombre = "Cerveza", cantidad = 0.33, vaso = 4, comida = 6, casa = 8, aviso = 12, muerte = 18, color = "#FFDA63", consumido = 0),
                    Bebida(nombre = "Vino", cantidad = 0.15, vaso = 4, comida = 6, casa = 8, aviso = 12, muerte = 18, color = "#8B0000", consumido = 0),
                    Bebida(nombre = "Cerveza", cantidad = 0.50, vaso = 3, comida = 5, casa = 7, aviso = 10, muerte = 15, color = "#F0E68C", consumido = 0),
                    Bebida(nombre = "Pacharán", cantidad = 0.10, vaso = 5, comida = 7, casa = 9, aviso = 14, muerte = 20, color = "#DC143C", consumido = 0),
                    Bebida(nombre = "GinTonic", cantidad = 0.50, vaso = 2, comida = 3, casa = 4, aviso = 6, muerte = 9, color = "#E0FFFF", consumido = 0),
                    Bebida(nombre = "RonCola", cantidad = 0.50, vaso = 2, comida = 3, casa = 4, aviso = 6, muerte = 9, color = "#8B4513", consumido = 0),
                    Bebida(nombre = "Tequila", cantidad = 0.05, vaso = 6, comida = 8, casa = 10, aviso = 15, muerte = 22, color = "#F4A460", consumido = 0),
                    Bebida(nombre = "Vermouth", cantidad = 0.10, vaso = 4, comida = 6, casa = 8, aviso = 12, muerte = 18, color = "#A0522D", consumido = 0)
                )
                // Inserta las películas iniciales en la base de datos
                val viewModelScope = CoroutineScope(Dispatchers.IO)
                viewModelScope.launch {
                    // Realizar la operación en segundo plano (p. ej., poblar la base de datos)
                    bebidasIniciales.forEach { bebida -> INSTANCE?.miDAO()?.insertar(bebida) }
                }
            }
        }
    }
}