package com.example.kopa.recyclerView

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.kopa.R
import com.example.kopa.databinding.RecyclerviewBebidaBinding
import com.example.kopa.bbdd.Bebida

class AdaptadorBebidas(var listaDEbebidas: List<Bebida>):RecyclerView.Adapter<AdaptadorBebidas.BebidaVH>() {
    inner class BebidaVH(val binding: RecyclerviewBebidaBinding): RecyclerView.ViewHolder(binding.root){
        var posicion: Int = 0
        init {
            //En este lugar inicializo cada uno de los elementos de la lista con
            //los datos de la bebida correspondiente.
            binding.btnVerBebida.setOnClickListener{
                val miBundle = bundleOf("posicion" to posicion)
                //Traslado al usuario a los detalles de cada bebida.
                binding.btnVerBebida.findNavController().navigate(R.id.action_listFragment_to_infoFragment, miBundle)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BebidaVH {
        val binding = RecyclerviewBebidaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BebidaVH(binding)
    }

    override fun getItemCount(): Int {
        return listaDEbebidas.count()
    }

    override fun onBindViewHolder(holder: BebidaVH, position: Int) {
        //Establezco el valor del texto en la recyclerview de cada bebida.
        holder.binding.tvBebida.text = "${listaDEbebidas[position].nombre}"
        val colorArgb = Color.parseColor("${ listaDEbebidas[position].color }")
        holder.binding.rectangulo.background = ColorDrawable(colorArgb)
        holder.posicion = position
    }
}