package com.example.bismillahtestiprojectucp.ui.viewmodel.lokasi

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bismillahtestiprojectucp.model.Lokasi
import com.example.bismillahtestiprojectucp.repository.LokasiRepository
import kotlinx.coroutines.launch

class InsertLokasiViewModel(private val lks: LokasiRepository) : ViewModel() {
    var uilokasiState by mutableStateOf(InsertLokasiUiState())
        private set
    fun updateInsertLokasiState(insertUiEvent: InsertLokasiUiEvent) {
        uilokasiState = InsertLokasiUiState(insertUiEvent = insertUiEvent)
    }

    fun insertLokasi() {
        viewModelScope.launch {
            try {
                lks.insertLokasi(uilokasiState.insertUiEvent.toLokasi())
                lks.getLokasi()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertLokasiUiState(
    val insertUiEvent: InsertLokasiUiEvent = InsertLokasiUiEvent(),
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false
)

data class InsertLokasiUiEvent(
    val idLokasi: String = "",
    val namaLokasi: String = "",
    val alamatLokasi: String = "",
    val kapasitas: String = "",
)

fun InsertLokasiUiEvent.toLokasi(): Lokasi = Lokasi(
    idLokasi = idLokasi,
    namaLokasi = namaLokasi,
    alamatLokasi = alamatLokasi,
    kapasitas = kapasitas,
)

fun Lokasi.toUiStateLokasi(): InsertLokasiUiState = InsertLokasiUiState(
    insertUiEvent = toInsertUiEvent()
)

fun Lokasi.toInsertUiEvent(): InsertLokasiUiEvent = InsertLokasiUiEvent(
    idLokasi = idLokasi,
    namaLokasi = namaLokasi,
    alamatLokasi = alamatLokasi,
    kapasitas = kapasitas,
)