package com.example.kopa

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.kopa.bbdd.Bebida
import com.example.kopa.databinding.FragmentCreateBinding

class CreateFragment : Fragment() {
    private var _binding: FragmentCreateBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCreateBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var color : String = ""

        (activity as MainActivity).adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        (activity as MainActivity).spinner.adapter = (activity as MainActivity).adapter

        (activity as MainActivity).spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedItem = (activity as MainActivity).spinnerItems[position]
                color = selectedItem
                val colorArgb = Color.parseColor(color)
                binding.spinner.background = ColorDrawable(colorArgb)
                Toast.makeText(context, "Selected item: $selectedItem", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }
        }

        binding.btnAnyadirBebida.setOnClickListener(){
            try{
                if (binding.etNombre.text.isNullOrEmpty() &&
                    binding.etVolumen.text.isNullOrEmpty() &&
                    binding.etCasa.text.isNullOrEmpty() &&
                    binding.etComida.text.isNullOrEmpty() &&
                    binding.etAviso.text.isNullOrEmpty() &&
                    binding.etMuerte.text.isNullOrEmpty()
                    ){
                    Toast.makeText(context, "Faltan datos por introducir.", Toast.LENGTH_SHORT).show()
                } else {
                    var BebidaAnyadida : Bebida = Bebida(
                        nombre = binding.etNombre.text.toString(),
                        cantidad = binding.etVolumen.text.toString().toDouble(),
                        vaso = binding.etVaso.text.toString().toInt(),
                        comida = binding.etComida.text.toString().toInt(),
                        casa = binding.etCasa.text.toString().toInt(),
                        aviso = binding.etAviso.text.toString().toInt(),
                        muerte = binding.etMuerte.text.toString().toInt(),
                        color = color,
                        consumido = 0
                    )
                    (activity as MainActivity).miViewModel.insertar(BebidaAnyadida)
                    Toast.makeText(context, "La bebida ha sido introducida correctamente en la base de datos.", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_createFragment_to_listFragment)

                }

            } catch (e : NumberFormatException){
                Toast.makeText(context, "Por favor ingresa un valor válido.", Toast.LENGTH_SHORT).show()
            }

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}