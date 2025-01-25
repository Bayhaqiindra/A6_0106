package com.example.bismillahtestiprojectucp.ui.viewmodel.vendor

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bismillahtestiprojectucp.model.Vendor
import com.example.bismillahtestiprojectucp.repository.VendorRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class VendorUiState {
    data class Success(val vendor: List<Vendor>) : VendorUiState()
    object Error : VendorUiState()
    object Loading : VendorUiState()
}

class HomeVendorViewModel(private val vendr: VendorRepository) : ViewModel() {

    var vendorUiState: VendorUiState by mutableStateOf(VendorUiState.Loading)
        private set

    init {
        getVendor()
    }

    fun getVendor() {
        viewModelScope.launch {
            vendorUiState = VendorUiState.Loading
            vendorUiState = try {
                VendorUiState.Success(vendr.getVendor())
            } catch (e: IOException) {
                VendorUiState.Error
            } catch (e: HttpException) {
                VendorUiState.Error
            }
        }
    }
}
