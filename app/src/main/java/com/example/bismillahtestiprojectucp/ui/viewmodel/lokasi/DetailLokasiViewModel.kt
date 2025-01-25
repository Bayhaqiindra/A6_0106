package com.example.bismillahtestiprojectucp.ui.viewmodel.lokasi

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bismillahtestiprojectucp.model.Lokasi
import com.example.bismillahtestiprojectucp.repository.LokasiRepository
import com.example.bismillahtestiprojectucp.ui.view.lokasi.DestinasiDetailLokasi
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class DetailLokasiUiState {
    data class Success(val lokasi: Lokasi) : DetailLokasiUiState()
    object Error : DetailLokasiUiState()
    object Loading : DetailLokasiUiState()
}

class DetailLokasiViewModel(
    savedStateHandle: SavedStateHandle,
    private val lks: LokasiRepository
) : ViewModel() {

    private val idLokasi: String = checkNotNull(savedStateHandle[DestinasiDetailLokasi.idLokasi])

    var detailLokasiUiState: DetailLokasiUiState by mutableStateOf(DetailLokasiUiState.Loading)
        private set

    init {
        getLokasiById()
    }

    fun getLokasiById() {
        viewModelScope.launch {
            detailLokasiUiState = DetailLokasiUiState.Loading
            detailLokasiUiState = try {
                val lokasi = lks.getLokasiById(idLokasi)
                DetailLokasiUiState.Success(lokasi)
            } catch (e: IOException) {
                DetailLokasiUiState.Error
            } catch (e: Exception) {
                DetailLokasiUiState.Error
            }
        }
    }

    fun deleteLokasi() {
        viewModelScope.launch {
            try {
                lks.deleteLokasi(idLokasi)
                lks.getLokasi() // Memastikan data diperbarui di repository
            } catch (e: IOException) {
                detailLokasiUiState = DetailLokasiUiState.Error
            } catch (e: HttpException) {
                detailLokasiUiState = DetailLokasiUiState.Error
            }
        }
    }
}
