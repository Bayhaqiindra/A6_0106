package com.example.bismillahtestiprojectucp.ui.viewmodel.lokasi

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bismillahtestiprojectucp.repository.KlienRepository
import com.example.bismillahtestiprojectucp.repository.LokasiRepository
import com.example.bismillahtestiprojectucp.ui.view.klien.DestinasiUpdateKlien
import com.example.bismillahtestiprojectucp.ui.view.lokasi.DestinasiUpdateLokasi
import com.example.bismillahtestiprojectucp.ui.viewmodel.klien.InsertKlienUiEvent
import com.example.bismillahtestiprojectucp.ui.viewmodel.klien.InsertKlienUiState
import com.example.bismillahtestiprojectucp.ui.viewmodel.klien.toKlien
import com.example.bismillahtestiprojectucp.ui.viewmodel.klien.toUiStateKlien
import kotlinx.coroutines.launch

class UpdateLokasiViewModel(
    savedStateHandle: SavedStateHandle,
    private val lks: LokasiRepository
) : ViewModel() {

    var uilokasiState by mutableStateOf(InsertLokasiUiState())
        private set

    val idLokasi: String = checkNotNull(savedStateHandle[DestinasiUpdateLokasi.idLokasi])

    init {
        viewModelScope.launch {
            uilokasiState = lks.getLokasiById(idLokasi).toUiStateLokasi()
        }
    }

    fun updateInsertLokasiState(insertUiEvent: InsertLokasiUiEvent) {
        uilokasiState = InsertLokasiUiState(insertUiEvent = insertUiEvent)
    }

    fun updateLokasi(){
        viewModelScope.launch {
            try {
                lks.updateLokasi(idLokasi, uilokasiState.insertUiEvent.toLokasi())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}