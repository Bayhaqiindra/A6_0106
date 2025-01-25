package com.example.bismillahtestiprojectucp.ui.viewmodel.lokasi

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bismillahtestiprojectucp.model.Lokasi
import com.example.bismillahtestiprojectucp.repository.LokasiRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class LokasiUiState {
    data class Success(val lokasi: List<Lokasi>) : LokasiUiState()
    object Error : LokasiUiState()
    object Loading : LokasiUiState()
}

class HomeLokasiViewModel(private val lks: LokasiRepository) : ViewModel() {

    var lokasiUiState: LokasiUiState by mutableStateOf(LokasiUiState.Loading)
        private set

    init {
        getLokasi()
    }

    fun getLokasi() {
        viewModelScope.launch {
            lokasiUiState = LokasiUiState.Loading
            lokasiUiState = try {
                LokasiUiState.Success(lks.getLokasi())
            } catch (e: IOException) {
                LokasiUiState.Error
            } catch (e: HttpException) {
                LokasiUiState.Error
            }
        }
    }
}
