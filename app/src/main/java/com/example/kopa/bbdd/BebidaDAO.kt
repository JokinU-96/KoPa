package com.example.kopa.bbdd

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.kopa.modelo.Bebida
import kotlinx.coroutines.flow.Flow

@Dao
interface BebidaDAO {
    @Query("SELECT * FROM bebidas ORDER BY nombre ASC")
    fun mostrarBebidas(): Flow<List<Bebida>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertar(miBebida: Bebida)
    @Delete
    suspend fun borrar(miBebida: Bebida)
    @Update
    suspend fun modificar(miBebida: Bebida)
}