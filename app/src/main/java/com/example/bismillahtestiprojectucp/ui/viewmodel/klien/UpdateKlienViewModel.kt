package com.example.bismillahtestiprojectucp.ui.viewmodel.klien

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bismillahtestiprojectucp.repository.KlienRepository
import com.example.bismillahtestiprojectucp.ui.view.klien.DestinasiUpdateKlien
import kotlinx.coroutines.launch

class UpdateKlienViewModel(
    savedStateHandle: SavedStateHandle,
    private val klen: KlienRepository
) : ViewModel() {

    var uiklienState by mutableStateOf(InsertKlienUiState())
        private set

    val idKlien: String = checkNotNull(savedStateHandle[DestinasiUpdateKlien.idKlien])

    init {
        viewModelScope.launch {
            uiklienState = klen.getKlienById(idKlien).toUiStateKlien()
        }
    }

    fun updateInsertKlienState(insertUiEvent: InsertKlienUiEvent) {
        uiklienState = InsertKlienUiState(insertUiEvent = insertUiEvent)
    }

    fun updateKlien(){
        viewModelScope.launch {
            try {
                klen.updateKlien(idKlien, uiklienState.insertUiEvent.toKlien())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}