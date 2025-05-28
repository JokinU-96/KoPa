package com.example.kopa

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kopa.databinding.FragmentDataBinding
import com.example.kopa.recyclerView.AdaptadorAvisos
import com.example.kopa.recyclerView.AdaptadorProgreso
import androidx.core.content.edit
import com.example.kopa.bbdd.Bebida
import com.example.kopa.recyclerView.AdaptadorBebidas
import androidx.core.graphics.toColorInt
import java.time.Duration
import java.time.LocalDateTime

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

        binding.editTextTime.setText((activity as MainActivity).miViewModel.horaIni)

        //Cargo las bebidas consumidas cada vez que entro en esta pantalla.
        (activity as MainActivity).miViewModel.mostrarBebidasConsumidas()
        (activity as MainActivity).miViewModel.progreso.observe(activity as MainActivity){
            //Compruebo que no sea nulo para empezar a jugar con los elementos en el progreso.
            it?.let {
                //Si el listado de bebidas del progreso está a 0 o es nulo, se actualiza la hora de inicio.
                if ((activity as MainActivity).miViewModel.progreso.value?.count() == 1) {
                    Log.d("hora", "A por la hora")
                    val datos: SharedPreferences = (activity as MainActivity).getSharedPreferences("datos", Context.MODE_PRIVATE)
                    datos.edit {
                        //Defino la hora
                        putString("hora", binding.editTextTime.text.toString())
                        Log.d("hora", "Hora lista")
                    }
                }

                //hasta que la sesión termine y las bebidas se queden a 0, la hora se queda en rojo con la fecha en la que se empezó a beber.
                (activity as MainActivity).miViewModel.progreso.value?.count()?.let { it1 ->
                    if(it1 >= 1){
                        binding.editTextTime.setTextColor(/* color = */ "#C82929".toColorInt())
                    }
                }

                binding.rvProgreso.layoutManager = LinearLayoutManager(activity)
                binding.rvProgreso.adapter= AdaptadorProgreso(it) { posicion ->

                    Log.d("Bebida Actual: ",(activity as MainActivity).miViewModel.progreso.value?.get(posicion)?.nombre ?:"null")


                    (activity as MainActivity).miViewModel.progreso.value?.get(posicion)?.let{ bebidaActual->

                        Log.d("Bebida Actual post Let: ",bebidaActual.nombre)

                        bebidaActual.consumido += 1
                        //Cada vez que pulso el botón de consumo, modifico la base de datos.
                        //La bebida Actual es un objeto con todas las propiedades de bebida.
                        (activity as MainActivity).miViewModel.modificar(bebidaActual)

                        cargarLogicaApp()

                        ejecutarLogicaAvisos(bebidaActual)

                        actualizarAvisos()

                    }
                }
            }

        }
        //Cuando entro en la pantalla actualizo los avisos.
        actualizarAvisos()



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

    @RequiresApi(Build.VERSION_CODES.O)
    private fun cargarLogicaApp() {
        /*Pautas:
        - Temporales:
            . Cada hora que pase, el consumo se reduce en la inversa de una copa dividida entre las copas necesarias para volver a casa. (Ej. [1 - (1 / 8)] en el caso de la Cerveza)
         */
        // 3. Parsear los Strings a objetos LocalDateTime
        val Act = LocalDateTime.parse((activity as MainActivity).miViewModel.crono, (activity as MainActivity).miViewModel.formatter)
        val Ini = LocalDateTime.parse((activity as MainActivity).miViewModel.horaIni, (activity as MainActivity).miViewModel.formatter)

        // 4. Calcular la duración entre las dos fechas
        val duration = Duration.between(Ini, Act)
        val horasTranscurridas = duration.toHours()
        (activity as MainActivity).miViewModel.progreso.value?.let {
            for(bebida in (activity as MainActivity).miViewModel.progreso.value){
                bebida.consumido -= (horasTranscurridas * ( 0.5f - ( 1.0f / bebida.casa ))).toInt()
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


