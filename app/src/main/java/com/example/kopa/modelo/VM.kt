package com.example.kopa.modelo

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.kopa.bbdd.Bebida
import com.example.kopa.bbdd.Repositorio
import kotlinx.coroutines.launch

class VM(private val miRepositorio : Repositorio):ViewModel() {
    var bebidas : LiveData<List<Bebida>> = miRepositorio.mostrarBebidas().asLiveData()
    var colores : MutableList<Color> = mutableListOf()
    var usuario : Usuario? = null

    var progreso : MutableList<Bebida> = mutableListOf()

    fun insertar(miBebida: Bebida) =viewModelScope.launch{
        miRepositorio.insertar(miBebida)
    }
    fun borrar(miBebida: Bebida) =viewModelScope.launch{
        miRepositorio.borrar(miBebida)
    }
    fun modificar(miBebida: Bebida) =viewModelScope.launch{
        miRepositorio.modificar(miBebida)
    }
}

class BebidaViewModelFactory(private val miRepositorio: Repositorio): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VM::class.java)){
            @Suppress("UNCHECKED_CAST")
            return VM(miRepositorio) as T
        }
        throw IllegalArgumentException("ViewModel class desconocida")
    }
}