package com.example.bismillahtestiprojectucp.ui.viewmodel.klien

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bismillahtestiprojectucp.model.Acara
import com.example.bismillahtestiprojectucp.model.Klien
import com.example.bismillahtestiprojectucp.repository.AcaraRepository
import com.example.bismillahtestiprojectucp.repository.KlienRepository
import com.example.bismillahtestiprojectucp.ui.view.acara.DestinasiDetailAcara
import com.example.bismillahtestiprojectucp.ui.view.klien.DestinasiDetailKlien
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class DetailKlienUiState {
    data class Success(val klien: Klien) : DetailKlienUiState()
    object Error : DetailKlienUiState()
    object Loading : DetailKlienUiState()
}

class DetailKlienViewModel(
    savedStateHandle: SavedStateHandle,
    private val klen : KlienRepository,
) : ViewModel() {

    private val idKlien: String = checkNotNull(savedStateHandle[DestinasiDetailKlien.idKlien])

    var detailKlienUiState: DetailKlienUiState by mutableStateOf(DetailKlienUiState.Loading)
        private set

    init {
        getKlienById()
    }

    fun getKlienById() {
        viewModelScope.launch {
            detailKlienUiState = DetailKlienUiState.Loading
            detailKlienUiState = try {
                val klien = klen.getKlienById(idKlien)
                DetailKlienUiState.Success(klien)
            } catch (e: IOException) {
                DetailKlienUiState.Error
            } catch (e: Exception) {
                DetailKlienUiState.Error
            }
        }
    }

    fun deleteKlien() {
        viewModelScope.launch {
            try {
                klen.deleteKlien(idKlien)
                klen.getKlien()
            } catch (e: IOException) {
                detailKlienUiState = DetailKlienUiState.Error
            } catch (e: HttpException) {
                detailKlienUiState = DetailKlienUiState.Error
            }
        }
    }
}