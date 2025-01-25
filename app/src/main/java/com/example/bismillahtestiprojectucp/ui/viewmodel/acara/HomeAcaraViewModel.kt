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

sealed class HomeAcaraUiState {
    data class Success(val acara: List<Acara>) : HomeAcaraUiState()
    object Error : HomeAcaraUiState()
    object Loading : HomeAcaraUiState()
}

class HomeAcaraViewModel(private val acr: AcaraRepository) : ViewModel() {

    var acaraHomeUIState: HomeAcaraUiState by mutableStateOf(HomeAcaraUiState.Loading)
        private set

    init {
        getAcara()
    }

    fun getAcara() {
        viewModelScope.launch {
            acaraHomeUIState = HomeAcaraUiState.Loading
            acaraHomeUIState = try {
                HomeAcaraUiState.Success(acr.getAcara())
            } catch (e: IOException) {
                HomeAcaraUiState.Error
            } catch (e: HttpException) {
                HomeAcaraUiState.Error
            }
        }
    }
}