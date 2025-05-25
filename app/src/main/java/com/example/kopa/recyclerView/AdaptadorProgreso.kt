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

class AdaptadorProgreso(var listaProgreso : MutableList<Bebida>, var avisos : MutableList<String>): RecyclerView.Adapter<AdaptadorProgreso.ProgresoVH>() {
    inner class ProgresoVH(val binding: RecyclerviewProgresoBinding): RecyclerView.ViewHolder(binding.root){
        var posicion: Int = 0
        init {
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

        holder.binding.btnConsumo.text = listaProgreso[position].consumido.toString()

        val bebidaActual = listaProgreso[position]

        //Necesito cambiar el valor del consumo.
        holder.binding.btnConsumo.setOnClickListener {
            bebidaActual.consumido += 1
            holder.binding.btnConsumo.text = bebidaActual.consumido.toString()
            //falta modificar la bebida actual en la base de datos.

            //Hago las condicionales para que vaya creando avisos.
            if (bebidaActual.consumido == bebidaActual.vaso){
                avisos.add("Vaso")
            } else if (bebidaActual.consumido == bebidaActual.comida){
                avisos.add("Comida")
            } else if (bebidaActual.consumido == bebidaActual.casa){
                avisos.add("Casa")
            }
        }
    }
}