package cl.csalinas.android.serviciosbasicos.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import cl.csalinas.android.serviciosbasicos.modelo.AppDatabase
import cl.csalinas.android.serviciosbasicos.modelo.Servicio
import cl.csalinas.android.serviciosbasicos.modelo.ServicioDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ServicioViewModel(application: Application) : AndroidViewModel(application) {

    private val servicioDao: ServicioDao = AppDatabase.getDatabase(application).servicioDao()

    // MutableStateFlow para almacenar los registros
    private val _allServicios = MutableStateFlow<List<Servicio>>(emptyList())
    // StateFlow para exponer los registros
    val allServicios: StateFlow<List<Servicio>> = _allServicios

    // Función para obtener todos los servicios (se ejecuta en un hilo de fondo)
    fun obtenerServicios() {
        viewModelScope.launch(Dispatchers.IO) {
            val servicios = servicioDao.getAllServicios()
            // Se actualiza el StateFlow con los servicios obtenidos
            _allServicios.emit(servicios)
        }
    }

    // Función para insertar un nuevo registro
    fun insertarServicio(servicio: Servicio) {
        viewModelScope.launch(Dispatchers.IO) {
            servicioDao.insert(servicio)
        }
    }

    // Función para eliminar un registro
    fun borrarServicio(servicioId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            servicioDao.deleteServicioById(servicioId)
            //Actualizar la lista cuando eliminamos un registro
            _allServicios.value = servicioDao.getAllServicios()
        }
    }
}