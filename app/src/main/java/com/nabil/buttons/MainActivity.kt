package com.nabil.buttons

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star

import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {


                MainScreen()
            }
        }
    }
}


@Composable
fun MainScreen() {

    val context = LocalContext.current
    var showProgress by remember { mutableStateOf(false) }
    var isChecked by remember { mutableStateOf(false) }
    var showButtonPressed by remember { mutableStateOf(false) }

    var switchState by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(0) }
    val options = listOf("Opción 1", "Opción 2", "Opción 3")

    var imageIndex by remember { mutableStateOf(0) }
    val images = listOf(
        R.drawable.pica, 
        R.drawable.pica2, 
        R.drawable.van 
    )



    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            showProgress = true
            Handler(Looper.getMainLooper()).postDelayed({
                showProgress = false
            }, 5000) // 5000 milisegundos (5 segundos)
            imageIndex = (imageIndex + 1) % images.size
        }) {
            Text(text = "Presionar")


        }

        if (showProgress) {
            CircularProgressIndicator()
            Text(text = "Botón presionado")
            if (showButtonPressed) {
                Text(text = "Botón presionado")
            }
        }



        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Activar", modifier = Modifier.padding(end = 8.dp), fontSize = 14.sp)
            Checkbox(
                checked = isChecked,
                onCheckedChange = {
                    isChecked = it
                    if (!it) {
                        showButtonPressed = false
                    }
                }
            )


        }

        Switch(
            checked = switchState,
            onCheckedChange = { switchState = it }
        )

        if (switchState) {
            Column {
                options.forEachIndexed { index, text ->
                    Row(
                        Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = index == selectedOption,
                            onClick = { selectedOption = index }
                        )
                        Text(text = text, modifier = Modifier.padding(start = 8.dp))
                    }
                }
            }

            // Mensaje de texto que se actualizará con la opción seleccionada del grupo de botones
            Text(
                text = "Opción seleccionada: ${options.getOrNull(selectedOption) ?: ""}",
                modifier = Modifier.padding(16.dp)
            )

        }

        LaunchedEffect(isChecked) {
            if (isChecked) {
                showButtonPressed = true
                delay(1000) // Tiempo en milisegundos que queremos que aparezca el mensaje
                showButtonPressed = false
            }
        }

        if (showButtonPressed) {
            Text(text = "Botón presionado")
        }

        Icon(Icons.Rounded.Star, contentDescription = null )    //el ícono


        Image(
            painter = painterResource(id = images[imageIndex]),
            contentDescription = null, 
            modifier = Modifier.padding(16.dp)
        )

    }


}

