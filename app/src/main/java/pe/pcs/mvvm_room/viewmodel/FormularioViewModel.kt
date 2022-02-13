package pe.pcs.mvvm_room.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pe.pcs.mvvm_room.config.Constantes
import pe.pcs.mvvm_room.config.PersonalApp.Companion.db
import pe.pcs.mvvm_room.model.Personal

class FormularioViewModel: ViewModel() {

    // Variables que se enlazaran con el modelo de activity_formulario via modelo
    var id = MutableLiveData<Long>()
    var nombre = MutableLiveData<String>()
    var apellido = MutableLiveData<String>()
    var telefono = MutableLiveData<String>()
    var email = MutableLiveData<String>()
    var edad = MutableLiveData<Int>()

    var operacion = Constantes.OPERACION_INSERTAR
    var operacionExitosa = MutableLiveData<Boolean>()

    // Inicializar
    init {
        edad.value = 18
    }

    fun guardarUsuario() {

        if(validadInformacion()) {
            val mPersona = Personal(
                0,
                nombre.value.toString(),
                apellido.value.toString(),
                telefono.value.toString(),
                email.value.toString(),
                edad.value?: 18
            )

            when(operacion) {
                Constantes.OPERACION_INSERTAR -> {
                    // Logica para insertar
                    viewModelScope.launch {
                        val result = withContext(Dispatchers.IO) {
                            db.personalDao().insert(
                                arrayListOf(mPersona)
                            )
                        }

                        operacionExitosa.value = result.isNotEmpty()
                    }
                }
                Constantes.OPERACION_EDITAR -> {
                    mPersona.idEmpleado = id.value!!

                    viewModelScope.launch {
                        val result = withContext(Dispatchers.IO) {
                            db.personalDao().update(mPersona)
                        }

                        // validar la operacion si es mayor a cero, entonces fue exitoso
                        operacionExitosa.value = result > 0
                    }
                }
            }
        } else {
            operacionExitosa.value = false
        }

    }

    fun cargarDatos() {
        viewModelScope.launch {
            val persona = withContext(Dispatchers.IO) {
                db.personalDao().getById(id.value ?: 0)
            }

            // Con losa datos devueltos, llenarlos
            nombre.value = persona.nombre
            apellido.value = persona.apellido
            telefono.value = persona.telefono
            email.value = persona.email
            edad.value = persona.edad
        }
    }

    fun eliminarPersonal() {
        val mPersona = Personal(
            id.value!!,
            nombre.value.toString(),
            apellido.value.toString(),
            telefono.value.toString(),
            email.value.toString(),
            edad.value?: 18
        )

        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                db.personalDao().delete(mPersona)
            }

            operacionExitosa.value = result > 0
        }
    }

    private fun validadInformacion(): Boolean {
        // Devuelve si la info no es nula o vacia y la edad es mayor a 0 y menor de 150
        return !(nombre.value.isNullOrEmpty() ||
                apellido.value.isNullOrEmpty() ||
                telefono.value.isNullOrEmpty() ||
                email.value.isNullOrEmpty() ||
                edad.value!! <= 0 || edad.value!! >= 150
                )
    }

}