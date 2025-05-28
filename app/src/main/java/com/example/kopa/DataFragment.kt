package com.example.kopa

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kopa.databinding.FragmentDataBinding
import com.example.kopa.recyclerView.AdaptadorAvisos
import com.example.kopa.recyclerView.AdaptadorProgreso
import androidx.core.content.edit
import com.example.kopa.bbdd.Bebida
import com.example.kopa.recyclerView.AdaptadorBebidas

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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Nulo si el progreso está a 0.
        //Log.d("dataf", (activity as MainActivity).miViewModel.progreso.value?.count().toString() ?:"-1")

        //Cargo las bebidas consumidas cada vez que entro en esta pantalla.
        (activity as MainActivity).miViewModel.mostrarBebidasConsumidas()
        (activity as MainActivity).miViewModel.progreso.observe(activity as MainActivity){
            it?.let {
                binding.rvProgreso.layoutManager = LinearLayoutManager(activity)
                binding.rvProgreso.adapter= AdaptadorProgreso(it) { posicion ->

                    Log.d("Bebida Actual: ",(activity as MainActivity).miViewModel.progreso.value?.get(posicion)?.nombre ?:"null")

                    (activity as MainActivity).miViewModel.progreso.value?.get(posicion)?.let{ bebidaActual->

                        Log.d("Bebida Actual post Let: ",bebidaActual.nombre)

                        bebidaActual.consumido += 1
                        //Cada vez que pulso el botón de consumo, modifico la base de datos.
                        //La bebida Actual es un objeto con todas las propiedades de bebida.
                        (activity as MainActivity).miViewModel.modificar(bebidaActual)

                        ejecutarLogicaAvisos(bebidaActual)

                        actualizarAvisos()

                    }
                }
            }

        }
        //Cuando entro en la pantalla actualizo los avisos.
        actualizarAvisos()

        //Si el listado de bebidas del progreso está a 0 o es nulo, se actualiza la hora de inicio.
        (activity as MainActivity).miViewModel.progreso.value?.let {
            val datos: SharedPreferences =
                (activity as MainActivity).getSharedPreferences("datos", Context.MODE_PRIVATE)
            datos.edit {
                //Defino la hora
                binding.editTextTime.setText((activity as MainActivity).miViewModel.horaIni)
                putString("hora", binding.editTextTime.text.toString())
            }
        }



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

    private fun actualizarAvisos() {
        (activity as MainActivity).miViewModel.avisos.value?.let {

            Log.d("dataf", "Dentro de avisos no null.")
            binding.rvAviso.layoutManager = LinearLayoutManager(activity)
            binding.rvAviso.adapter = AdaptadorAvisos (it){ posicion ->

                Log.d("Aviso borrado: ", (activity as MainActivity).miViewModel.avisos.value?.get(posicion).toString())

                (activity as MainActivity).miViewModel.avisos.value?.remove((activity as MainActivity).miViewModel.avisos.value?.get(posicion).toString())

                actualizarAvisos()
            }
        }
    }

    private fun ejecutarLogicaAvisos(bebidaActual: Bebida) {
        //Hago las condicionales para que vaya creando avisos.
        if (bebidaActual.consumido == bebidaActual.vaso){
            (activity as MainActivity).miViewModel.avisos.value.add("Vaso")
            Log.d("avisos","Vaso")
        } else if (bebidaActual.consumido == bebidaActual.comida){
            (activity as MainActivity).miViewModel.avisos.value.add("Comida")
        } else if (bebidaActual.consumido == bebidaActual.casa){
            (activity as MainActivity).miViewModel.avisos.value.add("Casa")
        } else if (bebidaActual.consumido == bebidaActual.aviso){
            (activity as MainActivity).miViewModel.avisos.value.add("Deja ya de beber")
        } else if (bebidaActual.consumido == bebidaActual.muerte){
            (activity as MainActivity).miViewModel.avisos.value.add("Has muerto")

        }
        else{
            Log.d("avisos", (activity as MainActivity).miViewModel.avisos.value.count().toString())
        }
    }

    override fun onDestroyView() {
        (activity as MainActivity).miViewModel.avisos.removeObservers(activity as MainActivity)
        (activity as MainActivity).miViewModel.progreso.removeObservers(activity as MainActivity)
        super.onDestroyView()
        _binding = null
    }
}