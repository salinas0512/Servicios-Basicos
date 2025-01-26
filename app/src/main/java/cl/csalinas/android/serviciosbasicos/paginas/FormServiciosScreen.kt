package cl.csalinas.android.serviciosbasicos.paginas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import cl.csalinas.android.serviciosbasicos.modelo.Servicio
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import cl.csalinas.android.serviciosbasicos.R
import cl.csalinas.android.serviciosbasicos.viewmodel.ServicioViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormServiciosScreen(navController: NavHostController, servicioViewModel: ServicioViewModel = viewModel()) {
    var medidor by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }
    var tipo by remember { mutableStateOf("") }
    //Variable contexto para recursos de texto
    val contexto = LocalContext.current

    // Estado del snackbar
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope() // Usado para lanzar el snackbar

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title ={
                    Text(
                    text = contexto.getString(R.string.tittle_registro_medidor),
                    fontWeight = FontWeight.Bold) }
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                snackbar = {
                    // Aquí puedes modificar el color del Snackbar directamente
                    Snackbar(
                        snackbarData = it,
                        containerColor = Color(0xFF6C54B8) ,
                        contentColor = Color.White // Texto blanco
                    )
                }
            )
        }, // SnackbarHost para mostrar mensaje de éxito
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 50.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Spacer(modifier = Modifier.height(15.dp))
                TextField(
                    value = medidor,
                    onValueChange = { medidor = it },
                    label = { Text("Medidor") },
                    modifier = Modifier.fillMaxWidth(),
                )
                TextField(
                    value = fecha,
                    onValueChange = { fecha = it },
                    label = { Text("Fecha") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(33.dp))
                Text("Medidor de:", textAlign = TextAlign.Start)
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start // Alinear opciones a la izquierda
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = tipo == "Agua",
                            onClick = { tipo = "Agua" }
                        )
                        Text("Agua")
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = tipo == "Luz",
                            onClick = { tipo = "Luz" }
                        )
                        Text("Luz")
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = tipo == "Gas",
                            onClick = { tipo = "Gas" }
                        )
                        Text("Gas")
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        // Crear una nueva instancia de Servicio con los datos del formulario
                        val nuevoServicio =
                            Servicio(tipo = tipo, medidor = medidor.toInt(), fecha = fecha)

                        // Usar ViewModel para insertar en la base de datos
                        servicioViewModel.insertarServicio(nuevoServicio)  // Insertar en la base de datos

                        // Mostrar mensaje de éxito usando Snackbar
                        scope.launch {
                            snackbarHostState.showSnackbar("Medición registrada con éxito")
                            //Redirigir a la página del listado cuando acaba el mensaje de éxito
                            navController.navigate("list-servicios")
                        }
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally) // Centrar el botón
                ) {
                    Text(
                        text = contexto.getString(R.string.btn_text_registrar_medicion)
                    )
                }
            }
        }
    )
}
