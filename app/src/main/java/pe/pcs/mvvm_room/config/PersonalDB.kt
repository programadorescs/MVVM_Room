package pe.pcs.mvvm_room.config

import androidx.room.Database
import androidx.room.RoomDatabase
import pe.pcs.mvvm_room.dao.PersonalDao
import pe.pcs.mvvm_room.model.Personal

// Agregar la notacion Database que indica que sera una base de datos
// dentro de esta notacion ira las tablas que tendra la DB y la version de la DB.
// estas tablas estaran definidas en el paquete model
@Database(
    entities = [Personal::class],
    version = 1
)
abstract class PersonalDB: RoomDatabase() {

    // Aqui estara la funcion que engloba a la interfaz Dao (PersonalDao)
    abstract fun personalDao(): PersonalDao

}