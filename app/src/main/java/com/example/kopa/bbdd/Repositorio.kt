package com.example.kopa.bbdd

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class Repositorio (val miDAO: BebidaDAO) {

    fun mostrarBebidas(): Flow<List<Bebida>> {
        return miDAO.mostrarBebidas()
    }

    fun mostrarBebidasConsumidas():Flow<List<Bebida>> {
        return miDAO.mostrarBebidasConsumidas()
    }

    suspend fun resetearConsumo(){
        return miDAO.resetearConsumo()
    }

    @WorkerThread
    suspend fun insertar(miBebida: Bebida){
        miDAO.insertar(miBebida)
    }
    @WorkerThread
    suspend fun borrar(miBebida: Bebida){
        miDAO.borrar(miBebida)
    }
    @WorkerThread
    suspend fun modificar(miBebida: Bebida){
        miDAO.modificar(miBebida)
    }

}