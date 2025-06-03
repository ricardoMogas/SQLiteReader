package com.richmogas.sqlitereader.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp

/**
 * Componente de layout principal que estructura la aplicación en tres áreas:
 * - Navbar (barra superior)
 * - Body (contenido principal)
 * - Footer (barra de navegación inferior)
 *
 * @param titleNav Título que se mostrará en la barra superior
 * @param routes Lista de rutas/secciones para la navegación inferior
 * @param content Contenido que se renderizará en el cuerpo según la ruta seleccionada
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainLayout(
    titleNav: String,
    routes: List<RouteConfig>,
    content: @Composable (Int, Modifier) -> Unit
) {
    var selectedRouteIndex by remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = titleNav,
                        color = Color.White,
                        fontSize = 18.sp
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF2196F3)
                )
            )
        },
        bottomBar = {
            NavigationBar {
                routes.forEachIndexed { index, routeConfig ->
                    NavigationBarItem(
                        selected = selectedRouteIndex == index,
                        onClick = { selectedRouteIndex = index },
                        icon = {
                            Icon(
                                painter = painterResource(id = routeConfig.iconResId),
                                contentDescription = routeConfig.title
                            )
                        },
                        label = { Text(routeConfig.title) }
                    )
                }
            }
        }
    ) { innerPadding ->
        // Llama al contenido proporcionado con el índice de la ruta seleccionada
        content(selectedRouteIndex, Modifier.padding(innerPadding))
    }
}

/**
 * Clase de configuración para las rutas de navegación
 *
 * @param title Título que se mostrará en la barra de navegación
 * @param iconResId ID del recurso de icono para la ruta
 */
data class RouteConfig(
    val title: String,
    val iconResId: Int
)
