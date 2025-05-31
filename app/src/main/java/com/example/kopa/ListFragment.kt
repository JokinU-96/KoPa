package com.example.kopa

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kopa.databinding.FragmentListBinding
import com.example.kopa.recyclerView.AdaptadorBebidas

class ListFragment : Fragment() {
    private var _binding: FragmentListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Añado la recycler view al fragmento.
        //Alimento mis recycler views.
        (activity as MainActivity).miViewModel.bebidas.observe(activity as MainActivity){
            binding.rvbebidas.layoutManager = LinearLayoutManager(activity)
            binding.rvbebidas.adapter=AdaptadorBebidas(it) { posicion ->

                (activity as MainActivity).miViewModel.bebidas.value?.get(posicion)?.let{ bebidaActual ->
                    Log.d("Bebida actual para ser eliminada: ", bebidaActual.nombre)

                    (activity as MainActivity).miViewModel.borrar(bebidaActual)

                }
            }
        }


        //Añado un menú personalizado para la pantalla de la lista de bebidas.
        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater){
                menuInflater.inflate(R.menu.menu_list, menu)
            }
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.anyadirBebida -> {
                        findNavController().navigate(R.id.action_listFragment_to_createFragment)
                        true // Indicate that the event has been handled
                    }
                    android.R.id.home -> { // Example for another menu item
                        findNavController().navigate(R.id.action_listFragment_to_dataFragment)
                        true
                    }
                    else -> false // Return false for unhandled items, allowing other MenuProviders to handle them
                }
                return true
            }

        },viewLifecycleOwner, Lifecycle.State.RESUMED)

    }

    override fun onDestroyView() {
        (activity as MainActivity).miViewModel.bebidas.removeObservers(activity as MainActivity)
        super.onDestroyView()
        _binding = null
    }
}