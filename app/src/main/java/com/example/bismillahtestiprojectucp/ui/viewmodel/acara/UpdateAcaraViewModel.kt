package com.example.bismillahtestiprojectucp.ui.viewmodel.acara

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bismillahtestiprojectucp.repository.AcaraRepository
import com.example.bismillahtestiprojectucp.ui.view.acara.DestinasiUpdateAcara
import com.example.bismillahtestiprojectucp.viewmodel.InsertAcaraUiEvent
import com.example.bismillahtestiprojectucp.viewmodel.InsertAcaraUiState
import com.example.bismillahtestiprojectucp.viewmodel.toAcara
import com.example.bismillahtestiprojectucp.viewmodel.toUiStateAcara
import kotlinx.coroutines.launch


class UpdateAcaraViewModel(
    savedStateHandle: SavedStateHandle,
    private val acr: AcaraRepository
) : ViewModel() {

    var uiacaraState by mutableStateOf(InsertAcaraUiState())
        private set

    val idAcara: String = checkNotNull(savedStateHandle[DestinasiUpdateAcara.idAcara])

    init {
        viewModelScope.launch {
            uiacaraState = acr.getAcaraById(idAcara).toUiStateAcara()
        }
    }

    fun updateInsertAcaraState(insertUiEvent: InsertAcaraUiEvent) {
        uiacaraState = InsertAcaraUiState(insertUiEvent = insertUiEvent)
    }

    suspend fun updateAcara(){
        viewModelScope.launch {
            try {
                acr.updateAcara(idAcara, uiacaraState.insertUiEvent.toAcara())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}