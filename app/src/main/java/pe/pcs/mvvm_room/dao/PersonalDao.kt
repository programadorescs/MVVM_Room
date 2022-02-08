package pe.pcs.mvvm_room.dao

import androidx.room.*
import pe.pcs.mvvm_room.model.Personal

@Dao
interface PersonalDao {

    // Aqui estara toda las funciones CRUD

    @Query("Select idEmpleado, nombre, apellido, email, telefono, edad FROM Personal")
    suspend fun getAll(): List<Personal> // Retorna una lista de personas

    @Query("Select idEmpleado, nombre, apellido, email, telefono, edad FROM Personal WHERE idEmpleado = :id")
    suspend fun getById(id: Long): Personal // Retorna una personas

    @Query("Select idEmpleado, nombre, apellido, email, telefono, edad FROM Personal WHERE nombre LIKE '%' || :dato || '%' OR apellido LIKE '%' || :dato || '%'")
    suspend fun getByNombre(dato: String): List<Personal>

    @Insert
    suspend fun insert(personas: List<Personal>): List<Long>

    @Update
    suspend fun update(persona: Personal): Int // retorna el nro de registro actualizado

    @Delete
    suspend fun delete(persona: Personal): Int // retorna el nro de registro eliminado

}