package cl.csalinas.android.serviciosbasicos.paginas

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.lifecycle.viewmodel.compose.viewModel
import cl.csalinas.android.serviciosbasicos.modelo.Servicio
import cl.csalinas.android.serviciosbasicos.viewmodel.ServicioViewModel
import cl.csalinas.android.serviciosbasicos.R
import java.text.DecimalFormat


@Composable
fun ListServicioScreen(navController: NavHostController, servicioViewModel: ServicioViewModel = viewModel()) {
    // Obtenemos la lista de servicios desde el ViewModel
    val servicios = servicioViewModel.allServicios.collectAsState(initial = listOf())

    // Llamada para obtener los servicios al iniciar la pantalla
    LaunchedEffect(true) {
        servicioViewModel.obtenerServicios()
    }
    // Formateador para los valores del medidor
    val decimalFormat = remember { DecimalFormat("#,###") }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("form-servicios") }
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Agregar Servicio")
            }
        },
        content = { padding ->
            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .padding(vertical = 15.dp)
            ) {
                items(servicios.value) { servicio ->
                    // Cada servicio se muestra en una fila
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 40.dp, vertical = 8.dp)
                    ) {
                        // Columna para tipo de servicio e icono
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            when (servicio.tipo) {
                                "Agua" -> Icon(
                                    painter = painterResource(id = R.drawable.agua),
                                    contentDescription = "Ícono de Agua",
                                    tint = Color.Unspecified,
                                    modifier = Modifier.size(27.dp)
                                )
                                "Luz" -> Icon(
                                    painter = painterResource(id = R.drawable.luz1),
                                    contentDescription = "Ícono de Luz",
                                    tint = Color.Unspecified,
                                    modifier = Modifier.size(27.dp)
                                )
                                "Gas" -> Icon(
                                    painter = painterResource(id = R.drawable.gas),
                                    contentDescription = "Ícono de Gas",
                                    tint = Color.Unspecified,
                                    modifier = Modifier.size(27.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = servicio.tipo.uppercase()) // Tipo de servicio
                        }


                        Row(
                            modifier = Modifier.weight(1f),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Medidor
                            Text(
                                text = decimalFormat.format(servicio.medidor).replace(",", "."),
                                modifier = Modifier.width(100.dp), // Ancho fijo para alinear
                                textAlign = TextAlign.Center
                            )

                            Spacer(modifier = Modifier.width(21.dp))

                            // Fecha
                            Text(
                                text = servicio.fecha,
                                modifier = Modifier.width(100.dp), // Ancho fijo para alinear
                                textAlign = TextAlign.Center
                            )
                        }

                        // Añadir espaciador antes del botón de eliminar
                        Spacer(modifier = Modifier.width(31.dp))

                        // Botón de eliminar
                        IconButton(
                            onClick = { servicioViewModel.borrarServicio(servicio.id) }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Eliminar Servicio",
                                tint = Color.Red
                            )
                        }
                    }
                    HorizontalDivider(modifier = Modifier.fillMaxWidth())
                    Spacer(modifier = Modifier.height(10.dp)) // Espacio entre ítems
                }
            }
        }
    )
}

