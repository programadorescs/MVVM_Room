package pe.pcs.mvvm_room.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import pe.pcs.mvvm_room.adapter.PersonalAdapter
import pe.pcs.mvvm_room.config.Constantes
import pe.pcs.mvvm_room.databinding.ActivityMainBinding
import pe.pcs.mvvm_room.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        // Inicializar el binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializar el viewModel
        viewModel = ViewModelProvider(this).get()

        // Enlazar el modelo con live data
        binding.lifecycleOwner = this
        binding.modelo = viewModel

        // Llamamos a la funcion que esta en el viewModel
        viewModel.listarTodo()

        // RecyclerView
        binding.rvLista.apply {
            layoutManager = LinearLayoutManager(applicationContext)
        }

        // Creamos el observador
        viewModel.personalList.observe(this, Observer {
            binding.rvLista.adapter = PersonalAdapter(it)
        })

        binding.etBuscar.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                if(p0.toString().isNotEmpty()) {
                    viewModel.buscarPersonal()
                } else {
                    viewModel.listarTodo()
                }
            }

        })

        binding.fabNuevo.setOnClickListener {
            val intent = Intent(this, FormularioActivity::class.java)
            intent.putExtra(Constantes.OPERACION_KEY, Constantes.OPERACION_INSERTAR)
            startActivity(intent)
        }
    }
}