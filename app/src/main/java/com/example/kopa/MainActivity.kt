package com.example.kopa

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import com.example.kopa.bbdd.Repositorio
import com.example.kopa.bbdd.bbdd
import com.example.kopa.databinding.ActivityMainBinding
import com.example.kopa.modelo.BebidaViewModelFactory
import com.example.kopa.modelo.Usuario
import com.example.kopa.modelo.VM
import java.util.Date

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    val miDataBase by lazy { bbdd.getDatabase(this) }
    val miRepositorio by lazy { Repositorio( miDataBase.miDAO() ) }
    val miViewModel : VM by viewModels{ BebidaViewModelFactory(miRepositorio) }

    val spinner: Spinner by lazy { findViewById(R.id.spinner) }
    val spinnerItems: Array<String> by lazy { resources.getStringArray(R.array.spinner_items) }
    val adapter: ArrayAdapter<String> by lazy { ArrayAdapter(this, android.R.layout.simple_spinner_item, spinnerItems) }

    //Aquí van los datos globales de la aplicación.
    var bebida:String? = null

    //Para que los datos de usuario persistan incluso despues de apagar y encender el dispositivo.
    lateinit var datos : SharedPreferences

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        //Recogido desde https://medium.com/@myofficework000/a-spinner-is-a-drop-down-menu-that-allows-users-to-select-an-item-from-a-list-e61d48368e5
        //Pretendo crear un menú desplegable en la vista Create_fragment para elegir el color de la bebida.
        val spinner = findViewById<Spinner>(R.id.spinner)
        val spinnerItems = resources.getStringArray(R.array.spinner_items)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, spinnerItems)


        //cargo los datos persistentes por si los hubiera.
        datos = this.getSharedPreferences("datos", Context.MODE_PRIVATE)

        // En caso de que los campos del usuario estén llenos, se añaden al ViewModel.
        datos.getString("nombre", "")
            ?.let{nombre ->
                datos.getString("apellidos", "")?.let{
                        apellido ->
                    val usuario = Usuario(datos.getString("nombre", "").toString(), datos.getString("apellidos", "").toString(), datos.getInt("edad", -1))
                    miViewModel.usuario = usuario
                }
            }

        //Relleno la última hora guardada
        datos.getString("hora", "")
            ?.let { hora ->
                miViewModel.horaIni = datos.getString("hora", "1996-01-26 10:30:56").toString()
            }

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Cargo los datos de manera que tenoo una bbdd poblada.
        miViewModel.mostrarBebidas()

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)//Para que la flecha de Volver no aparezca en la pantalla principal.
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> {
                true
            }
            R.id.reset -> {
                miViewModel.resetearConsumo()
                true // Indicate that the event has been handled
            }
            R.id.action_logout -> {
                miViewModel.usuario = null

                val datos : SharedPreferences = this.getSharedPreferences("datos", Context.MODE_PRIVATE)
                val editor : SharedPreferences.Editor = datos.edit()

                editor.putString("nombre", null)//Nombre
                editor.putString("apellidos", null)//Apellidos
                editor.putInt("edad", -1)
                editor.apply()

                val navController = findNavController(R.id.nav_host_fragment_content_main)
                //Borrar la pila de fragmentos
                navController.popBackStack(R.id.logInFragment, false)
            }
            else -> {
                super.onOptionsItemSelected(item)
            }

        }

    }



    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}