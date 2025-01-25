package com.example.bismillahtestiprojectucp.ui.viewmodel.vendor

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bismillahtestiprojectucp.model.Vendor
import com.example.bismillahtestiprojectucp.repository.VendorRepository
import com.example.bismillahtestiprojectucp.ui.view.vendor.DestinasiDetailVendor
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class DetailVendorUiState {
    data class Success(val vendor: Vendor) : DetailVendorUiState()
    object Error : DetailVendorUiState()
    object Loading : DetailVendorUiState()
}

class DetailVendorViewModel(
    savedStateHandle: SavedStateHandle,
    private val vendr: VendorRepository
) : ViewModel() {

    private val idVendor: String = checkNotNull(savedStateHandle[DestinasiDetailVendor.idVendor])

    var detailVendorUiState: DetailVendorUiState by mutableStateOf(DetailVendorUiState.Loading)
        private set

    init {
        getVendorById()
    }

    fun getVendorById() {
        viewModelScope.launch {
            detailVendorUiState = DetailVendorUiState.Loading
            detailVendorUiState = try {
                val vendor = vendr.getVendorById(idVendor)
                DetailVendorUiState.Success(vendor)
            } catch (e: IOException) {
                DetailVendorUiState.Error
            } catch (e: Exception) {
                DetailVendorUiState.Error
            }
        }
    }

    fun deleteVendor() {
        viewModelScope.launch {
            try {
                vendr.deleteVendor(idVendor)
                vendr.getVendor() // Opsional: Memastikan data repository diperbarui
            } catch (e: IOException) {
                detailVendorUiState = DetailVendorUiState.Error
            } catch (e: HttpException) {
                detailVendorUiState = DetailVendorUiState.Error
            }
        }
    }
}
