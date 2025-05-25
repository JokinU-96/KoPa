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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kopa.databinding.FragmentDataBinding
import com.example.kopa.recyclerView.AdaptadorAvisos
import com.example.kopa.recyclerView.AdaptadorBebidas
import com.example.kopa.recyclerView.AdaptadorProgreso
import java.nio.charset.MalformedInputException

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

        //De este modo tengo la lista de avisos con un observer que cuando los datos cambien cambiará.
        /*(activity as MainActivity).miViewModel.avisos.observe(activity as MainActivity){
            binding.rvAviso.layoutManager = LinearLayoutManager(activity)
            binding.rvAviso.adapter= AdaptadorAvisos(it)
        }*/

        /*binding.rvProgreso.layoutManager = LinearLayoutManager(activity)
        binding.rvProgreso.adapter = (activity as MainActivity).miViewModel.avisos.value?.let {
            AdaptadorProgreso ((activity as MainActivity).miViewModel.progreso,
                it
            )
        }*/

        (activity as MainActivity).miViewModel.avisos.observe(viewLifecycleOwner) { avisos ->
            // This block will be executed whenever the data in avisos changes
            Toast.makeText(context, "Avisos ha sido modificada", Toast.LENGTH_SHORT).show()
        }

        /*binding.rvAviso.layoutManager = LinearLayoutManager(activity)
        binding.rvAviso.adapter = AdaptadorAvisos ((activity as MainActivity).miViewModel.avisos)*/

        //Defino la acción del botón +.
        //Deberá llevar al listado de bebidas. Esta recoge los elementos desde la base de datos.
        binding.btnSuma.setOnClickListener{
            if((activity as MainActivity).miViewModel.usuario == null) {
                Toast.makeText(context, "Debes hacer el login antes de añadir una bebida.", Toast.LENGTH_SHORT).show()
            } else {
                findNavController().navigate(R.id.action_dataFragment_to_listFragment)
            }
        }

    }

    override fun onDestroyView() {
        //(activity as MainActivity).miViewModel.avisos.removeObservers(activity as MainActivity)
        //(activity as MainActivity).miViewModel.progreso.removeObservers(activity as MainActivity)
        super.onDestroyView()
        _binding = null
    }
}