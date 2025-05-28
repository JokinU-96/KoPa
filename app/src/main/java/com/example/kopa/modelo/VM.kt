package com.example.kopa.modelo

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.kopa.bbdd.Bebida
import com.example.kopa.bbdd.Repositorio
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date

class VM(private val miRepositorio : Repositorio):ViewModel() {

    var bebidas : LiveData<List<Bebida>> = miRepositorio.mostrarBebidas().asLiveData()
    var usuario : Usuario? = null

    @RequiresApi(Build.VERSION_CODES.O)
    val formatter = DateTimeFormatter.ofPattern("HH:mm")

    @RequiresApi(Build.VERSION_CODES.O)
    val crono = LocalDateTime.now().format(formatter).toString()

    @RequiresApi(Build.VERSION_CODES.O)
    var horaIni : String = ""

    val avisos: MutableList<String> = mutableListOf()
    var progreso : MutableList<Bebida> = mutableListOf()
    //var avisos : MutableLiveData<MutableList<String>> = MutableLiveData()
    //var progreso : MutableLiveData<MutableList<Bebida>> = MutableLiveData()

    fun mostrarBebidas() = viewModelScope.launch{
        bebidas = miRepositorio.mostrarBebidas().asLiveData()
    }

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