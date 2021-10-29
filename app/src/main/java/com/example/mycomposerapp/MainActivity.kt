package com.example.mycomposerapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.compose.foundation.layout.Column as Column1


class MainActivity : ComponentActivity() {
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Body()
        }
    }

    @Composable
    fun Avatar() {
        MaterialTheme {
            Column1(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(R.drawable.clipart444231),
                    contentDescription = "MyPhoto",
                    modifier = Modifier
                        .clip(CircleShape)
                        .border(
                            BorderStroke(
                                2.dp,
                                SolidColor(Color.Blue)
                            ),
                            CircleShape
                        )
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Bienvenido Pedro",
                    style = MaterialTheme
                        .typography.h5

                )
                Text(
                    text = "Ingresa tus datos para calcular tu necesidad energetica en kalorias diarias",
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.Center
                )
            }
        }
    }

    @ExperimentalMaterialApi
    @Composable
    fun Form() {
        MaterialTheme {
            Column1() {
                var edad by remember { mutableStateOf("") }
                var peso by remember { mutableStateOf("" )}
                var altura by remember { mutableStateOf("") }
                val options = listOf(
                    "Poca actividad",
                    "Actividad moderada",
                    "Actividad intesa",
                    "Atleta profesional"
                )
                val factorlist = listOf(
                    1.375,
                    1.55,
                    1.725,
                    1.9
                )
                var selectedOptionText by remember { mutableStateOf(options[0]) }
                var factor by remember { mutableStateOf(0.0) }
                var expanded by remember { mutableStateOf(false) }
                val icon = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.ArrowDropDown
                val openDialog = remember { mutableStateOf(false) }
                var textfieldSize by remember { mutableStateOf(Size.Zero) }
                val openModal = remember { mutableStateOf(false) }
                val total = remember { mutableStateOf(0) }


                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    value = edad,
                    onValueChange = { edad = it },
                    label = { Text(text = "Introduce tu edad") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    value = peso, onValueChange = { peso = it },
                    label = { Text(text = "Introduce tu peso en kg") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    value = altura, onValueChange = { altura = it },
                    label = { Text(text = "Introduce tu altura en cm") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentSize(Alignment.CenterEnd)
                ) {
                    OutlinedTextField(
                        readOnly = true,
                        value = selectedOptionText,
                        onValueChange = { selectedOptionText = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { expanded = !expanded }
                            .onGloballyPositioned { coordinates ->
                                textfieldSize = coordinates.size.toSize()
                            }
                            .padding(8.dp),
                        label = { Text(text = "Selecciona tu nivel de actividad fisica diaria") },
                        trailingIcon = {
                            Icon(
                                icon, "flecha",
                                modifier = Modifier.clickable { expanded = !expanded }
                            )
                        }
                    )
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier
                            .width(with(LocalDensity.current) { textfieldSize.width.toDp() })
                    ) {
                        options.forEachIndexed{ index, label ->
                            DropdownMenuItem(
                                onClick = {
                                    selectedOptionText = label
                                    factor = factorlist[index]
                                    expanded = !expanded
                                }) {
                                Text(text = label)
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }

                val radioOptions = listOf("Hombre", "Mujer")
                val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }
                Column1(Modifier.selectableGroup()) {
                    radioOptions.forEach { text ->
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .selectable(
                                    selected = (text == selectedOption),
                                    onClick = { onOptionSelected(text) },
                                    role = Role.RadioButton
                                )
                                .padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = (text == selectedOption),
                                onClick = {
                                    onOptionSelected(text)
                                }
                            )
                            Text(
                                text = text,
                                style = MaterialTheme.typography.body1.merge(),
                                modifier = Modifier.padding(start = 16.dp)
                            )
                        }
                    }
                }
                Button(
                    onClick = {

                        if (selectedOption[0].toString() == "H") {
                            var kalorias = (655 + (9.6 * peso.toFloat())) + ((1.8 * altura.toDouble()) - (4.7 * edad.toInt()))
                            total.value = (kalorias * factor).toInt()
                            openModal.value = true
                        }
                        if (selectedOption[0].toString() == "M") {
                            var kalorias = (655 + (13.7 * peso.toDouble())) + ((5 * altura.toDouble()) - (6.8 * edad.toInt()))
                            total.value = (kalorias * factor).toInt()
                            openModal.value = true
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                ) {
                    Text(text = "Calcular")
                }
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedButton(
                    onClick = {
                        openDialog.value = true
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                ) {
                    Text(text = "Ayuda")
                }
                if (openDialog.value) {
                    AlertDialog(
                        onDismissRequest = {
                            // Dismiss the dialog when the user clicks outside the dialog or on the back
                            // button. If you want to disable that functionality, simply use an empty
                            // onCloseRequest.
                            openDialog.value = false
                        },
                        title = {
                            Text(text = "¿Necesitas ayuda?")
                        },
                        text = {
                            Spacer(modifier = Modifier.height(16.dp))
                            Column1() {
                                val icons = Icons.Filled
                                ListItem(
                                    text = { Text("Poca actividad") },
                                    secondaryText = { Text( "Ejercicio 1 a 3 veces por semana") },
                                    trailing = {
                                        Icon(
                                            painterResource(R.drawable.outline_sentiment_dissatisfied_24),
                                            contentDescription = "Poca actividad fisica"
                                        )
                                    }
                                )
                                ListItem(
                                    text = { Text("Actividad moderada") },
                                    secondaryText = { Text("Ejercicio 3 a 5 veces por semana") },

                                    trailing = {
                                        Icon(
                                            painterResource(R.drawable.outline_sentiment_satisfied_24),
                                            contentDescription = "Localized Description"
                                        )
                                    }
                                )
                                ListItem(
                                    text = { Text("Actividad intensa") },
                                    secondaryText = { Text("Ejercicio 6 a 7 veces por semana") },
                                    trailing = {
                                        Icon(
                                            painterResource(R.drawable.outline_sentiment_very_satisfied_24),
                                            contentDescription = "Localized Description"
                                        )
                                    }
                                )
                                ListItem(
                                    text = { Text("Atleta profesional") },
                                    secondaryText = { Text("Más de 4 horas diarias") },
                                    trailing = {
                                        Icon(
                                            painterResource(R.drawable.outline_self_improvement_24),
                                            contentDescription = "Localized Description"
                                        )
                                    }
                                )
                            }
                        },
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    openDialog.value = false
                                }
                            ) {
                                Text("Entendido")
                            }
                        },
                    )
                }

                if (openModal.value) {
                    AlertDialog(
                        onDismissRequest = {
                            // Dismiss the dialog when the user clicks outside the dialog or on the back
                            // button. If you want to disable that functionality, simply use an empty
                            // onCloseRequest.
                            openModal.value = false
                        },
                        title = {
                            Text(text = "Resultado")
                        },
                        text = {
                            val altura2 = altura.toFloat() / 100
                            val imc = peso.toFloat() / (altura2 * altura2)
                            val pesoaprox = ((altura.toFloat() - 140) * 0.91) + 50
                            val pesoaproxmin = ((altura.toFloat() - 140) * 0.91) + 40

                            Text(
                                "Consumir aproximadamente " + total.value.toString() + " kalorias para mantener su peso actual\n" +
                                        "Tu IMC es: " +  imc.toInt() + "\n" +
                                        "Tu peso ideal maximo aproximado es: " + pesoaprox + "\n"+
                                        "Tu peso ideal minimo es: " + pesoaproxmin
                            )
                        },
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    openModal.value = false
                                }
                            ) {
                                Text("Confirm")
                            }
                        }
                    )
                }
            }
        }
    }

    @ExperimentalMaterialApi
    @Preview(showBackground = true)
    @Composable
    fun Body() {
        MaterialTheme {
            Column1 {
                Avatar()
                Form()
            }
        }
    }

}
