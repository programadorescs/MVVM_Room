package pe.pcs.mvvm_room.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pe.pcs.mvvm_room.config.PersonalApp.Companion.db
import pe.pcs.mvvm_room.model.Personal

class MainViewModel: ViewModel() {

    val personalList = MutableLiveData<List<Personal>>()
    var parametroBusqueda = MutableLiveData<String>()

    fun listarTodo() {
        viewModelScope.launch {
            personalList.value = withContext(Dispatchers.IO) {

                db.personalDao().getAll()

            }
        }
    }

    fun buscarPersonal() {
        viewModelScope.launch {
            personalList.value = withContext(Dispatchers.IO) {

                db.personalDao().getByNombre(parametroBusqueda.value ?: "")

            }
        }
    }
}