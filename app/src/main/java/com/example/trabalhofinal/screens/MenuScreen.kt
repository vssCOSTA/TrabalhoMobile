package com.example.trabalhofinal.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.trabalhofinal.R
import com.example.trabalhofinal.viewmodel.ViagemViewModel


data class Viagem(
    val destino: String,
    val dataInicio: String,
    val dataFim: String,
    val orcamento: String,
    val imagem: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScreen(navController: NavController, viewModel: ViagemViewModel)
 {
     val viagens = viewModel.viagens

     var selectedIndex by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Viagens") }
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = selectedIndex == 0,
                    onClick = { selectedIndex = 0 },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") }
                )
                NavigationBarItem(
                    selected = selectedIndex == 1,
                    onClick = {
                        selectedIndex = 1
                        navController.navigate("nova_viagem")
                    },
                    icon = { Icon(Icons.Default.Add, contentDescription = "Nova Viagem") },
                    label = { Text("Nova Viagem") }
                )
                NavigationBarItem(
                    selected = selectedIndex == 2,
                    onClick = { selectedIndex = 2 },
                    icon = { Icon(Icons.Default.Info, contentDescription = "Sobre") },
                    label = { Text("Sobre") }
                )
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(viagens) { viagem ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Row(modifier = Modifier.padding(16.dp)) {
                        Image(
                            painter = painterResource(id = viagem.imagem),
                            contentDescription = "Imagem da viagem",
                            modifier = Modifier
                                .size(100.dp)
                                .padding(end = 16.dp)
                        )

                        Column {
                            Text(viagem.destino, style = MaterialTheme.typography.titleMedium)
                            Spacer(modifier = Modifier.height(4.dp))
                            Column {
                                Text("Início: ${viagem.dataInicio}")
                                Text("Fim: ${viagem.dataFim}")
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("Orçamento: ${viagem.orcamento}", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}