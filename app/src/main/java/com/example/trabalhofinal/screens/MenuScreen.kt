package com.example.trabalhofinal.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.trabalhofinal.R
import com.example.trabalhofinal.data.AppDatabase
import com.example.trabalhofinal.data.ViagemEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScreen(navController: NavController) {
    val context = LocalContext.current
    val db = remember { AppDatabase.getDatabase(context) }

    var viagens by remember { mutableStateOf(listOf<ViagemEntity>()) }

    // Buscar viagens do banco
    LaunchedEffect(Unit) {
        viagens = withContext(Dispatchers.IO) {
            db.viagemDao().getAllViagens()
        }
    }

    var selectedIndex by remember { mutableStateOf(0) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Viagens") }) },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(selected = selectedIndex == 0, onClick = {
                    selectedIndex = 0
                }, icon = { Icon(Icons.Default.Home, contentDescription = "Home") }, label = { Text("Home") })
                NavigationBarItem(selected = selectedIndex == 1, onClick = {
                    selectedIndex = 1
                    navController.navigate("nova_viagem")
                }, icon = { Icon(Icons.Default.Add, contentDescription = "Nova Viagem") }, label = { Text("Nova Viagem") })
                NavigationBarItem(selected = selectedIndex == 2, onClick = {
                    selectedIndex = 2
                }, icon = { Icon(Icons.Default.Info, contentDescription = "Sobre") }, label = { Text("Sobre") })
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
            items(viagens, key = { it.id }) { viagem ->
                val dismissState = rememberDismissState(
                    confirmValueChange = {
                        if (it == DismissValue.DismissedToStart) {
                            CoroutineScope(Dispatchers.IO).launch {
                                db.viagemDao().deleteViagem(viagem)
                                val atualizada = db.viagemDao().getAllViagens()
                                withContext(Dispatchers.Main) {
                                    viagens = atualizada
                                }
                            }
                            true
                        } else false
                    }
                )

                SwipeToDismiss(
                    state = dismissState,
                    directions = setOf(DismissDirection.EndToStart),
                    background = {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(end = 24.dp),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            Icon(
                                Icons.Default.Delete,
                                contentDescription = "Excluir",
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    },
                    dismissContent = { // ⬅️ ESSA LINHA FALTAVA
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .pointerInput(Unit) {
                                    detectTapGestures(
                                        onLongPress = {
                                            navController.navigate("editar_viagem/${viagem.id}")
                                        }
                                    )
                                },
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Row(modifier = Modifier.padding(16.dp)) {
                                Image(
                                    painter = painterResource(id = R.drawable.logo_viagem),
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
                )
            }
        }
    }
}

