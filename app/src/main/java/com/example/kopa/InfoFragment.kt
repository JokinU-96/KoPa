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
        //Voy a mostrar los datos de cada bebida, volumen y cantidades de consumo límites.
        var posicion = arguments?.getInt("posicion") ?: -1
        var bebida = (activity as MainActivity).miViewModel.bebidas[0]

        //Si la posción del elemento seleccionado es erroneo la ventana retrocede a donde estaba.
        if (posicion == -1) findNavController().popBackStack()
        else{
            //sino, cargo los datos de la bebida.
            bebida = (activity as MainActivity).miViewModel.bebidas[posicion]
            binding.etNombre.setText(bebida.nombre);
            binding.etVolumen.setText(bebida.cantidad.toString());
            binding.etVaso.setText(bebida.vaso.toString());
            binding.etComida.setText(bebida.comida.toString());
            binding.etCasa.setText(bebida.casa.toString());
            binding.etAviso.setText(bebida.aviso.toString());
            binding.etMuerte.setText(bebida.muerte.toString());
        }

        binding.btnSumarBebida.setOnClickListener{
            sumarProgreso(bebida)
        }

    }

    fun sumarProgreso(bebida: Bebida){
        var bebidaConsumida: Bebida = Bebida(bebida.nombre , bebida.cantidad , bebida.vaso , bebida.comida , bebida.casa , bebida.aviso , bebida.muerte, bebida.color , bebida.consumido)

        (activity as MainActivity).miViewModel.progreso.add(bebidaConsumida)

        findNavController().navigate(R.id.action_infoFragment_to_listFragment)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}