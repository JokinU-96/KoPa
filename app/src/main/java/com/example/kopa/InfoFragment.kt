package com.example.kopa

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.kopa.databinding.FragmentInfoBinding
import com.example.kopa.bbdd.Bebida

class InfoFragment : Fragment() {
    private var _binding: FragmentInfoBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentInfoBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Voy a mostrar los datos de una bebida, volumen y cantidades de consumo límites.
        var posicion = arguments?.getInt("posicion") ?: -1
        (activity as MainActivity).miViewModel.bebidas.observe(viewLifecycleOwner) { bebidasList ->
            // This block will be executed whenever the data in bebidasList changes

            // Si la posición del elemento seleccionado es errónea la ventana retrocede a donde estaba.
            if (posicion == -1 || posicion >= bebidasList.size) { // Also check if position is within bounds
                findNavController().popBackStack()
            } else {
                // sino, cargo los datos de la bebida.
                val bebida = bebidasList[posicion] // Access the element from the observed list
                binding.etNombre.setText(bebida.nombre)
                binding.etVolumen.setText(bebida.cantidad.toString())
                binding.etVaso.setText(bebida.vaso.toString())
                binding.etComida.setText(bebida.comida.toString())
                binding.etCasa.setText(bebida.casa.toString())
                binding.etAviso.setText(bebida.aviso.toString())
                binding.etMuerte.setText(bebida.muerte.toString())

                binding.btnSumarBebida.setOnClickListener {
                    sumarProgreso(bebida)
                }
            }
        }

    }

    fun sumarProgreso(bebida: Bebida){
        var bebidaConsumida: Bebida = Bebida(bebida.id , bebida.nombre , bebida.cantidad , bebida.vaso , bebida.comida , bebida.casa , bebida.aviso , bebida.muerte, bebida.color , bebida.consumido)

        (activity as MainActivity).miViewModel.progreso.add(bebidaConsumida)

        findNavController().navigate(R.id.action_infoFragment_to_listFragment)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}