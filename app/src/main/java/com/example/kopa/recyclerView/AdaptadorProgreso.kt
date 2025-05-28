package com.example.kopa.recyclerView

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.example.kopa.MainActivity
import com.example.kopa.databinding.RecyclerviewProgresoBinding
import com.example.kopa.bbdd.Bebida

class AdaptadorProgreso(var listaProgreso : List<Bebida>, val adapterOnClick : (Int) -> Unit): RecyclerView.Adapter<AdaptadorProgreso.ProgresoVH>() {
    inner class ProgresoVH(val binding: RecyclerviewProgresoBinding): RecyclerView.ViewHolder(binding.root){
        var posicion: Int = 0
        init {
            binding.btnConsumo.setOnClickListener{
                adapterOnClick(posicion)
                binding.btnConsumo.text=listaProgreso[posicion].consumido.toString()
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
        holder.binding.btnConsumo.text = "${listaProgreso[position].consumido}"
    }
}