package cl.csalinas.android.serviciosbasicos.modelo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Aquí se define la base de datos, especificando las entidades y el DAO que se usarán
@Database(entities = [Servicio::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    // DAO que será utilizado para interactuar con la base de datos
    abstract fun servicioDao(): ServicioDao

    companion object {
        // Instancia de la base de datos
        @Volatile
        private var INSTANCE: AppDatabase? = null

        // Función para obtener la instancia de la base de datos
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database" // Nombre de la base de datos
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}