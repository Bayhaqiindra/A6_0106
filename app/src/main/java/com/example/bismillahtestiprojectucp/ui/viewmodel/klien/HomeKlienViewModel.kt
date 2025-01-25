package com.example.bismillahtestiprojectucp.ui.viewmodel.klien

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bismillahtestiprojectucp.model.Klien
import com.example.bismillahtestiprojectucp.repository.KlienRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class KlienUiState {
    data class Success(val klien: List<Klien>) : KlienUiState()
    object Error : KlienUiState()
    object Loading : KlienUiState()
}

class HomeKlienViewModel(private val klen: KlienRepository) : ViewModel() {

    var klienUiState: KlienUiState by mutableStateOf(KlienUiState.Loading)
        private set

    init {
        getKlien()
    }

    fun getKlien() {
        viewModelScope.launch {
            klienUiState = KlienUiState.Loading
            klienUiState = try {
                KlienUiState.Success(klen.getKlien())
            } catch (e: IOException) {
                KlienUiState.Error
            } catch (e: HttpException) {
                KlienUiState.Error
            }
        }
    }
}