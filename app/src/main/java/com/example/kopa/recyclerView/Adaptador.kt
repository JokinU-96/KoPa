package com.example.kopa.recyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kopa.ListFragment
import com.example.kopa.databinding.FragmentListBinding
import com.example.kopa.modelo.Bebida

class Adaptador(var listaDEbebidas: MutableList<Bebida>):RecyclerView.Adapter<Adaptador.BebidaVH>() {
    inner class BebidaVH(val binding: FragmentListBinding): RecyclerView.ViewHolder(binding.root){
        var position: Int = 0
        init {
            //En este lugar inicializo cada uno de los elementos de la lista con
            //los datos de la bebida correspondiente.
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BebidaVH {
        val binding = FragmentListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BebidaVH(binding)
    }

    override fun getItemCount(): Int {
        return listaDEbebidas.count()
    }

    override fun onBindViewHolder(holder: BebidaVH, position: Int) {

    }
}