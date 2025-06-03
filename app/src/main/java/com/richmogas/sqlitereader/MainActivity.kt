package com.richmogas.sqlitereader

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.richmogas.sqlitereader.components.MainLayout
import com.richmogas.sqlitereader.components.RouteConfig
import com.richmogas.sqlitereader.ui.theme.SQLiteReaderTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SQLiteReaderTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    // Definimos las rutas/secciones de nuestra aplicación
    val routes = listOf(
        RouteConfig("Inicio", android.R.drawable.ic_menu_info_details),
        RouteConfig("Consultas", android.R.drawable.ic_menu_search),
        RouteConfig("Ajustes", android.R.drawable.ic_menu_preferences)
    )

    // Utilizamos nuestro componente de layout personalizado
    MainLayout(
        titleNav = "SQLite Reader",
        routes = routes
    ) { selectedRouteIndex, innerPaddingModifier ->
        // Renderizamos el contenido según la ruta seleccionada
        when (selectedRouteIndex) {
            0 -> SQLiteDemo(modifier = innerPaddingModifier)
            1 -> ConsultaSection(modifier = innerPaddingModifier)
            2 -> AjustesSection(modifier = innerPaddingModifier)
        }
    }
}

@Composable
fun SQLiteDemo(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val resultText = remember { mutableStateOf("") }

    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Sección Inicio",
            fontSize = 20.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            textAlign = TextAlign.Center
        )

        Button(
            onClick = {
                try {
                    val db = MyDatabaseHelper(context).writableDatabase
                    // Insertar datos
                    db.execSQL("INSERT INTO usuarios (nombre) VALUES (?)", arrayOf("Carlos"))

                    // Consultar datos
                    val cursor = db.query("SELECT * FROM usuarios", emptyArray())
                    val result = StringBuilder()

                    while (cursor.moveToNext()) {
                        val id = cursor.getInt(0)
                        val nombre = cursor.getString(1)
                        result.append("Usuario: $id - $nombre\n")
                    }

                    cursor.close()
                    resultText.value = result.toString()
                } catch (e: Exception) {
                    resultText.value = "Error: ${e.message}"
                }
            }
        ) {
            Text(text = "Insertar y Mostrar Datos")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = resultText.value)
    }
}

@Composable
fun ConsultaSection(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Sección Consultas",
            fontSize = 20.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            textAlign = TextAlign.Center
        )

        Text(
            text = "Aquí podrás realizar consultas personalizadas a la base de datos",
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun AjustesSection(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Sección Ajustes",
            fontSize = 20.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            textAlign = TextAlign.Center
        )

        Text(
            text = "Configura las opciones de la aplicación",
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    SQLiteReaderTheme {
        MainScreen()
    }
}

