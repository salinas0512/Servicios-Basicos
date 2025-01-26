package cl.csalinas.android.serviciosbasicos.modelo

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ServicioDao {

    // Método para insertar un nuevo servicio en la base de datos
    @Insert
    suspend fun insert(servicio: Servicio)

    // Método para obtener todos los servicios registrados
    @Query("SELECT * FROM servicios")
    suspend fun getAllServicios(): List<Servicio>

    // Método para eliminar un servicio por su id (opcional, si necesitas eliminar)
    @Query("DELETE FROM servicios WHERE id = :servicioId")
    suspend fun deleteServicioById(servicioId: Int)
}