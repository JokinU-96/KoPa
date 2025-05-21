package com.example.kopa.recyclerView

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.RecyclerView
import com.example.kopa.databinding.RecyclerviewProgresoBinding
import com.example.kopa.modelo.Bebida

class AdaptadorProgreso(var listaProgreso : MutableList<Bebida>): RecyclerView.Adapter<AdaptadorProgreso.ProgresoVH>() {
    inner class ProgresoVH(val binding: RecyclerviewProgresoBinding): RecyclerView.ViewHolder(binding.root){
        var posicion: Int = 0
        init {
            binding.btnConsumo.setOnClickListener{
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgresoVH {
        val binding = RecyclerviewProgresoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProgresoVH(binding)
    }

    override fun getItemCount(): Int {
        return listaProgreso.count()
    }

    override fun onBindViewHolder(holder: ProgresoVH, position: Int) {
        holder.binding.tvBebida.text = "${listaProgreso[position].nombre}"
        val colorArgb = Color.parseColor("${ listaProgreso[position].color }")
        holder.binding.rectangulo.background = ColorDrawable(colorArgb)
        holder.posicion = position
    }
}