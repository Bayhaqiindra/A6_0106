package com.example.bismillahtestiprojectucp.ui.viewmodel.acara

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bismillahtestiprojectucp.model.Acara
import com.example.bismillahtestiprojectucp.repository.AcaraRepository
import com.example.bismillahtestiprojectucp.ui.view.acara.DestinasiDetailAcara
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class DetailAcaraUiState {
    data class Success(val acara: Acara) : DetailAcaraUiState()
    object Error : DetailAcaraUiState()
    object Loading : DetailAcaraUiState()
}

class DetailAcaraViewModel(
    savedStateHandle: SavedStateHandle,
    private val acr : AcaraRepository
) : ViewModel() {

    private val idAcara: String = checkNotNull(savedStateHandle[DestinasiDetailAcara.idAcara])
    var detailAcaraUiState: DetailAcaraUiState by mutableStateOf(DetailAcaraUiState.Loading)
        private set

    init {
        getAcaraById()
    }

    fun getAcaraById() {
        viewModelScope.launch {
            detailAcaraUiState = DetailAcaraUiState.Loading
            detailAcaraUiState = try {
                val acara = acr.getAcaraById(idAcara)
                DetailAcaraUiState.Success(acara)
            } catch (e: IOException) {
                DetailAcaraUiState.Error
            } catch (e: Exception) {
                DetailAcaraUiState.Error
            }
        }
    }

    fun deleteAcara() {
        Log.d("DetailAcaraViewModel", "Deleting acara with id: $idAcara")
        viewModelScope.launch {
            try {
                acr.deleteAcara(idAcara)
                acr.getAcara()
            } catch (e: IOException) {
                detailAcaraUiState = DetailAcaraUiState.Error
            } catch (e: HttpException) {
                detailAcaraUiState = DetailAcaraUiState.Error
            }
        }
    }
}

