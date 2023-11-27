package com.example.trivianumeros

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trivianumeros.data.RetrofitServiceFactory
import com.example.trivianumeros.data.model.ResultadoTrivia
import com.example.trivianumeros.ui.theme.TrivianumerosTheme
import kotlinx.coroutines.launch
import java.lang.Exception




class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TrivianumerosTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Trivia()
                }
            }
        }
    }
}
@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Trivia( modifier: Modifier = Modifier) {
    val service = RetrofitServiceFactory.makeRetrofitService()
    var numerotrivia by remember { mutableStateOf("") }
    var respuestaApi by remember { mutableStateOf("") }
    var resultadotrivia by remember { mutableStateOf<ResultadoTrivia?>(null) }

    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Trivia de numeros",
            fontSize = 30.sp,
            modifier = Modifier.padding(16.dp)

        )

        TextField(
            value = numerotrivia,
            onValueChange = { numerotrivia = it },
            label = {
                Text(
                    text = "Introduce tu numero",
                    style = TextStyle(color = Color.Gray, fontWeight = FontWeight.Bold)
                )
            },
            maxLines = 2,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
        Row {
            Text("Ingresa tu numero:  $numerotrivia")
        }

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Button(
                onClick = {
                    coroutineScope.launch {
                        try {
                            val triviaResponse = service.getTrivia(numerotrivia)
                            respuestaApi = triviaResponse

                        } catch (e: Exception) {
                            respuestaApi =
                                "Error al obtener la trivia: ${e.message ?: "Error desconocido"}"
                        }
                    }
                },
                modifier = Modifier.padding(8.dp),
            ) {
                Text("Enviar número")
            }
        }

        if (numerotrivia == "666") {
            Text("Número del diablo")
            Image(
                painter = painterResource(id = R.drawable.marca_bestia), // Reemplaza con tu imagen
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        } else if (respuestaApi.isNotEmpty()) {
            Text("$respuestaApi")
        }

    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TrivianumerosTheme {
        Trivia()
    }
}