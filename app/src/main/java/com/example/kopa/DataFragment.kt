package com.example.kopa

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.kopa.databinding.FragmentDataBinding

/**
 *  Para mostrar los datos de la aplicación,
 *  algo similar a un panel de control o un escritorio de un pc.
 *  Se muestran las bebidas que se están consumiendo en el momento,
 *  la hora a la que se empieza a consumir alcohol y las alertas o
 *  notificaciones.
 */
class DataFragment : Fragment() {

    private var _binding: FragmentDataBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDataBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Necesito guardar la lista de bebidas que lleva consumidas.
        // Cantidad de veces que ha consumido cada una de las bebidas desde que inició la sesión.
        // Tipos de bebida que ha consumido desde que inició sesión por última vez.
        // La hora a la que empezó a beber.
        val datos: SharedPreferences = (activity as MainActivity).getSharedPreferences("datos", Context.MODE_PRIVATE)
        val editor = datos.edit()

        //La idea es construir dentro de un linear layout una recycler view nueva que
        //se alimente de todas las bebidas que lleva consumiendo el usuario durante cada sesión de bebercio.


        //Defino la acción del botón +.
        //Deberá llevar al listado de bebidas. Esta recoge los elementos desde la base de datos.
        binding.btnSuma.setOnClickListener{
            if((activity as MainActivity).miViewModel.usuario == null) {
                Toast.makeText(context, "Debes hacer el login antes de comprar un vehículo.", Toast.LENGTH_SHORT).show()
            } else {
                findNavController().navigate(R.id.action_dataFragment_to_listFragment)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}