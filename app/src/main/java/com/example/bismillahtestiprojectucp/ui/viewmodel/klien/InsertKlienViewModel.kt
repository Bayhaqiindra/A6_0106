package com.example.bismillahtestiprojectucp.ui.viewmodel.klien

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bismillahtestiprojectucp.model.Klien
import com.example.bismillahtestiprojectucp.repository.KlienRepository
import com.example.bismillahtestiprojectucp.viewmodel.InsertAcaraUiEvent
import kotlinx.coroutines.launch

class InsertKlienViewModel(private val klen: KlienRepository) : ViewModel() {
    var uiklienState by mutableStateOf(InsertKlienUiState())
        private set
    fun updateInsertKlienState(insertUiEvent: InsertKlienUiEvent) {
        uiklienState = InsertKlienUiState(insertUiEvent = insertUiEvent)
    }

    fun insertKlien() {
        viewModelScope.launch {
            try {
                klen.insertKlien(uiklienState.insertUiEvent.toKlien())
                klen.getKlien()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertKlienUiState(
    val insertUiEvent: InsertKlienUiEvent = InsertKlienUiEvent(),
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false
)

data class InsertKlienUiEvent(
    val idKlien: String = "",
    val namaKlien: String = "",
    val kontakKlien: String = "",
)

fun InsertKlienUiEvent.toKlien(): Klien = Klien(
    idKlien = idKlien,
    namaKlien = namaKlien,
    kontakKlien = kontakKlien
)

fun Klien.toUiStateKlien(): InsertKlienUiState = InsertKlienUiState(
    insertUiEvent = toInsertUiEvent()
)

fun Klien.toInsertUiEvent(): InsertKlienUiEvent = InsertKlienUiEvent(
    idKlien = idKlien,
    namaKlien = namaKlien,
    kontakKlien = kontakKlien
)