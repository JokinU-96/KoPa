package com.example.kopa

import android.os.Bundle
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
        //Cargo los datos de manera que tenoo una bbdd poblada.
        (activity as MainActivity).miViewModel.mostrarBebidas()
        //Alimento mis recycler views.
        (activity as MainActivity).miViewModel.bebidas.observe(activity as MainActivity){
            binding.rvbebidas.layoutManager = LinearLayoutManager(activity)
            binding.rvbebidas.adapter=AdaptadorBebidas(it)
        }


        //Añado un menú personalizado para la pantalla de la lista de bebidas.
        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater){
                menuInflater.inflate(R.menu.menu_list, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                findNavController().navigate(R.id.action_listFragment_to_dataFragment)
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