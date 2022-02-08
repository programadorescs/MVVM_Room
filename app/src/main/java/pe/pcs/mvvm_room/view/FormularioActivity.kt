package pe.pcs.mvvm_room.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import pe.pcs.mvvm_room.MainActivity
import pe.pcs.mvvm_room.R
import pe.pcs.mvvm_room.config.Constantes
import pe.pcs.mvvm_room.databinding.ActivityFormularioBinding
import pe.pcs.mvvm_room.dialog.BorrarDialogo
import pe.pcs.mvvm_room.viewmodel.FormularioViewModel

class FormularioActivity : AppCompatActivity(), BorrarDialogo.BorrarListener {

    lateinit var binding: ActivityFormularioBinding
    lateinit var viewModel: FormularioViewModel
    lateinit var dialogo: BorrarDialogo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Iniciar el binding
        binding = ActivityFormularioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // inicializa el viewmodel
        viewModel = ViewModelProvider(this).get()

        viewModel.operacion = intent.getStringExtra(Constantes.OPERACION_KEY)!!
        binding.modelo = viewModel // Esto es para enlazar con el data binding del xml
        binding.lifecycleOwner = this

        // Iniciarlizar el dialogo borrar
        dialogo = BorrarDialogo(this)

        viewModel.operacionExitosa.observe(this, Observer {
            if(it) {
                mostrarMensaje("Operacion existosa")
                irAlInicio()
            } else {
                mostrarMensaje("Ocurrio un error")
            }
        })

        if(viewModel.operacion.equals(Constantes.OPERACION_EDITAR)) {
            viewModel.id.value = intent.getLongExtra(Constantes.ID_PERSONAL_KEY, 0)
            viewModel.cargarDatos()

            binding.btEditar.visibility = View.VISIBLE
            binding.btEliminar.visibility = View.VISIBLE
            binding.btGuardar.visibility = View.GONE
        } else {
            binding.btEditar.visibility = View.GONE
            binding.btEliminar.visibility = View.GONE
            binding.btGuardar.visibility = View.VISIBLE
        }

        binding.btGuardar.setOnClickListener {
            viewModel.guardarUsuario()
        }

        binding.btEditar.setOnClickListener {
            viewModel.guardarUsuario()
        }

        binding.btEliminar.setOnClickListener {
            mostrarDialogo()
        }
    }

    private fun mostrarMensaje(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }

    private fun irAlInicio() {
        val intent = Intent(applicationContext, MainActivity::class.java)
        // Quiere decir que cuando cambie esta actividad,
        // toda lo que estaba anterior se va a destruir y no le van a poder dar atras
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }

    private fun mostrarDialogo() {
        dialogo.show(supportFragmentManager, "Borrar registro")
    }

    override fun borrarPersonal() {
        viewModel.eliminarPersonal()
    }
}