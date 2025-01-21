package com.example.bismillahtestiprojectucp.ui.viewmodel.acara

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bismillahtestiprojectucp.model.Acara
import com.example.bismillahtestiprojectucp.repository.AcaraRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class AcaraUiState {
    data class Success(val acara: List<Acara>) : AcaraUiState()
    object Error : AcaraUiState()
    object Loading : AcaraUiState()
}

class HomeAcaraViewModel(private val acr: AcaraRepository) : ViewModel() {

    var acaraUIState: AcaraUiState by mutableStateOf(AcaraUiState.Loading)
        private set

    init {
        getAcara()
    }

    fun getAcara() {
        viewModelScope.launch {
            acaraUIState = AcaraUiState.Loading
            acaraUIState = try {
                AcaraUiState.Success(acr.getAcara())
            } catch (e: IOException) {
                AcaraUiState.Error
            } catch (e: HttpException) {
                AcaraUiState.Error
            }
        }
    }

    fun deleteAcara(idAcara: String) {
        viewModelScope.launch {
            try {
                acr.deleteAcara(idAcara)
                getAcara() // Refresh data setelah penghapusan
            } catch (e: IOException) {
                acaraUIState = AcaraUiState.Error
            } catch (e: HttpException) {
                acaraUIState = AcaraUiState.Error
            }
        }
    }
}