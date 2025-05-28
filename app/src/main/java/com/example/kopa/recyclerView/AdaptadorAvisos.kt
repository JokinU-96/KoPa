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
import com.example.kopa.databinding.RecyclerviewAvisoBinding

class AdaptadorAvisos(var avisos : MutableList<String>, val adapterOnClick : (Int) -> Unit): RecyclerView.Adapter<AdaptadorAvisos.AvisoVH>() {
    inner class AvisoVH(val binding: RecyclerviewAvisoBinding): RecyclerView.ViewHolder(binding.root){
        var posicion: Int = 0
        init {
            binding.btnAviso.setOnClickListener{
                adapterOnClick(posicion)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AvisoVH {
        val binding = RecyclerviewAvisoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AvisoVH(binding)
    }

    override fun getItemCount(): Int {
        return avisos.count()
    }

    override fun onBindViewHolder(holder: AvisoVH, position: Int) {
        holder.binding.btnAviso.text = avisos[position]
    }
}