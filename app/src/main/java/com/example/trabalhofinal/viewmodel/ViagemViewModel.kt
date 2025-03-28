package com.example.trabalhofinal.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.trabalhofinal.screens.Viagem

class ViagemViewModel : ViewModel() {
    private val _viagens = mutableStateListOf<Viagem>()
    val viagens: List<Viagem> get() = _viagens

    fun adicionarViagem(viagem: Viagem) {
        _viagens.add(viagem)
    }
}