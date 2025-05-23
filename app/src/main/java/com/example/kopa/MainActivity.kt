package com.example.kopa

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import com.example.kopa.bbdd.Repositorio
import com.example.kopa.bbdd.bbdd
import com.example.kopa.databinding.ActivityMainBinding
import com.example.kopa.modelo.BebidaViewModelFactory
import com.example.kopa.modelo.Usuario
import com.example.kopa.modelo.VM

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding


    val miDataBase by lazy { bbdd.getDatabase(this) }
    val miRepositorio by lazy { Repositorio( miDataBase.miDAO() ) }
    val miViewModel : VM by viewModels{ BebidaViewModelFactory(miRepositorio) }

    //Aquí van los datos globales de la aplicación.
    var bebida:String? = null

    //Para que los datos de usuario persistan incluso despues de apagar y encender el dispositivo.
    lateinit var datos : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //cargo los datos persistentes por si los hubiera.
        datos = this.getSharedPreferences("datos", Context.MODE_PRIVATE)

        //en caso de que los campos del usuario estén llenos, se añaden al ViewModel.
        datos.getString("nombre", "")
            ?.let{nombre ->
                datos.getString("apellido", "")?.let{
                        apellido ->
                    val usuario = Usuario(datos.getString("nombre", "").toString(), datos.getString("apellidos", "").toString(), datos.getInt("edad", 0))
                    miViewModel.usuario = usuario
                }
            }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}