package cl.csalinas.android.serviciosbasicos
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cl.csalinas.android.serviciosbasicos.paginas.FormServiciosScreen
import cl.csalinas.android.serviciosbasicos.paginas.ListServicioScreen



class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "list-servicios") {
                composable("list-servicios") {
                    // Composable de listado
                    ListServicioScreen(navController = navController)
                }
                composable("form-servicios") {
                    // Composable de formulario
                    FormServiciosScreen(navController = navController)
                }
            }
        }
    }
}




