package com.example.bismillahtestiprojectucp.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bismillahtestiprojectucp.model.Acara
import com.example.bismillahtestiprojectucp.repository.AcaraRepository
import kotlinx.coroutines.launch

class InsertAcaraViewModel(private val acr: AcaraRepository) : ViewModel() {
    var uiacaraState by mutableStateOf(InsertAcaraUiState())
        private set
    fun updateInsertAcaraState(insertUiEvent: InsertAcaraUiEvent) {
        uiacaraState = InsertAcaraUiState(insertUiEvent = insertUiEvent)
    }

    fun insertAcara() {
        viewModelScope.launch {
            try {
                acr.insertAcara(uiacaraState.insertUiEvent.toAcara())
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("InsertAcaraViewModel", "Insert failed", e)
            }
        }
    }
}

data class InsertAcaraUiState(
    val insertUiEvent: InsertAcaraUiEvent = InsertAcaraUiEvent(),
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false
)

data class InsertAcaraUiEvent(
    val idAcara: String = "",
    val namaAcara: String = "",
    val deskripsiAcara: String = "",
    val tanggalMulai: String = "",
    val tanggalBerakhir: String = "",
    val idLokasi: String = "",
    val idKlien: String = "",
    val statusAcara: String = ""
)

fun InsertAcaraUiEvent.toAcara(): Acara = Acara(
    idAcara = idAcara,
    namaAcara = namaAcara,
    deskripsiAcara = deskripsiAcara,
    tanggalMulai = tanggalMulai,
    tanggalBerakhir = tanggalBerakhir,
    idLokasi = idLokasi,
    idKlien = idKlien,
    statusAcara = statusAcara
)

fun Acara.toUiStateAcara(): InsertAcaraUiState = InsertAcaraUiState(
    insertUiEvent = toInsertUiEvent()
)

fun Acara.toInsertUiEvent(): InsertAcaraUiEvent = InsertAcaraUiEvent(
    idAcara = idAcara,
    namaAcara = namaAcara,
    deskripsiAcara = deskripsiAcara,
    tanggalMulai = tanggalMulai,
    tanggalBerakhir = tanggalBerakhir,
    idLokasi = idLokasi,
    idKlien = idKlien,
    statusAcara = statusAcara
)
