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
import com.example.kopa.databinding.FragmentLoginBinding
import com.example.kopa.modelo.Usuario

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class LogInFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val datos: SharedPreferences = (activity as MainActivity).getSharedPreferences("datos", Context.MODE_PRIVATE)
        val editor = datos.edit()

        (activity as MainActivity).miViewModel.usuario?.let {
            binding.etNombre.setText(it.nombre.toString())
            binding.etApellidos.setText(it.apellidos.toString())
            if (it.edad == -1){
                //nada
            } else {
                binding.etEdad.setText(it.edad.toString())
            }
        }

        if ((activity as MainActivity).miViewModel.usuario == null){
            binding.etNombre.setText("")
            binding.etApellidos.setText("")
            binding.etEdad.setText("")
        }

        //Acciones a la hora de pulsar sobre el botón de iniciar sesión.
        binding.btnIniciarSesion.setOnClickListener{
            try {
                if (binding.etNombre.text.isNullOrEmpty()) {
                    Toast.makeText(context, "Por favor introduzca su nombre.", Toast.LENGTH_SHORT)
                        .show()
                } else if (binding.etApellidos.text.isNullOrEmpty()) {
                    Toast.makeText(
                        context,
                        "Por favor introduzca sus apellidos.",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (binding.etEdad.text.isNullOrEmpty()) {
                    Toast.makeText(context, "Por favor introduzca su edad.", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    val edadText = binding.etEdad.text.toString()
                    val edad = edadText.toInt()
                    if (edad >= 16) {
                        var usuarioActual = Usuario(binding.etNombre.text.toString(), binding.etApellidos.text.toString(), edad)

                        (activity as MainActivity).miViewModel.usuario = usuarioActual

                        editor.putInt("edad", edad)
                        editor.putString("nombre", binding.etNombre.text.toString())
                        editor.putString("apellidos", binding.etApellidos.text.toString())
                        editor.apply()

                        findNavController().navigate(R.id.action_logInFragment_to_dataFragment)

                    } else {
                        Toast.makeText(context, "Debes ser mayor de edad para poder entrar a KoPa.", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            } catch (e : NumberFormatException){
                Toast.makeText(context, "Por favor ingresa una edad válida", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}