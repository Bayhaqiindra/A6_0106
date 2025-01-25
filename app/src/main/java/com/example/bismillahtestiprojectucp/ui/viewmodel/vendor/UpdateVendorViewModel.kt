package com.example.bismillahtestiprojectucp.ui.viewmodel.vendor

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bismillahtestiprojectucp.repository.VendorRepository
import com.example.bismillahtestiprojectucp.ui.view.vendor.DestinasiUpdateVendor
import kotlinx.coroutines.launch

class UpdateVendorViewModel(
    savedStateHandle: SavedStateHandle,
    private val vendr: VendorRepository
) : ViewModel() {

    var uivendorState by mutableStateOf(InsertVendorUiState())
        private set

    val idVendor: String = checkNotNull(savedStateHandle[DestinasiUpdateVendor.idVendor])

    init {
        viewModelScope.launch {
            uivendorState = vendr.getVendorById(idVendor).toUiStateVendor()
        }
    }

    fun updateInsertVendorState(insertUiEvent: InsertVendorUiEvent) {
        uivendorState = InsertVendorUiState(insertUiEvent = insertUiEvent)
    }

    fun updateVendor(){
        viewModelScope.launch {
            try {
                vendr.updateVendor(idVendor, uivendorState.insertUiEvent.toVendor())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}