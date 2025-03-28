package com.example.trabalhofinal.screens

import android.app.DatePickerDialog
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.trabalhofinal.viewmodel.ViagemViewModel
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NovaViagemScreen(navController: NavController, viewModel: ViagemViewModel) {
    val context = LocalContext.current

    var destino by remember { mutableStateOf("") }
    var tipo by remember { mutableStateOf("Lazer") }

    var dataInicio by remember { mutableStateOf("") }
    var dataFim by remember { mutableStateOf("") }

    var orcamento by remember { mutableStateOf("") }

    val calendar = Calendar.getInstance()

    fun showDatePicker(onDateSelected: (String) -> Unit) {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                onDateSelected("${"%02d".format(dayOfMonth)}/${"%02d".format(month + 1)}/$year")
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate("menu") },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") }
                )
                NavigationBarItem(
                    selected = true,
                    onClick = { /* já está aqui */ },
                    icon = { Icon(Icons.Default.Add, contentDescription = "Nova Viagem") },
                    label = { Text("Nova Viagem") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate("sobre") }, // se for criar a tela 'sobre'
                    icon = { Icon(Icons.Default.Info, contentDescription = "Sobre") },
                    label = { Text("Sobre") }
                )
            }
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Nova Viagem", style = MaterialTheme.typography.headlineMedium)

            OutlinedTextField(
                value = destino,
                onValueChange = { destino = it },
                label = { Text("Destino") },
                modifier = Modifier.fillMaxWidth()
            )

            Text("Tipo de Viagem")

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = tipo == "Lazer",
                        onClick = { tipo = "Lazer" }
                    )
                    Text("Lazer")
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = tipo == "Negócio",
                        onClick = { tipo = "Negócio" }
                    )
                    Text("Negócio")
                }
            }

            OutlinedTextField(
                value = dataInicio,
                onValueChange = {},
                label = { Text("Data de Início") },
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = { showDatePicker { dataInicio = it } }) {
                        Icon(Icons.Default.DateRange, contentDescription = "Selecionar Data Início")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showDatePicker { dataInicio = it } }
            )

            OutlinedTextField(
                value = dataFim,
                onValueChange = {},
                label = { Text("Data de Fim") },
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = { showDatePicker { dataFim = it } }) {
                        Icon(Icons.Default.DateRange, contentDescription = "Selecionar Data Fim")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showDatePicker { dataFim = it } }
            )

            OutlinedTextField(
                value = orcamento,
                onValueChange = { orcamento = it },
                label = { Text("Orçamento estimado") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    if (destino.isBlank() || dataInicio.isBlank() || dataFim.isBlank() || orcamento.isBlank()) {
                        Toast.makeText(context, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
                    } else {
                        // Adiciona a viagem no ViewModel
                        viewModel.adicionarViagem(
                            Viagem(
                                destino = destino,
                                dataInicio = dataInicio,
                                dataFim = dataFim,
                                orcamento = "R$ $orcamento",
                                imagem = com.example.trabalhofinal.R.drawable.logo_viagem
                            )
                        )
                        Toast.makeText(context, "Viagem salva com sucesso!", Toast.LENGTH_SHORT).show()
                        navController.navigate("menu")
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Salvar Viagem")
            }
        }
    }
}
