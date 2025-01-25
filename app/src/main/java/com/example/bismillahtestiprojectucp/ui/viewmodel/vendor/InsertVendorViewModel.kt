package com.example.bismillahtestiprojectucp.ui.viewmodel.vendor

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bismillahtestiprojectucp.model.Vendor
import com.example.bismillahtestiprojectucp.repository.VendorRepository
import kotlinx.coroutines.launch

class InsertVendorViewModel(private val vendr: VendorRepository) : ViewModel() {
    var uiVendorState by mutableStateOf(InsertVendorUiState())
        private set

    fun updateInsertVendorState(insertUiEvent: InsertVendorUiEvent) {
        uiVendorState = InsertVendorUiState(insertUiEvent = insertUiEvent)
    }

    fun insertVendor() {
        viewModelScope.launch {
            try {
                vendr.insertVendor(uiVendorState.insertUiEvent.toVendor())
                vendr.getVendor()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertVendorUiState(
    val insertUiEvent: InsertVendorUiEvent = InsertVendorUiEvent(),
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false
)

data class InsertVendorUiEvent(
    val idVendor: String = "",
    val namaVendor: String = "",
    val jenisVendor: String = "",
    val kontakVendor: String = ""
)

fun InsertVendorUiEvent.toVendor(): Vendor = Vendor(
    idVendor = idVendor,
    namaVendor = namaVendor,
    jenisVendor = jenisVendor,
    kontakVendor = kontakVendor
)

fun Vendor.toUiStateVendor(): InsertVendorUiState = InsertVendorUiState(
    insertUiEvent = toInsertUiEvent()
)

fun Vendor.toInsertUiEvent(): InsertVendorUiEvent = InsertVendorUiEvent(
    idVendor = idVendor,
    namaVendor = namaVendor,
    jenisVendor = jenisVendor,
    kontakVendor = kontakVendor
)
