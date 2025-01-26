package cl.csalinas.android.serviciosbasicos.modelo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "servicios")
data class Servicio(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val tipo: String,   // Tipo de servicio: Agua, Luz, Gas
    var medidor: Int,   // Valor del medidor
    var fecha: String   // Fecha
)